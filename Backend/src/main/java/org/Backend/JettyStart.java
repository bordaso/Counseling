package org.Backend;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.EnumSet;

import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;

import org.Backend.Config.Config;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.sun.faces.config.ConfigureListener;

public class JettyStart {

	private final Server server;

	// private Logger logger = LoggerFactory.getLogger(JettyTest.class);

	@SuppressWarnings(value = { "unused" })
	private static void loadTimeWeavingAtStartup(String[] args) throws URISyntaxException, IOException {
		String currentPath = JettyStart.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()
				.replace('/', File.separator.charAt(0)).substring(1);
		if (args.length == 0 /* && Runtime.getRuntime().maxMemory()/1024/1024<980 */) {
			Runtime.getRuntime().exec(
					"java -jar -javaagent:C:/Users/Oliver/.m2/repository/org/aspectj/aspectjweaver/1.9.2/aspectjweaver-1.9.2.jar -javaagent:C:/Users/Oliver/.m2/repository/org/springframework/spring-instrument/5.1.6.RELEASE/spring-instrument-5.1.6.RELEASE.jar "
							+ currentPath + " restart");
			return;
		}
	}

	public static void main(String[] args) throws Exception {

		// loadTimeWeavingAtStartup(args);

		int port = 54321;
		JettyStart embeddedServer = new JettyStart(port);
		embeddedServer.listen();
	}

	public JettyStart(int port) {

		this.server = new Server();

		try {

			ServerConnector httpConnector = new ServerConnector(server);
			httpConnector.setHost("localhost");
			httpConnector.setPort(port);
			httpConnector.setIdleTimeout(5000);
			server.addConnector(httpConnector);

			ServletContextHandler webappContext = new ServletContextHandler(ServletContextHandler.SESSIONS);

			URL webRootLocation = this.getClass().getResource("/webapp/index.html");

			URI webRootUri = URI.create(webRootLocation.toURI().toASCIIString().replaceFirst("/index.html$", "/"));
			System.err.printf("//////////////////////////////////////////////////Web Root URI: %s%n", webRootUri);

			webappContext.setContextPath("/");
			webappContext.setBaseResource(Resource.newResource(webRootUri));
			webappContext.setWelcomeFiles(new String[] { "index.html" });
			webappContext.getMimeTypes().addMimeMapping("txt", "text/plain;charset=utf-8");

			webappContext.setDisplayName(
					"Angular 6 and Primefaces 6 on Jetty Embedded 9 with Spring 5 and Hibernate and Jersey with Jackson Example");

			initializeJSF(webappContext);
			initializeJersey(webappContext);
			
			webappContext.addFilter(new FilterHolder(new DelegatingFilterProxy("springSecurityFilterChain")), "/*", EnumSet.allOf(DispatcherType.class));

			webappContext.addServlet(DefaultServlet.class, "/").setInitOrder(2);
			
			ErrorPageErrorHandler errorHandler = new ErrorPageErrorHandler();
		    errorHandler.addErrorPage(401, "/p401.html");
		    errorHandler.addErrorPage(404, "/p404.html");
		    webappContext.setErrorHandler(errorHandler);

			server.setHandler(webappContext);
			server.start();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private void initializeJersey(ServletContextHandler webappContext) {
		ServletHolder serHol = webappContext.addServlet(ServletContainer.class, "/rest/*");
		serHol.setInitOrder(1);
		serHol.setInitParameter("jersey.config.server.provider.packages", "org.Backend");
		serHol.setInitParameter("jersey.config.server.provider.classnames",
				"org.glassfish.jersey.jackson.JacksonFeature");

		// Enable Spring to inject beans into Jersey
		AnnotationConfigWebApplicationContext wac = new AnnotationConfigWebApplicationContext();
		wac.register(Config.class);
		webappContext.addEventListener(new ContextLoaderListener(wac));

	}

	private void listen() {
		try {
			server.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void initializeJSF(ServletContextHandler context) {

		// JSF parameters ...
		context.setInitParameter("com.sun.faces.forceLoadConfiguration", "true");
		context.setInitParameter("com.sun.faces.enableRestoreView11Compatibility", "true");
		context.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
		context.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", "true");
		context.setInitParameter("javax.faces.STATE_SAVING_METHOD", "server");
		context.setInitParameter("javax.faces.DEFAULT_SUFFIX", ".xhtml");
		context.setInitParameter("defaultHtmlEscape", "true");
		// context.setInitParameter("primefaces.THEME", "redmond");
		context.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "false");

		context.addEventListener(new ConfigureListener());

		// JSF Servlet ...
		ServletHolder jsfServlet = new ServletHolder(FacesServlet.class);
		jsfServlet.setDisplayName("Faces Servlet");
		jsfServlet.setName("Faces_Servlet");
		jsfServlet.setInitOrder(0);

		// Add to web context ...
		context.addServlet(jsfServlet, "*.xhtml");
	}

	public void shutdown() {
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					for (Handler handler : server.getHandlers()) {
						handler.stop();
					}
					server.stop();
					server.getThreadPool().join();
					System.exit(0);
				} catch (Exception ex) {
					System.out.println("Failed to stop Jetty");
				}
			}
		}.start();
	}
}