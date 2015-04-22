package fine.project.foldershare;

import org.apache.log4j.Logger;


public class App {
	private static final Logger LOGGER= Logger.getLogger(App.class);
	public static void main(String[] args) throws Exception {
		String current = System.getProperty("user.dir");
        LOGGER.info("Current working directory to share : " + current);
        ShareServer shareServer = new ShareServer(8000, current);
        shareServer.startServer();
	}
}
