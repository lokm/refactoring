package fr.nouas.main.action;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;

import fr.nouas.beans.Question;
import fr.nouas.beans.Reponse;
import fr.nouas.enums.TypeQuestion;
import fr.nouas.pojo.utils.Action;
import fr.nouas.utils.JpaUtil;

public class EditQuestion extends Action {
	
	int idQuestionnaire = 0;

	@Override
	public boolean executeAction(HttpServletRequest request) {
		if(request.getMethod().equals("GET")) {
			int id = Integer.parseInt(request.getParameter("question"));
			// renvoi de l'id
			System.out.println("dans le get");
			request.getSession().setAttribute("questionid", id);
		}else {
			// connection a la base de donn�e, recuperation de l EMF grace a jpa util. la creation de EMF est faite au lancement de lappli7
			// grace a un ecouteur devenement	
			EntityManager em = JpaUtil.getEntityManager();
			EntityTransaction tr = em.getTransaction();
			
			int id = Integer.parseInt(request.getParameter("question"));
			
			// recuperation du livre a editer grace a l id
			Question question = em.find(Question.class, id);
			// edition grace au formulaire
			question.setQuestion(request.getParameter("question-question"));
			if(question.getType() == TypeQuestion.valueOf("QCM")) {
				String reponses[] = {
						request.getParameter("question-reponse1"),
						request.getParameter("question-reponse2"),
						request.getParameter("question-reponse3"),
						request.getParameter("question-reponse4")
				};

				
				
				
				for(int i = 0; i<question.getReponses().size(); i++) {
					question.getReponses().get(i).setReponse(reponses[i]);
				}
				
			}
				else if (question.getType() == TypeQuestion.valueOf("QUESTION_SIMPLE")) {
				String reponseStr = request.getParameter("question-bonnereponse");
				question.getBonneReponse().setReponse(reponseStr);
			}
			System.out.println("JE VAIS TRY");
			try {
				tr.begin();
				em.persist(question);
				tr.commit();

				System.out.println("DSHDUIUISKDSUIDHUIDHUISHIDSHDQHISHS LAS");

				request.getSession().setAttribute("questionid", -1);				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("DANS LE CATCH");
			}
		}
			
			return false;
		}


}
