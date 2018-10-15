package fr.nouas.main.action;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;

import fr.nouas.beans.Category;
import fr.nouas.beans.User;
import fr.nouas.pojo.utils.Action;
import fr.nouas.utils.JpaUtil;

public class EditCategory extends Action {
 // TODO Gerer edition de même couleur
	@Override
	public boolean executeAction(HttpServletRequest request) {
		if(request.getMethod().equals("GET")) {
			int id = Integer.parseInt(request.getParameter("categorie"));
			// renvoi de l'id
			System.out.println("dans le get");
			request.getSession().setAttribute("categoryid", id);
		}else {
			// connection a la base de donn�e, recuperation de l EMF grace a jpa util. la creation de EMF est faite au lancement de lappli7
			// grace a un ecouteur devenement	
			EntityManager em = JpaUtil.getEntityManager();
			EntityTransaction tr = em.getTransaction();
			
			int id = Integer.parseInt(request.getParameter("id"));
			
			// recuperation du livre a editer grace a l id
			Category category = em.find(Category.class, id);
			
			// edition grace au formulaire
			category.setName(request.getParameter("category-name"));
			category.setColor(request.getParameter("category-color"));
			 try {	
			tr.begin();
			em.merge(category);
			tr.commit();
			 request.getSession().removeAttribute("bugeditquestionnaire");
			request.getSession().setAttribute("categoryid", -1);
			 }
			 catch (Exception e) { 
			request.getSession().setAttribute("bugeditquestionnaire", "Veuillez choisir votre categorie");
			 e.printStackTrace();
			 }
	        
		     }
			return true;
	
         

     }
 

}
