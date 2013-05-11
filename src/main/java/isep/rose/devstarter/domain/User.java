package isep.rose.devstarter.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "USER")
@RooDbManaged(automaticallyDelete = true)
public class User {

    public isep.rose.devstarter.domain.User findUserByEmail(String email) {
        List<User> users = new ArrayList<User>();
        Query query = entityManager().createQuery("select user from User user " + "where email = :email", User.class);
        query.setParameter("email", email);
        users = query.getResultList();
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    public isep.rose.devstarter.domain.User getUserAfterAuthentification(String email, String password) {
        List<User> users = new ArrayList<User>();
        Query query = entityManager().createQuery("select user from User user " + "where email = :email" + " and password = :password", User.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        users = query.getResultList();
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }
}
