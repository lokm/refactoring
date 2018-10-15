package fr.nouas.main;
																																																																																																																																																													
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.nouas.pojo.utils.ActionManager;
		
@WebServlet(name="/FrontServlet",

		value= { "/home", "/addCategory", "/addQuestionnaire", "/questionnaire", "/editQuestionnaire", "/deleteQuestionnaire", 
				"/addQuestion", "/editQuestion", "/categorie", "/deleteQuestion",
				"/editCategory", "/deleteCategory", "/signIn", "/logIn", "/logOut", "/validQuestionnaire", "/checkReponse", "/checkProfil"}
		)
@MultipartConfig
public class FrontServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PAGE_HOME = "/WEB-INF/index.jsp";
	private static final String PAGE_QUESTIONNAIRE ="/WEB-INF/questionnaire.jsp";
	
    public FrontServlet() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* TODO: faire une modification */
		
		
		String actionName = getActionName(request);
		boolean redirect = ActionManager.getAction(actionName).executeAction(request);
		System.out.println("action name : " + actionName);
		request.setAttribute("actionName", actionName);
		
			if(redirect) {
				
				getServletContext().getRequestDispatcher(PAGE_HOME).forward(request, response);

			}else { 
				switch(actionName)
				{
				case "questionnaire":
				

					getServletContext().getRequestDispatcher(PAGE_QUESTIONNAIRE).forward(request, response);
					break;
				case "addQuestion":
					
					getServletContext().getRequestDispatcher(PAGE_QUESTIONNAIRE).forward(request, response);
					break;
				case "deleteQuestion":
					System.out.println("dans le case deletequestion " +request.getQueryString().substring((request.getQueryString().lastIndexOf("&")+ 1)));
					response.sendRedirect(request.getContextPath() +"/questionnaire?"+request.getQueryString().substring((request.getQueryString().lastIndexOf("&")+ 1)));
					break;
					
				case "editQuestion":
					System.out.println("dans le case editQuestion");
					response.sendRedirect(request.getContextPath() +"/questionnaire?"+request.getQueryString().substring((request.getQueryString().lastIndexOf("&")+ 1)));
					break;
					
				default :
					response.sendRedirect(request.getContextPath() +"/home");
				}
			}
		
	}

		
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("userID");
		System.out.println("user ID : " + userId);
		String actionName = getActionName(request);
		boolean redirect = ActionManager.getAction(actionName).executeAction(request);

		if (redirect) {

			response.sendRedirect(request.getContextPath() + "/home");
		} else {
			System.out.println("dans le switch: " + actionName);
			switch (actionName) {
			case "questionnaire":

				response.sendRedirect(request.getContextPath() + "/questionnaire?" + request.getQueryString());
				break;
			case "addQuestion":
				response.sendRedirect(request.getContextPath() + "/questionnaire?" + request.getQueryString());

				System.out.println("dans le case addquestion");
				break;
			case "checkReponse":
				response.sendRedirect(request.getContextPath() + "/questionnaire?" + request.getQueryString());

				System.out.println("dans le case addquestion");
				break;

			case "editQuestion":
				System.out.println("dans le case editQuestion");
				response.sendRedirect(request.getContextPath() + "/questionnaire?"
						+ request.getQueryString().substring((request.getQueryString().lastIndexOf("&") + 1)));

				break;

			}
		}
	}

	private String getActionName(HttpServletRequest req) {
		String uri = req.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/") + 1);
		return uri;
	}
	
	private int getQueryString(HttpServletRequest req) {
		int QueryString = Integer.parseInt(req.getQueryString());
		return QueryString;
	}

}