package org.Backend;

import java.awt.GraphicsEnvironment;
import java.lang.Thread.UncaughtExceptionHandler;

import javax.faces.webapp.FacesServlet;
import javax.swing.JOptionPane;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.sun.faces.config.ConfigureListener;

public class JettyTest implements UncaughtExceptionHandler {

	private final Server server;

	public static final String SERVER_REFERENCE = "jettyInMemory";

	public static void main(String[] args) throws Exception {
		int port = 54321;

		JettyTest embeddedServer = new JettyTest(port);
		embeddedServer.listen();
	}

	public JettyTest(int port) {

		Thread.setDefaultUncaughtExceptionHandler(this);

		this.server = new Server();

		try {

			ServerConnector httpConnector = new ServerConnector(server);
			httpConnector.setHost("localhost");
			httpConnector.setPort(port);
			httpConnector.setIdleTimeout(5000);
			server.addConnector(httpConnector);
			// log.info("Http connector started on port: " + port);

			// WebApp Context Handler

			// final String webappDir =
			// this.getClass().getClassLoader().getResource("org/Backend/NAH3").toExternalForm();

			final String webappDir = this.getClass().getClassLoader().getResource("webapp").toExternalForm();
			// webappDir IS  file:/C:/Users/Oliver/consultancy_poc_workspace_v2/Main/Backend/target/classes/webapp
			// try it file:/C:/Users/Oliver/consultancy_poc_workspace_v2/Main/Frontend/src/main/resources/webapp
			System.out.println(webappDir);
			WebAppContext webappContext = new WebAppContext();

			/*
			 * WebAppContext webappContext = new WebAppContext(webappDir, "/") {
			 * 
			 * // Workaround to support JSF annotation scanning in Maven environment (part1)
			 * 
			 * @Override public String getResourceAlias(String alias) {
			 * 
			 * System.out.println("IAM LOOKING FOR  "+alias);
			 * 
			 * final Map<String, String> resourceAliases = (Map<String, String>)
			 * getResourceAliases();
			 * 
			 * if (resourceAliases == null) { return null; } for (Entry<String, String>
			 * oneAlias : resourceAliases.entrySet()) {
			 * System.out.println("oneAlias:  "+oneAlias.getKey()); if
			 * (alias.startsWith(oneAlias.getKey())) {
			 * System.out.println(alias.replace(oneAlias.getKey(), oneAlias.getValue()));
			 * 
			 * return alias.replace(oneAlias.getKey(), oneAlias.getValue()); } } return
			 * null; } } ;
			 * 
			 * // file:/C:/Users/Oliver/consultancy_poc_workspace_v2/Main/Backend/target/
			 * classes/webapp/WEB-INF/faces-config.xml ///
			 * /Backend/target/classes/org/Backend
			 */
			// Workaround to support JSF annotation scanning in Maven environment (part2)
			try {

				webappContext.setBaseResource(new ResourceCollection(new String[] { webappDir, "./target" }));
				//C:\Users\Oliver\consultancy_poc_workspace_v2\Main\Frontend\target\classes\index.xhtml
				//webappContext.setBaseResource(new ResourceCollection(new String[] { "file:/C:/Users/Oliver/consultancy_poc_workspace_v2/Main/Frontend/target/classes/webapp"}));
				// webappContext.setResourceAlias("/WEB-INF/classes/", "/classes/");
			} catch (Exception e) {
			}

			webappContext.setDisplayName("Angular 6 and Primefaces 6 on Jetty Embedded 9 with Spring 5 and Hibernate and Jersey with Jackson Example");
			webappContext.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");

			// add server reference to context ...
			webappContext.setAttribute(SERVER_REFERENCE, this);
			
	
			initializeJSF(webappContext);
			initializeJersey(webappContext);


			server.setHandler(webappContext);

			// Start server ...
			server.start();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private void initializeJersey(WebAppContext webappContext) {
		ServletHolder serHol = webappContext.addServlet(ServletContainer.class, "/rest/*");
		serHol.setInitOrder(1);
		serHol.setInitParameter("jersey.config.server.provider.packages", "org.Backend");
		serHol.setInitParameter("jersey.config.server.provider.classnames", "org.glassfish.jersey.jackson.JacksonFeature");
	

		// Enable Spring to inject beans into Jersey
		AnnotationConfigWebApplicationContext wac = new AnnotationConfigWebApplicationContext();
		wac.register(Config.class); 
		webappContext.addEventListener(new ContextLoaderListener(wac));

	}

	private void listen() {
		// Listen for connections ...
		try {
			server.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void initializeJSF(WebAppContext context) {

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
		context.setWelcomeFiles(new String[] { "index.xhtml" });
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

	@Override
	public void uncaughtException(Thread thread, Throwable e) {
		if (!GraphicsEnvironment.isHeadless()) {
			String message = "[" + e.getClass() + "] " + e.getMessage();
			JOptionPane.showMessageDialog(null, message, "An uncaught error occured!", JOptionPane.ERROR_MESSAGE);
		}
	}
}