package fine.project.foldershare;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class FolderHandler implements HttpHandler {
	private static final Logger LOGGER= Logger.getLogger(FolderHandler.class);
	
	private static final String TEMPLETE = "<html>\n" + "<head>\n"
			+ "<title>FolderShare</title>\n" + "</head>\n" + "<body>\n"
			+ "<h2>%s</h2>\n" + "<hr size=\"2\"> " + "%s" + "<hr size=\"2\">"
			+ "</body>\n" + "</html>";

	public void handle(String path, HttpServletRequest req,
			HttpServletResponse resp) {
		File directory = new File(path);
		try {
			resp.setContentType("text/html");
			resp.setCharacterEncoding("UTF-8");
			PrintWriter writer = resp.getWriter();
			String generateLinks = generateLinks(req, directory);
			String content = String.format(TEMPLETE,
					"Current Directory " + req.getServletPath(), generateLinks);
			writer.write(content);

		} catch (IOException e) {
			try {
				resp.sendError(400, e.getLocalizedMessage());
			} catch (IOException e1) {
				LOGGER.error("Error while executing "+directory.getName(), e1);
			}
		}
		resp.setStatus(200);
	}

	private String generateLinks(HttpServletRequest request, File directory)
			throws MalformedURLException {
		StringBuilder generatedLinks = new StringBuilder();
		File[] listFiles = directory.listFiles();
		generatedLinks.append("<ul>");
		generatedLinks.append("<li><a href=\"../\">../</a></li>\n");

		if (listFiles != null && listFiles.length != 0)
			for (File file : listFiles) {
				String name = file.getName();

				if (file.isDirectory()) {
					name += "/";
				}
				URL urlLink = getAbsoluteLink(request, name);
				generatedLinks.append("<li>").append("<a href=\"")
						.append(urlLink.toExternalForm()).append("\">")
						.append(name).append("</a>").append("</li>")
						.append("\n");
			}

		generatedLinks.append("</ul>");
		return generatedLinks.toString();

	}

	private URL getAbsoluteLink(HttpServletRequest request, String name)
			throws MalformedURLException {
		String uri = request.getScheme()
				+ "://"
				+ request.getServerName()
				+ ("http".equals(request.getScheme())
						&& request.getServerPort() == 80
						|| "https".equals(request.getScheme())
						&& request.getServerPort() == 443 ? "" : ":"
						+ request.getServerPort())
				+ (request.getRequestURI().endsWith("/") ? request
						.getRequestURI() : request.getRequestURI() + "/");
		URL urlLink = new URL(new URL(uri), name);
		return urlLink;
	}

}
