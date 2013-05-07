package isep.rose.devstarter.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import javax.persistence.Query;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "USER")
@RooDbManaged(automaticallyDelete = true)
public class User {
	
	
	public User getUserByEmail(String email) {
		List<User> users= new  ArrayList<User>();
		Query query = entityManager().createQuery("select user from User user " + 
                "where email = :email", User.class) ;
		query.setParameter("email", email);
		users= query.getResultList();
		if(users.isEmpty()){
			return null;
		}
        return users.get(0);
    }
}
