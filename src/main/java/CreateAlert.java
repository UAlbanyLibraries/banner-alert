import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/create")
@MultipartConfig
public class CreateAlert extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(CreateAlert.class.getName());
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean shibdOn = Boolean.parseBoolean(Config.getValue("shibdOn"));
		String netid = shibdOn ? ((String) request.getAttribute("eppn")).split("@")[0] : "libsys";
		
		String message = (String) request.getParameter("message");
		String endString = (String) request.getParameter("end");
		Date end = (endString == null || endString.length() == 0) ? null : Date.valueOf(request.getParameter("end"));
		Integer type = Integer.parseInt((String) request.getParameter("type"));
		
		BannerEntry.create(netid, type, message, true, end);
		
		JSONObject responsejson = new JSONObject();
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(responsejson.toString());
		out.flush();
	}
}