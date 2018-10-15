package fr.nouas.main.action;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import fr.nouas.beans.User;
import fr.nouas.pojo.utils.Action;
import fr.nouas.pojo.utils.Md5;
import fr.nouas.utils.JpaUtil;

public class SignIn extends Action {


	@Override
    public boolean executeAction(HttpServletRequest request) {
                
	
		
       // if(request.getMethod().equals("POST")) {
            
       // Recuperation des donnees utilisateur    
            /*
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String role = request.getParameter("role");
        int userId = Integer.parseInt(request.getParameter("userId"));
        */

        	String firstname = "youcef";
            String lastname = "Handoura";
            String role = "superAdmin";
            int userId = 5;
            
            String tokenSend = request.getParameter("token");
            
            // creation cle decryptage
            
            char tokenKey [] = { 
            		tokenSend.charAt(0),
            		tokenSend.charAt(1),
            		tokenSend.charAt(2),
            		tokenSend.charAt(3)
            };
           
            String key = "";
            
            for (int i=0; i>4; i++) {
            	switch(tokenKey[i]) {
            	      case 0 :
            	      key += request.getParameter("nom");
            	      break;
            	      
            	      case 1 :
                      key += request.getParameter("prenom");
                	  break;
                	  
            	      case 2 :
                	  key += request.getParameter("type");
                	  break;
                	  
            	      case 3 :
                	  key += request.getParameter("id");
                	  break;
                	          
            	}
            };
            Md5  tF =  new Md5(key); 
            String tokenFind = tokenSend.substring(0, 3)+tF.codeGet();
            
            if(tokenSend == tokenFind) {
        
            
            boolean redirect = false;
            
     
        if(firstname != null && lastname != null && !firstname.isEmpty() && !lastname.isEmpty()) {
            
        	// login //
            
            EntityManager em = JpaUtil.getEntityManager();
                   
           
            
            
            try {
            	Query q = em.createQuery("SELECT u FROM User AS u Where u.userId=:userId"); 
                q.setParameter("userId", userId);
                User user = (User) q.getSingleResult();
                // on envoi lutilisateur
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("userid", user.getId());
                request.getSession().removeAttribute("usererror");
                // on redirige sur books si la connection est ok
                return true;
            } catch (Exception e) {
                // si les parametres de connections sont faux.
                request.getSession().setAttribute("usererror", "Pseudo ou mot de passe incorrect...");
                e.printStackTrace();
            }
        	
            // signin //
        
            User user = new User(lastname, firstname, role, userId);
        
            EntityTransaction transaction = em.getTransaction();
            redirect = false;
            
            try {
                transaction.begin();
                em.persist(user);
                transaction.commit();
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("userid", user.getId());
                redirect = true;
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
            }
           
            em.close();
            return redirect;
          }
        
        //}
      
    }
            return true;
	}
}

