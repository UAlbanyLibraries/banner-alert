import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(Dashboard.class.getName());
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		
		if (StringUtils.equals(type, "js")) {
			request.getRequestDispatcher("/WEB-INF/js/dashboard.js").forward(request, response);
		}
		else {
			request.getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject responsejson = new JSONObject();
		
		JSONArray typeArray = new JSONArray();
		Arrays.stream(BannerEntry.TYPES).forEach(type -> typeArray.put(type));
		responsejson.put("types", typeArray);
		
		JSONArray beArray = new JSONArray();
		BannerEntry.getAll().forEach(be -> beArray.put(be.toJSONObject()));
		responsejson.put("list", beArray);
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(responsejson.toString());
		out.flush();
	}
}
