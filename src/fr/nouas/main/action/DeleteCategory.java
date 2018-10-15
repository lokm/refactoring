package fr.nouas.main.action;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;

import fr.nouas.beans.Category;
import fr.nouas.pojo.utils.Action;
import fr.nouas.utils.JpaUtil;

public class DeleteCategory extends Action {

	@Override
	public boolean executeAction(HttpServletRequest request) {
		
		if(request.getMethod().equals("GET")) {
			String idStr = request.getParameter("categorie"); 
				if(idStr != null) {
					
					System.out.println("je delete");
					try {
						int id = Integer.parseInt(idStr);
						// connection a la base de donnée, recuperation de l EMF grace a jpa util. la creation de EMF est faite au lancement de lappli7
						// grace a un ecouteur devenement	
						EntityManager em = JpaUtil.getEntityManager();
						EntityTransaction tr = em.getTransaction();
						
						Category category = em.find(Category.class, id);
						
						try{
							tr.begin();
							em.remove(category);
							tr.commit();
						}catch (Exception e) {
							tr.rollback();
							e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
				return false;
			} return false;
	} 
}
