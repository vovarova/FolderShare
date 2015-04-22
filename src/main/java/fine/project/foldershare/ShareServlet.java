package fine.project.foldershare;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class ShareServlet extends HttpServlet{
	private static final long serialVersionUID = -3140534066941303422L;
	private static final Logger LOGGER= Logger.getLogger(ShareServlet.class);
	private String rootDirectory;
	public ShareServlet(String rootDirectory) {
		this.rootDirectory = rootDirectory;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = URLDecoder.decode(req.getRequestURI(),"UTF-8");
		LOGGER.info("Get "+path);
		File requestedFile = new File(rootDirectory, path);
		String requestedFilePath = requestedFile.getAbsolutePath();
		if(requestedFile.exists()){
			HttpHandler handler = HandlerManager.getHandler(requestedFilePath);
			handler.handle(requestedFilePath,req, resp);
		}else{
			try {
				resp.sendError(404, "Not found "+path);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	public String getRootDirectory() {
		return rootDirectory;
	}
	public void setRootDirectory(String rootDirectory) {
		this.rootDirectory = rootDirectory;
	}

	
}
