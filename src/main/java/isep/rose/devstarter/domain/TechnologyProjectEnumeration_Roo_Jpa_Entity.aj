// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package isep.rose.devstarter.domain;

import isep.rose.devstarter.domain.TechnologyProjectEnumeration;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

privileged aspect TechnologyProjectEnumeration_Roo_Jpa_Entity {
    
    declare @type: TechnologyProjectEnumeration: @Entity;
    
    declare @type: TechnologyProjectEnumeration: @Table(name = "TECHNOLOGY_PROJECT_ENUMERATION");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_TECHNOLOGY", columnDefinition = "INT")
    private Integer TechnologyProjectEnumeration.idTechnology;
    
    public Integer TechnologyProjectEnumeration.getIdTechnology() {
        return this.idTechnology;
    }
    
    public void TechnologyProjectEnumeration.setIdTechnology(Integer id) {
        this.idTechnology = id;
    }
    
}
