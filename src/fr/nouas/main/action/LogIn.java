package fr.nouas.main.action;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import fr.nouas.beans.User;
import fr.nouas.pojo.utils.Action;
import fr.nouas.pojo.utils.Md5;
import fr.nouas.utils.JpaUtil;

public class LogIn extends Action {

	@Override
    public boolean executeAction(HttpServletRequest request) {
        
        

        if (request.getMethod().equals("POST")) {
            // en cas de post recuperation du pseudo et mot de passe 
            String firstname = request.getParameter("firstname");
            String password = request.getParameter("password");
            boolean redirect = false;
            if (firstname != null && password != null && !firstname.isEmpty() && !password.isEmpty()) {

                EntityManager em = JpaUtil.getEntityManager();
                Query q = em.createQuery("SELECT u FROM User u Where u.firstname=:fn AND u.password=:pwd"); 
                q.setParameter("fn", firstname); // on modifie la valeur de ps et on la remplace par pseudo // pour
                                                // empecher injection sql
                
                // hachage du mot de passe a verifier en base de donnee
                
                
                Md5 md = new Md5(password);
                password = md.codeGet();
                q.setParameter("pwd", password); // on modifie la valeur de pwd et on la remplace par password
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
                em.close();
                return redirect;
            }
        }
        return false;
    }

}
