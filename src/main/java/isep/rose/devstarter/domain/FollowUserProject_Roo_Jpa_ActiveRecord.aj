// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package isep.rose.devstarter.domain;

import isep.rose.devstarter.domain.FollowUserProject;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect FollowUserProject_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager FollowUserProject.entityManager;
    
    public static final EntityManager FollowUserProject.entityManager() {
        EntityManager em = new FollowUserProject().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long FollowUserProject.countFollowUserProjects() {
        return entityManager().createQuery("SELECT COUNT(o) FROM FollowUserProject o", Long.class).getSingleResult();
    }
    
    public static List<FollowUserProject> FollowUserProject.findAllFollowUserProjects() {
        return entityManager().createQuery("SELECT o FROM FollowUserProject o", FollowUserProject.class).getResultList();
    }
    
    public static FollowUserProject FollowUserProject.findFollowUserProject(Integer idFollow) {
        if (idFollow == null) return null;
        return entityManager().find(FollowUserProject.class, idFollow);
    }
    
    public static List<FollowUserProject> FollowUserProject.findFollowUserProjectEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM FollowUserProject o", FollowUserProject.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void FollowUserProject.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void FollowUserProject.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            FollowUserProject attached = FollowUserProject.findFollowUserProject(this.idFollow);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void FollowUserProject.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void FollowUserProject.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public FollowUserProject FollowUserProject.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        FollowUserProject merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
