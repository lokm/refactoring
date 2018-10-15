package fr.nouas.main.action;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import fr.nouas.beans.User;
import fr.nouas.pojo.utils.Action;
import fr.nouas.utils.JpaUtil;

public class CheckProfil extends Action {

	@Override
	public boolean executeAction(HttpServletRequest request) {
	
		int idStudent =	 Integer.parseInt(request.getParameter("profil"));
		
		 EntityManager em = JpaUtil.getEntityManager();
		 
		 User userTocheck = em.find(User.class, idStudent);
		 
		 request.setAttribute("usertocheck", userTocheck);
		 
		 return true;
	}

}
