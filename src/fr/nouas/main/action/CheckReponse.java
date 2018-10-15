package fr.nouas.main.action;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import fr.nouas.beans.Questionnaire;
import fr.nouas.beans.Reponse;
import fr.nouas.beans.User;
import fr.nouas.pojo.utils.Action;
import fr.nouas.utils.JpaUtil;

public class CheckReponse extends Action {

	@Override
	public boolean executeAction(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("questionnaire"));
		String VersionSession = null;
		int idUser = Integer.parseInt(request.getParameter("userForReponse"));

		EntityManager em = JpaUtil.getEntityManager();

		User userTocheck = em.find(User.class, idUser);
		request.getSession().setAttribute("userToCheck", userTocheck);
		String checkVersion = request.getParameter("checkVersion");
		System.out.println("DANS LE CHECK REPONSE LA OU IL FAUT");

		Questionnaire questionnaire = em.find(Questionnaire.class, id);

		request.setAttribute("questionnaire", questionnaire);
		Query q1;
		List<Reponse> ReponsesUser;
		if (checkVersion == null) {
			q1 = em.createQuery("SELECT R FROM Reponse R Where user_id=:idUser AND questionnaire_id=:idquestionnaire"
					+ " AND version = (SELECT MAX(version) FROM Reponse WHERE user_id=:idUser AND questionnaire_id=:idquestionnaire )");

			q1.setParameter("idUser", idUser);
			q1.setParameter("idquestionnaire", id);

			ReponsesUser = (List<Reponse>) q1.getResultList();
			int SendVersion = 0;
			for (Reponse reponse : ReponsesUser) {
				System.out.println("DANS LES VERSIONS  " + reponse.getVersion());

				if (reponse.getVersion() > SendVersion) {
					SendVersion = reponse.getVersion();
				}
				System.out.println("la derniere version a envoyer :" + SendVersion);
			}

			request.getSession().setAttribute("VersionMaxUser", SendVersion);

		} else {
			q1 = em.createQuery("SELECT R FROM Reponse R Where user_id=:idUser AND questionnaire_id=:idquestionnaire"
					+ " AND version=:checkVersion");
			q1.setParameter("checkVersion", Integer.parseInt(checkVersion));
			q1.setParameter("idUser", idUser);
			q1.setParameter("idquestionnaire", id);

			ReponsesUser = (List<Reponse>) q1.getResultList();
		}

		System.out.println("DANS LE REPONSE USER" + ReponsesUser.get(0).getReponse());
		request.getSession().setAttribute("ReponsesUser", ReponsesUser);

		// RECUPERATION BONNE REPONSES
		Query q3 = em
				.createQuery("SELECT R FROM Reponse R Where" + " questionnaire_id=:idquestionnaire AND correct = true");

		q3.setParameter("idquestionnaire", id);
		List<Reponse> bonneReponses = (List<Reponse>) q3.getResultList();
		request.getSession().setAttribute("bonneReponsesUser", bonneReponses);

		return false;
	}

}
