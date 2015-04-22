package fine.project.foldershare;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpHandler {
	void handle(String path,HttpServletRequest req, HttpServletResponse resp);
}
