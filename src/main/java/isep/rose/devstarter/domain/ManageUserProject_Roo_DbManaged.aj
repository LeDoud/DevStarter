// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package isep.rose.devstarter.domain;

import isep.rose.devstarter.domain.ManageUserProject;
import isep.rose.devstarter.domain.Project;
import isep.rose.devstarter.domain.User;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

privileged aspect ManageUserProject_Roo_DbManaged {
    
    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID_USER", nullable = false)
    private User ManageUserProject.userId;
    
    @ManyToOne
    @JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID_PROJECT", nullable = false)
    private Project ManageUserProject.projectId;
    
    public User ManageUserProject.getUserId() {
        return userId;
    }
    
    public void ManageUserProject.setUserId(User userId) {
        this.userId = userId;
    }
    
    public Project ManageUserProject.getProjectId() {
        return projectId;
    }
    
    public void ManageUserProject.setProjectId(Project projectId) {
        this.projectId = projectId;
    }
    
}
