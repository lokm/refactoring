package fr.nouas.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppListener implements ServletContextListener {
	private static EntityManagerFactory emf;
	
	
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		if(emf != null && emf.isOpen()) {
			emf.close();
		}
		 System.out.println("application detruite");
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("application ouverte");
		
		// creation de lentity manager factory au lancement de lapplication
		 emf = Persistence.createEntityManagerFactory("questionnaire"); 
		
		
	}

}
