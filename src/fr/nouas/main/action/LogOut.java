package fr.nouas.main.action;

import javax.servlet.http.HttpServletRequest;

import fr.nouas.pojo.utils.Action;

public class LogOut extends Action {

	@Override
	public boolean executeAction(HttpServletRequest request) {
		// deconnection = suppression du user
		        
				System.out.println("je me deconnecte");
		        request.getSession().setAttribute("user", null);
		        request.getSession().setAttribute("userid", null);
		        
		        
		        // redirection vers l'acceuil
		    
		    
		        
		        return false;
		        }
}
