// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package isep.rose.devstarter.domain;

import isep.rose.devstarter.domain.Notification;
import isep.rose.devstarter.domain.User;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

privileged aspect Notification_Roo_DbManaged {
    
    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID_USER", nullable = false)
    private User Notification.userId;
    
    @Column(name = "TITLE", columnDefinition = "LONGTEXT")
    private String Notification.title;
    
    @Column(name = "MESSAGE", columnDefinition = "LONGTEXT")
    private String Notification.message;
    
    @Column(name = "DATE_CREATED", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date Notification.dateCreated;
    
    public User Notification.getUserId() {
        return userId;
    }
    
    public void Notification.setUserId(User userId) {
        this.userId = userId;
    }
    
    public String Notification.getTitle() {
        return title;
    }
    
    public void Notification.setTitle(String title) {
        this.title = title;
    }
    
    public String Notification.getMessage() {
        return message;
    }
    
    public void Notification.setMessage(String message) {
        this.message = message;
    }
    
    public Date Notification.getDateCreated() {
        return dateCreated;
    }
    
    public void Notification.setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    
}
