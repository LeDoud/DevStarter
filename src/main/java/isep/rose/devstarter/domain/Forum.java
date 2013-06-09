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
        Query query = entityManager().createQuery("select topics from Forum topics " + "where parentId = :parentId"+ " ORDER BY dateCreated DESC" , Forum.class);
        query.setParameter("parentId", 0);
        topics = query.getResultList();
        if (topics.isEmpty()) {
            return null;
        }
        return topics;
    }
    
    public static List<isep.rose.devstarter.domain.Forum > findAllMessageTopic(int topicId) {
        List<Forum > messages = new ArrayList<Forum >();
        Query query = entityManager().createQuery("select topics from Forum topics " + "where parentId = :parentId" +" ORDER BY dateCreated" , Forum.class);
        query.setParameter("parentId", topicId);
        messages = query.getResultList();
        if (messages.isEmpty()) {
            return null;
        }
        return messages;
    }
    
    public static int findCountMessageTopic(int topicId) {
        List<Forum > messages = new ArrayList<Forum >();
        Query query = entityManager().createQuery("select topics from Forum topics " + "where parentId = :parentId" +" ORDER BY dateCreated" , Forum.class);
        query.setParameter("parentId", topicId);
        messages = query.getResultList();
        if (messages.isEmpty()) {
            return 0;
        }
        return Integer.valueOf(messages.size());
    }
    
    public static isep.rose.devstarter.domain.Forum findLastMessageTopic(int topicId) {
        List<Forum > messages = new ArrayList<Forum >();
        Query query = entityManager().createQuery("select topics from Forum topics " + "where parentId = :parentId or idForum = :parentId" +" ORDER BY dateCreated DESC" , Forum.class);
        query.setParameter("parentId", topicId);
        messages = query.getResultList();
        if (messages.isEmpty()) {
            return null;
        }
        return messages.get(0);
    }
}
