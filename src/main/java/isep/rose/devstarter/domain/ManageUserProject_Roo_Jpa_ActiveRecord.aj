// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package isep.rose.devstarter.domain;

import isep.rose.devstarter.domain.ManageUserProject;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ManageUserProject_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager ManageUserProject.entityManager;
    
    public static final EntityManager ManageUserProject.entityManager() {
        EntityManager em = new ManageUserProject().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long ManageUserProject.countManageUserProjects() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ManageUserProject o", Long.class).getSingleResult();
    }
    
    public static List<ManageUserProject> ManageUserProject.findAllManageUserProjects() {
        return entityManager().createQuery("SELECT o FROM ManageUserProject o", ManageUserProject.class).getResultList();
    }
    
    public static ManageUserProject ManageUserProject.findManageUserProject(Integer idManage) {
        if (idManage == null) return null;
        return entityManager().find(ManageUserProject.class, idManage);
    }
    
    public static List<ManageUserProject> ManageUserProject.findManageUserProjectEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM ManageUserProject o", ManageUserProject.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void ManageUserProject.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void ManageUserProject.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            ManageUserProject attached = ManageUserProject.findManageUserProject(this.idManage);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void ManageUserProject.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void ManageUserProject.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public ManageUserProject ManageUserProject.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ManageUserProject merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
