// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package isep.rose.devstarter.domain;

import isep.rose.devstarter.domain.Enumeration;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

privileged aspect Enumeration_Roo_Jpa_Entity {
    
    declare @type: Enumeration: @Entity;
    
    declare @type: Enumeration: @Table(name = "ENUMERATION");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_ENUMERATION", columnDefinition = "INT")
    private Integer Enumeration.idEnumeration;
    
    public Integer Enumeration.getIdEnumeration() {
        return this.idEnumeration;
    }
    
    public void Enumeration.setIdEnumeration(Integer id) {
        this.idEnumeration = id;
    }
    
}
