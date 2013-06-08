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
@RooJpaActiveRecord(versionField = "", table = "FORUM")
@RooDbManaged(automaticallyDelete = true)
public class Forum {
	
    public static List<isep.rose.devstarter.domain.Forum > findAllTopics() {
        List<Forum > topics = new ArrayList<Forum >();
        Query query = entityManager().createQuery("select topics from Forum topics " + "where parentId = :parentId" , Forum.class);
        query.setParameter("parentId", 0);
        topics = query.getResultList();
        if (topics.isEmpty()) {
            return null;
        }
        return topics;
    }
    
    public static List<isep.rose.devstarter.domain.Forum > findAllMessageTopic(int topicId) {
        List<Forum > topics = new ArrayList<Forum >();
        Query query = entityManager().createQuery("select topics from Forum topics " + "where parentId = :parentId" +" ORDER BY dateCreated" , Forum.class);
        query.setParameter("parentId", topicId);
        topics = query.getResultList();
        if (topics.isEmpty()) {
            return null;
        }
        return topics;
    }
}
