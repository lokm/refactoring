package fr.nouas.main.action;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;

import fr.nouas.beans.Questionnaire;
import fr.nouas.pojo.utils.Action;
import fr.nouas.utils.JpaUtil;



public class DeleteQuestionnaire extends Action {
	@Override
	public boolean executeAction(HttpServletRequest request) {
		
		String idStr = request.getParameter("id");
		System.out.println(idStr);
		if (idStr != null) {
			try {
				int id = Integer.parseInt(idStr);
				
				EntityManager em = JpaUtil.getEntityManager();
				EntityTransaction tr = em.getTransaction();
				
				
				try {
					Questionnaire questionnaire = em.find(Questionnaire.class, id);
					tr.begin();
					em.remove(questionnaire);
					tr.commit();
				} catch (Exception e) {
					tr.rollback();
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
}
