import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webfirmframework.wffweb.tag.html.P;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.htmlwff.NoTag;

@WebServlet("/output")
public class Output extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(Output.class.getName());
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("contextPath", Config.getValue("truckaudit.contextPath"));
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(buildFragment(BannerEntry.getAllActive()));
		out.flush();
	}
	
	private String buildFragment(ArrayList<BannerEntry> beList) {
		if (beList.size() == 0) {
			return "";
		}
		else {
			NoTag containerDiv = new NoTag(null);
			
			for (BannerEntry be : beList) {
				containerDiv.appendChild(buildAlert(be));
			}
			
			return containerDiv.toHtmlString();
		}
	}
	
	private Div buildAlert(BannerEntry be) {
		String alertType = BannerEntry.TYPES[be.getType()].split(" ")[0];
		
		ClassAttribute classes = new ClassAttribute();
		List<String> classList = Arrays.asList("alert", String.format("alert-%s", alertType.toLowerCase()));
		classes.addAllClassNames(classList);
		
		Div alertDiv = new Div(null, classes);
		Div innerDiv = new Div(alertDiv, new ClassAttribute("container text-center"));
		
		// for Danger and Warning, no icon for Info
		if (be.getType() < 2) {
			new NoTag(innerDiv, String.format("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\" fill=\"currentColor\" class=\"bi bi-exclamation-triangle-fill flex-shrink-0 me-2\" viewBox=\"0 0 16 16\" role=\"img\" aria-label=\"%s:\"><path d=\"M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z\"/></svg>", alertType));
		}
		else {
			new NoTag(innerDiv, "<svg height=\"24\" width=\"24\"></svg>");
		}
		
		P messageContainer = new P(innerDiv);
		new NoTag(messageContainer, be.getMessage());
		
		return alertDiv;
	}
}
