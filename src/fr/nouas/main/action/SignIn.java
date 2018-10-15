package fr.nouas.main.action;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.transform.Transformers;

import fr.nouas.beans.User;
import fr.nouas.pojo.utils.Action;
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
        	String firstname = "toto";
            String lastname = "titi";
            String role = "admin";
            int userId = 1;
            
            boolean redirect = false;
            
     
        if(firstname != null && lastname != null && !firstname.isEmpty() && !lastname.isEmpty()) {
            
        	// login //
            
            EntityManager em = JpaUtil.getEntityManager();
                   
            Query q = em.createQuery("SELECT u FROM User AS u Where u.userId=:userId"); 
            q.setParameter("userId", userId);
            
            
            try {
                User user = (User) q.getSingleResult();
                // on envoi lutilisateur
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("userid", user.getId());
                request.getSession().removeAttribute("usererror");
                // on redirige sur books si la connection est ok
                redirect = true;
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
        return true;
    }

}
