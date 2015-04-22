package fine.project.foldershare;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class ShareServer {
	private int port;
	private String rootDirectory;
	public ShareServer(int port, String rootDirectory) {
		this.port = port;
		this.rootDirectory = rootDirectory;
	}
	public void startServer() throws Exception{
		Server server = new Server(port);
		ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
 		context.addServlet(new ServletHolder(new ShareServlet(rootDirectory)), "/");
 		server.start();
	}
	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getRootDirectory() {
		return rootDirectory;
	}
	public void setRootDirectory(String rootDirectory) {
		this.rootDirectory = rootDirectory;
	}
	
	
}
