package fr.nouas.main.action;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;

import fr.nouas.beans.Category;
import fr.nouas.beans.Questionnaire;
import fr.nouas.pojo.utils.Action;
import fr.nouas.utils.JpaUtil;

public class EditQuestionnaire extends Action {
	@Override
	public boolean executeAction(HttpServletRequest request) {
		
		if (request.getMethod().equals("GET")) {
			int id = Integer.parseInt(request.getParameter("id"));
			request.getSession().setAttribute("questionnaireid", id);
		} else {
			
			String categoryStr = request.getParameter("questionnaire-category");
			if( categoryStr != null) {
				
				EntityManager em = JpaUtil.getEntityManager();
				EntityTransaction tr = em.getTransaction();
				
				int id = Integer.parseInt(request.getParameter("id"));
				System.out.println("post_id : " + request.getParameter("id"));
				Questionnaire questionnaire = em.find(Questionnaire.class, id);
				
				System.out.println(categoryStr);
				int categoryId = Integer.parseInt(categoryStr);
				Category category = em.find(Category.class, categoryId);
				questionnaire.setName(request.getParameter("questionnaire-name"));
				questionnaire.setDescription(request.getParameter("questionnaire-description"));
				questionnaire.setCategory(category);
				
				try {
					tr.begin();
					em.persist(questionnaire);
					tr.commit();				
				} catch (RollbackException | java.lang.NumberFormatException e) {
					
					
					e.printStackTrace();
					em.getTransaction().rollback();
				}
	
				request.getSession().setAttribute("questionnaireid", -1);
				request.getSession().removeAttribute("editquestionnaireerror");
			}
			if( categoryStr == null) {
				request.getSession().setAttribute("editquestionnaireerror", "Veuillez choisir votre categorie");
				return true;
			}
			
		}
			return true;
		}
}