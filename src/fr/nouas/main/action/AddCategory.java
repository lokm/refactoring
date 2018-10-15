package fr.nouas.main.action;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;

import fr.nouas.beans.Category;
import fr.nouas.pojo.utils.Action;
import fr.nouas.utils.JpaUtil;

public class AddCategory extends Action {

	@Override
	public boolean executeAction(HttpServletRequest request) {
		if (request.getMethod().equals("POST")) {
			Category category = new Category(
					request.getParameter("titre_categorie"), 
					request.getParameter("color")
					);

			EntityManager em = JpaUtil.getEntityManager();

			try {
				em.getTransaction().begin();
				em.persist(category);

				em.getTransaction().commit();
			} catch (RollbackException e) {
				em.getTransaction().rollback();
				e.printStackTrace();
			}
			em.close();

			return true;
		}
		return false;
	}

}
