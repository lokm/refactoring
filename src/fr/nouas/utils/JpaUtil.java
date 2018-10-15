package fr.nouas.utils;

import javax.persistence.EntityManager;

public final class JpaUtil {
	
	private JpaUtil() {};
	
	public static EntityManager getEntityManager() {
		return AppListener.getEmf().createEntityManager();
	}
}
