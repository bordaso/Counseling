package org.Backend;

import java.net.URI;
import java.net.URL;

import javax.faces.webapp.FacesServlet;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.sun.faces.config.ConfigureListener;

public class JettyTest {

	private final Server server;

	// private Logger logger = LoggerFactory.getLogger(JettyTest.class);

	public static void main(String[] args) throws Exception {
		int port = 54321;
		JettyTest embeddedServer = new JettyTest(port);
		embeddedServer.listen();
	}

	public JettyTest(int port) {

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

			webappContext.addServlet(DefaultServlet.class, "/").setInitOrder(2);

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