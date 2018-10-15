package fr.nouas.main.action;



import javax.persistence.EntityManager;

import javax.persistence.EntityTransaction;

import javax.servlet.http.HttpServletRequest;



import fr.nouas.beans.Question;

import fr.nouas.beans.Questionnaire;

import fr.nouas.pojo.utils.Action;

import fr.nouas.utils.JpaUtil;



public class DeleteQuestion extends Action {

	@Override

	public boolean executeAction(HttpServletRequest request) {


		String idStr = request.getParameter("question");
		String idque = request.getParameter("questionnaire");
		System.out.println(idStr);
		if (idStr != null && idque != null) {
			try {
				int idquestion = Integer.parseInt(idStr);
				int idquestionnaire = Integer.parseInt(idque);

				EntityManager em = JpaUtil.getEntityManager();

				EntityTransaction tr = em.getTransaction();

				

				

				try {

					Question question = em.find(Question.class, idquestion);
					Questionnaire questionnaire = em.find(Questionnaire.class, idquestionnaire);
					questionnaire.deleteQuestion(question);
					question.setQuestionnaire(null);
					tr.begin();
					em.persist(questionnaire);
					em.persist(question);

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