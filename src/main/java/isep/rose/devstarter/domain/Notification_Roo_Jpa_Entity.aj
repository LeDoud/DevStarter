// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package isep.rose.devstarter.domain;

import isep.rose.devstarter.domain.Notification;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

privileged aspect Notification_Roo_Jpa_Entity {
    
    declare @type: Notification: @Entity;
    
    declare @type: Notification: @Table(name = "NOTIFICATION");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_NOTIFICATION", columnDefinition = "INT")
    private Integer Notification.idNotification;
    
    public Integer Notification.getIdNotification() {
        return this.idNotification;
    }
    
    public void Notification.setIdNotification(Integer id) {
        this.idNotification = id;
    }
    
}
