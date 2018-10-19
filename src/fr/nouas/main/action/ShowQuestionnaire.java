package fr.nouas.main.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import fr.nouas.beans.Questionnaire;
import fr.nouas.beans.Reponse;
import fr.nouas.beans.User;
import fr.nouas.pojo.utils.Action;
import fr.nouas.utils.JpaUtil;

public class ShowQuestionnaire extends Action {

	@Override
	public boolean executeAction(HttpServletRequest request) {
		
		// mettre leffacement de la session a 0
		  request.getSession().setMaxInactiveInterval(-1);
		
		// effacement de l'erreur de la validation trop tard 
		request.getSession().setAttribute("validateToLate", null);

		int id = Integer.parseInt(request.getParameter("questionnaire"));
		Boolean newUser = Boolean.valueOf(request.getParameter("newUser"));
		System.out.println(newUser + "test");
		if (newUser == true) {
			request.getSession().setAttribute("userToCheck", null);
			System.out.println(newUser + "sa doit etre nul !");
		}
		String VersionSession = null;
		int idUser = Integer.parseInt(request.getSession().getAttribute("userid").toString());
		System.out.println("id user : " + idUser);
		EntityManager em = JpaUtil.getEntityManager();
		User user = em.find(User.class, idUser);

		if (request.getMethod().equals("POST")) {
			String checkVersion = request.getParameter("checkVersion");
			System.out.println("checkVersion : " + checkVersion);

			request.getSession().setAttribute("checkVersion", checkVersion);
		}

		try {
			VersionSession = request.getSession().getAttribute("checkVersion").toString();
		} catch (Exception e) {
			System.out.println("premier passage");
		}

		Questionnaire questionnaire = em.find(Questionnaire.class, id);

		
		
		// creation temps limite pour faire questionnaire
		long now = System.currentTimeMillis();
		long examMustBeDoneBy = now + questionnaire.getTimer() * 60 * 1000;
		
	
		
		request.getSession().setAttribute("examMustBeDoneBy", examMustBeDoneBy);
		request.getSession().setAttribute("now", now);
		
		

		request.setAttribute("questionnaire", questionnaire);

		// VERIFICATION QUESTIONNAIRY DONE :
		Boolean DoneQuestionnary = false;
		String chemin = "/Users/lokm/eclipse-workspace/questionnaire/WebContent/resources/img/";
		request.getSession().setAttribute("chemin", chemin);

		try {

			Query q, q1;

			// Recuperation du nombre de version existante
			q1 = em.createQuery("SELECT R FROM Reponse R Where user_id=:idUser AND questionnaire_id=:idquestionnaire"
					+ " AND version = (SELECT MAX(version) FROM Reponse WHERE user_id=:idUser AND questionnaire_id=:idquestionnaire )");
			q1.setParameter("idUser", idUser);
			q1.setParameter("idquestionnaire", id);
			List<Reponse> Versionreponses = (List<Reponse>) q1.getResultList();
			int SendVersion = 0;
			for (Reponse reponse : Versionreponses) {
				System.out.println("DANS LES VERSIONS  " + reponse.getVersion());
				if (reponse.getVersion() > SendVersion) {
					SendVersion = reponse.getVersion();
				}
			}
			try {
				request.setAttribute("lastVersion", SendVersion);
				System.out.println("DANS LE MAUVAIS  " + SendVersion);
			} catch (Exception e) {
				System.out.println("DANS LE MAUVAIS CATCH pas de lastversion");
			}

			// Si l'utilisateur veux une version particuliere
			if (VersionSession != null) {
				q = em.createQuery(
						"SELECT R FROM Reponse R Where user_id=:idUser AND questionnaire_id=:idquestionnaire AND version =:checkVersion");
				q.setParameter("checkVersion", Integer.parseInt(VersionSession));
				System.out.println("dans le versionSession pas egale nul" + Integer.parseInt(VersionSession));
			} // Si derniere version
			else {
				q = em.createQuery("SELECT R FROM Reponse R Where user_id=:idUser AND questionnaire_id=:idquestionnaire"
						+ " AND version = (SELECT MAX(version) FROM Reponse WHERE user_id=:idUser AND questionnaire_id=:idquestionnaire )");
				System.out.println("dans le versionSession  egale nul");
			}
			System.out.println("idquesitionnaire " + id);
			System.out.println("iduser " + idUser);
			q.setParameter("idUser", idUser);
			q.setParameter("idquestionnaire", id);

			List<Reponse> reponses = (List<Reponse>) q.getResultList();

			if (!reponses.isEmpty()) {
				DoneQuestionnary = true;
				System.out.println(
						"jai bien le check reponse ? : " + VersionSession + "et" + reponses.get(0).getVersion());

				request.setAttribute("reponses", reponses);
				System.out.println("JE SUISUIS JSDIOS KSDQP XKPQSDKSPJ OHCI");
			}

		}

		catch (Exception e) {
			System.out.println("DANS LE MAUVAIS CATCH ");
		}
		System.out.println("questionnaire fait? : " + DoneQuestionnary);
		request.setAttribute("DoneQuestionnary", DoneQuestionnary);

		Boolean AddNewVersion = false;
		// CREATION NOUVELLE VERSION

		String nextVersion = request.getParameter("nextVersion");
		System.out.println("VERSION A CREER : " + nextVersion);
		if (nextVersion != null) {
			AddNewVersion = true;

		}

		System.out.println("Ajout de Version : " + AddNewVersion);
		request.setAttribute("AddNewVersion", AddNewVersion);

		// RECUPERATION DES BONNES REPONSES
		if (DoneQuestionnary == true) {
			Query q3 = em.createQuery(
					"SELECT R FROM Reponse R Where" + " questionnaire_id=:idquestionnaire AND correct = true");

			q3.setParameter("idquestionnaire", id);
			List<Reponse> bonneReponses = (List<Reponse>) q3.getResultList();
			request.setAttribute("bonneReponses", bonneReponses);
			System.out.println("LA PREMIERE REPONSE : " + bonneReponses.get(0).getReponse());
		}

		return false;
	}
}
