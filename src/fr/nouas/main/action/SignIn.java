package fr.nouas.main.action;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import fr.nouas.beans.User;
import fr.nouas.pojo.utils.Action;
import fr.nouas.pojo.utils.Md5;
import fr.nouas.utils.JpaUtil;
import sun.misc.BASE64Encoder;

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

       
            String lastname = "Bourai";
         	String firstname = "Ramdane";
            String role = "Admin";
            int userId = 18;
            


            
            Md5  test=  new Md5("BouraiAdminBouraiAdmin"); 
            
            String tokenSend = "0202BouraiAdminBouraiAdmin" + test.codeGet();
            
            // creation cle decryptage
            
            int tokenKey [] = { 
            		tokenSend.charAt(0) - '0',
            		tokenSend.charAt(1) - '0',
            		tokenSend.charAt(2)- '0',
            		tokenSend.charAt(3)- '0',
            };
           System.out.println(tokenKey[0] + "le 0 carac");
           System.out.println(tokenKey[1] + "le 1 carac");
            String key = "";
            
            for (int i=0; i<4; i++) {
            	  System.out.println(tokenKey[i]);
            	switch(tokenKey[i]) {
            	      case 0 :
            	      key += lastname;
            	      break;
            	      
            	      case 1 :
            	      System.out.println("cas 1");
            	      key += firstname;                 
                	  break;
                	  
            	      case 2 :
                	  key += role;
                	  break;
                	  
            	      case 3 :
                	  key += userId;
                	  break;
                	          
            	}
            };
            System.out.println(key);

                String hash = null;
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(key.getBytes("UTF-8"));
                    byte[] raw = md.digest();
                    hash = (new BASE64Encoder()).encode(raw);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        
  
           
            String tokenFind = tokenSend.substring(0, 4)+hash;
            System.out.println(tokenFind);
            System.out.println(tokenSend);
            
            if(tokenSend.equals( tokenFind)){
        
            
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

