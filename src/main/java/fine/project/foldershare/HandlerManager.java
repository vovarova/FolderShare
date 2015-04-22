package fine.project.foldershare;

import java.io.File;

public class HandlerManager {
	private HandlerManager() {
	}
	
	public static HttpHandler getHandler(String requestedFile){
		File rfile = new File(requestedFile);
		HttpHandler httpHandler = null;
		if(rfile.isDirectory()){
			httpHandler =new FolderHandler();
		}else{
			httpHandler =new FileHandler();
		}
		return httpHandler;
		
	}
}
