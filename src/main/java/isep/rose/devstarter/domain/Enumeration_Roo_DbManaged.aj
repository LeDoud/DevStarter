// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package isep.rose.devstarter.domain;

import isep.rose.devstarter.domain.Enumeration;
import isep.rose.devstarter.domain.Forum;
import isep.rose.devstarter.domain.TechnologyProjectEnumeration;
import isep.rose.devstarter.domain.User;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.OneToMany;

privileged aspect Enumeration_Roo_DbManaged {
    
    @OneToMany(mappedBy = "typeEnumId")
    private Set<Forum> Enumeration.forums;
    
    @OneToMany(mappedBy = "technoEnumId")
    private Set<TechnologyProjectEnumeration> Enumeration.technologyProjectEnumerations;
    
    @OneToMany(mappedBy = "compteEnumId")
    private Set<User> Enumeration.users;
    
    @OneToMany(mappedBy = "jobEnumId")
    private Set<User> Enumeration.users1;
    
    @Column(name = "NAME", columnDefinition = "VARCHAR", length = 255)
    private String Enumeration.name;
    
    @Column(name = "VALUE", columnDefinition = "VARCHAR", length = 255)
    private String Enumeration.value;
    
    @Column(name = "RANK", columnDefinition = "INT")
    private Integer Enumeration.rank;
    
    @Column(name = "ACTIVE", columnDefinition = "INT")
    private Integer Enumeration.active;
    
    @Column(name = "PARENT_ID", columnDefinition = "INT")
    private Integer Enumeration.parentId;
    
    @Column(name = "TYPE_ID", columnDefinition = "INT")
    private Integer Enumeration.typeId;
    
    public Set<Forum> Enumeration.getForums() {
        return forums;
    }
    
    public void Enumeration.setForums(Set<Forum> forums) {
        this.forums = forums;
    }
    
    public Set<TechnologyProjectEnumeration> Enumeration.getTechnologyProjectEnumerations() {
        return technologyProjectEnumerations;
    }
    
    public void Enumeration.setTechnologyProjectEnumerations(Set<TechnologyProjectEnumeration> technologyProjectEnumerations) {
        this.technologyProjectEnumerations = technologyProjectEnumerations;
    }
    
    public Set<User> Enumeration.getUsers() {
        return users;
    }
    
    public void Enumeration.setUsers(Set<User> users) {
        this.users = users;
    }
    
    public Set<User> Enumeration.getUsers1() {
        return users1;
    }
    
    public void Enumeration.setUsers1(Set<User> users1) {
        this.users1 = users1;
    }
    
    public String Enumeration.getName() {
        return name;
    }
    
    public void Enumeration.setName(String name) {
        this.name = name;
    }
    
    public String Enumeration.getValue() {
        return value;
    }
    
    public void Enumeration.setValue(String value) {
        this.value = value;
    }
    
    public Integer Enumeration.getRank() {
        return rank;
    }
    
    public void Enumeration.setRank(Integer rank) {
        this.rank = rank;
    }
    
    public Integer Enumeration.getActive() {
        return active;
    }
    
    public void Enumeration.setActive(Integer active) {
        this.active = active;
    }
    
    public Integer Enumeration.getParentId() {
        return parentId;
    }
    
    public void Enumeration.setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    
    public Integer Enumeration.getTypeId() {
        return typeId;
    }
    
    public void Enumeration.setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
    
}
