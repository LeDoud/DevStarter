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
@RooJpaActiveRecord(versionField = "", table = "FOLLOW_USER_PROJECT")
@RooDbManaged(automaticallyDelete = true)
public class FollowUserProject {

    public static List<isep.rose.devstarter.domain.FollowUserProject> findFollowUserProjectByUserId(User user, Project project) {
        List<FollowUserProject> followUserProject = new ArrayList<FollowUserProject>();
        Query query = entityManager().createQuery("select follow from FollowUserProject donation " + "where userId = :userId and projectId = :projectId", FollowUserProject.class);
        query.setParameter("userId", user);
        query.setParameter("projectId", project);
        followUserProject = query.getResultList();
        if (followUserProject.isEmpty()) {
            return null;
        }
        return followUserProject;
    }
}
