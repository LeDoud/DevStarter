package isep.rose.devstarter.domain;

import java.util.ArrayList;
import java.util.Date;
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

	
	public static List<isep.rose.devstarter.domain.Project> searchProjects(String title,Date startDate,Date endDate,Integer application_type,String[] languagesArray,String[] frameworksArray) {
        List<Project> projects = new ArrayList<Project>();
        String strQuery="select project from Project project where ";
        
        title +="%";
        strQuery+="name LIKE :title AND ";
        if(startDate != null){
            strQuery+="startDate >= :startDate AND ";
        }
        if(endDate != null){
            strQuery+="effectiveEndDate <= :endDate AND ";
        }
        if(application_type != -1){
            strQuery+="typeId = :type AND ";
        }
        
        strQuery+="1=1 ORDER BY name DESC";
        Query query =entityManager().createQuery(strQuery, Project.class);
        query.setParameter("title", title);
        if(startDate != null){
        	query.setParameter("startDate", startDate);
        }
        if(endDate != null){
        	query.setParameter("endDate", endDate);
        }
        if(application_type != -1){
        	Enumeration type=Enumeration.findEnumeration(application_type);
        	query.setParameter("type", type);
        }
        projects = query.getResultList();
        if (projects.isEmpty()) {
            return null;
        }
        return projects;
    }
	
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
        Query query = entityManager().createQuery("select project from Project project where active=1 and start_date>=NOW() " + "ORDER BY start_date", Project.class);
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

    public static List<isep.rose.devstarter.domain.Project> findUserProjects(Integer idUser) {
        List<Project> projects = new ArrayList<Project>();
        User user = User.findUser(idUser);
        Query query = entityManager().createQuery("select project from Project project where idProject IN (SELECT managed.projectId FROM ManageUserProject managed WHERE userId = :user) " + "ORDER BY start_date DESC ", Project.class);
        query.setParameter("user", user);
        projects = query.getResultList();
        if (projects.isEmpty()) {
            return null;
        }
        return projects;
    }
}
