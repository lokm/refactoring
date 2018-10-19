package fr.nouas.main.action;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import fr.nouas.beans.Question;
import fr.nouas.beans.Questionnaire;
import fr.nouas.beans.Reponse;
import fr.nouas.beans.User;
import fr.nouas.pojo.utils.Action;
import fr.nouas.utils.JpaUtil;

public class ValidQuestionnaire extends Action {

	@Override
	public boolean executeAction(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		// mettre leffacement de la session a pareil que sur le server.xml
		  
		
		 // verification si temps ok ?
		Date currentDate = new Date();

		long examMustBeDoneBy = (long) request.getSession().getAttribute("examMustBeDoneBy");
		Date examLimit = new Date (examMustBeDoneBy * 1000);
		if (currentDate.before(examLimit)){
		
			

			String idStr = request.getParameter("questionnaire");
			int idUser = Integer.parseInt(request.getSession().getAttribute("userid").toString());
			
			System.out.println(idStr);
			System.out.println(idUser);
			if (idStr != null) {
				System.out.println("tesy 1");
					int id = Integer.parseInt(idStr);
					
					EntityManager em = JpaUtil.getEntityManager();
					EntityTransaction tr = em.getTransaction();
					
//					VERIFICATION VERSION EXISTANTE
					  int NewVersion = 0;
			    		 Query q = em.createQuery("SELECT R FROM Reponse R Where user_id=:idUser AND questionnaire_id=:idquestionnaire" 
			    		 			      		  +" AND version = (SELECT MAX(version) FROM Reponse WHERE user_id=:idUser AND questionnaire_id=:idquestionnaire )");
					   q.setParameter("idUser", idUser); 
					   q.setParameter("idquestionnaire", id);
							
			    	   List <Reponse> VersionReponses = (List <Reponse>) q.getResultList();
			    	   
			    	   if(!VersionReponses.isEmpty()) {
			    		   System.out.println("il y a une reponses");
			    		  
				    		 for ( Reponse reponse : VersionReponses) {
				    			 if(reponse.getVersion() > NewVersion) {
				    				 NewVersion = reponse.getVersion();
				    			 }
				    		 }
			    	   }	
			    	   System.out.println("NEW VERSION ::" + NewVersion );
					
						Questionnaire questionnaire = em.find(Questionnaire.class, id);
						String nbQuestion = request.getParameter("nbQuestion");
						
						User user = em.find(User.class, idUser);
						
					
						
												 
						 
						 
						 
						 
						 
						System.out.println(nbQuestion);
						
						if (nbQuestion != null) {
							int intNbQuestion= Integer.parseInt(request.getParameter("nbQuestion"));
							Reponse [] reponses = new Reponse [intNbQuestion];
							System.out.println("question : ");
							for (int i = 0; i < intNbQuestion; i++ ) {
								
								System.out.println("laaaaaaaaaaa : " + request.getParameter("reponseEleve"+(i+1)));
								//creation des reponses vide
								
								if((request.getParameter("reponseEleve"+(i+1))) == null ||  (request.getParameter("reponseEleve"+(i+1))) == "" ) {
									System.out.println("laaaaaaaaaaa DEDANS: " + request.getParameter("reponseEleve"+(i+1)));
									 reponses[i] = new Reponse (
											 "Pas Repondu", 
											 em.find(Question.class, Integer.parseInt(request.getParameter("question"+(i+1)))));
									
								}else {
								 reponses[i] = new Reponse (
										 request.getParameter("reponseEleve"+(i+1)), 
										 em.find(Question.class, Integer.parseInt(request.getParameter("question"+(i+1)))));
								}
								reponses[i].setUser(user);
								reponses[i].setQuestionnaire(questionnaire);
								
								System.out.println("VERSION //// " + NewVersion);
								reponses[i].setVersion(NewVersion + 1 );
								if(!questionnaire.getUsers().contains(user)) {
								questionnaire.addUser(user);
								}
									try {
										System.out.println("tesy 3");
										tr.begin();
										em.persist(reponses[i]);
										
								
										tr.commit();
									} catch (Exception e) {
										System.out.println("tesy 4");
										tr.rollback();
										e.printStackTrace();
									}
									
								
							}
						
								
								
							}
							
							
							
							
						
						
						
						
						
					
				}
			
			
				return true;
		}
			
			
			
		
		else {
			String nop = "Vous avez mit trop de temps a faire le questionnaire, Veuillez recommencer";
			request.getSession().setAttribute("validateToLate", nop);
			return true;
		}
		
}


	

}
			
	
	
		
			
		

		

