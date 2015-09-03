package fine.project.foldershare;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class FileHandler implements HttpHandler {
	private static final Logger LOGGER= Logger.getLogger(FileHandler.class);

	public void handle(String path, HttpServletRequest req,
			HttpServletResponse resp) {

		File requstedFile = new File(path);
		if(!FileFilter.needToProcess(requstedFile)){
			try {
				resp.sendError(404, "Not found");
			} catch (IOException e) {
				LOGGER.error("Error whole executin "+requstedFile.getName());
			}
			return;
		}
		try {
			FileInputStream fileInputStream = new FileInputStream(requstedFile);
			String mimeType = Files.probeContentType(Paths.get(path));
			resp.setContentType(mimeType);
			resp.setContentLengthLong(requstedFile.length());
			IOUtils.copy(fileInputStream, resp.getOutputStream());
			fileInputStream.close();
		} catch (FileNotFoundException e) {
			try {
				resp.sendError(404, "Not found "+e.getLocalizedMessage());
			} catch (IOException e1) {
				LOGGER.error("Not found "+requstedFile.getName(), e1);
			}
		} catch (IOException e) {
			try {
				resp.sendError(400, e.getLocalizedMessage());
			} catch (IOException e1) {
				LOGGER.error("Error while executing "+requstedFile.getName(), e1);
			}
		}
		resp.setStatus(200);

	}

}
