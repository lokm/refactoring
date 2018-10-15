package fr.nouas.main.action;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import fr.nouas.beans.Category;
import fr.nouas.pojo.utils.Action;
import fr.nouas.utils.JpaUtil;

public class HomeAction extends Action {

	@Override
	public boolean executeAction(HttpServletRequest request) {
	
		
		EntityManager em = JpaUtil.getEntityManager();
		
		// selection de toutes les categories
		Query q = em.createQuery("SELECT c FROM Category c");
	
		List<Category> categories = q.getResultList();
		
		// renvoi des categories
		request.getSession().setAttribute("categories", categories);
	
		 request.getSession().setAttribute("checkVersion", null);
		 request.getSession().setAttribute("userToCheck", null);
		 request.getSession().setAttribute("VersionMaxUser", null);
		 request.getSession().setAttribute("bonneReponsesUser", null);
		 
		 
		 
		return true;

	}

}
