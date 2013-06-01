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
@RooJpaActiveRecord(versionField = "", table = "PROJECT")
@RooDbManaged(automaticallyDelete = true)
public class Project {

    public static List<isep.rose.devstarter.domain.Project> findTopProjects() {
        List<Project> projects = new ArrayList<Project>();
        Query query = entityManager().createQuery("select project from Project project where active=1 " + "ORDER BY rank DESC", Project.class);
        projects = query.getResultList();
        if (projects.isEmpty()) {
            return null;
        }
        return projects;
    }

    public static List<isep.rose.devstarter.domain.Project> findNewProjects() {
        List<Project> projects = new ArrayList<Project>();
        Query query = entityManager().createQuery("select project from Project project where active=1 " + "ORDER BY date_created DESC", Project.class);
        projects = query.getResultList();
        if (projects.isEmpty()) {
            return null;
        }
        return projects;
    }

    public static List<isep.rose.devstarter.domain.Project> findStartingSoonProjects() {
        List<Project> projects = new ArrayList<Project>();
        Query query = entityManager().createQuery("select project from Project project where active=1 " + "ORDER BY start_date", Project.class);
        projects = query.getResultList();
        if (projects.isEmpty()) {
            return null;
        }
        return projects;
    }

    public static List<isep.rose.devstarter.domain.Project> findFollowedProjects(Integer idUser) {
        List<Project> projects = new ArrayList<Project>();
        User user = User.findUser(idUser);
        Query query = entityManager().createQuery("select project from Project project where idProject IN (SELECT followed.projectId FROM FollowUserProject followed WHERE userId = :user) " + "ORDER BY start_date DESC", Project.class);
        query.setParameter("user", user);
        projects = query.getResultList();
        if (projects.isEmpty()) {
            return null;
        }
        return projects;
    }

    public static List<isep.rose.devstarter.domain.Project> findFundedProjects(Integer idUser) {
        List<Project> projects = new ArrayList<Project>();
        User user = User.findUser(idUser);
        Query query = entityManager().createQuery("select project from Project project where idProject IN (SELECT funded.projectId FROM DonationUserProject funded WHERE userId = :user) " + "ORDER BY start_date DESC", Project.class);
        query.setParameter("user", user);
        projects = query.getResultList();
        if (projects.isEmpty()) {
            return null;
        }
        return projects;
    }
}
