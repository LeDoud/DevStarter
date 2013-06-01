// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package isep.rose.devstarter.domain;

import isep.rose.devstarter.domain.CommentUserProject;
import isep.rose.devstarter.domain.DonationUserProject;
import isep.rose.devstarter.domain.Enumeration;
import isep.rose.devstarter.domain.FollowUserProject;
import isep.rose.devstarter.domain.ManageUserProject;
import isep.rose.devstarter.domain.Project;
import isep.rose.devstarter.domain.TechnologyProjectEnumeration;
import isep.rose.devstarter.domain.UploadedFile;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

privileged aspect Project_Roo_DbManaged {
    
    @OneToMany(mappedBy = "projectId")
    private Set<CommentUserProject> Project.commentUserProjects;
    
    @OneToMany(mappedBy = "projectId")
    private Set<DonationUserProject> Project.donationUserProjects;
    
    @OneToMany(mappedBy = "projectId")
    private Set<FollowUserProject> Project.followUserProjects;
    
    @OneToMany(mappedBy = "projectId")
    private Set<ManageUserProject> Project.manageUserProjects;
    
    @OneToMany(mappedBy = "projectId")
    private Set<TechnologyProjectEnumeration> Project.technologyProjectEnumerations;
    
    @OneToMany(mappedBy = "projectId")
    private Set<UploadedFile> Project.uploadedFiles;
    
    @ManyToOne
    @JoinColumn(name = "TYPE_ID", referencedColumnName = "ID_ENUMERATION")
    private Enumeration Project.typeId;
    
    @Column(name = "NAME", columnDefinition = "VARCHAR", length = 255)
    private String Project.name;
    
    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String Project.description;
    
    @Column(name = "PICTURE_URL", columnDefinition = "VARCHAR", length = 255)
    private String Project.pictureUrl;
    
    @Column(name = "PICTURE_BYTES", columnDefinition = "LONGBLOB")
    private byte[] Project.pictureBytes;
    
    @Column(name = "START_DATE", columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date Project.startDate;
    
    @Column(name = "MIN_END_DATE", columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date Project.minEndDate;
    
    @Column(name = "EFFECTIVE_END_DATE", columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date Project.effectiveEndDate;
    
    @Column(name = "MAX_END_DATE", columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date Project.maxEndDate;
    
    @Column(name = "FUND", columnDefinition = "INT")
    private Integer Project.fund;
    
    @Column(name = "RANK", columnDefinition = "INT")
    private Integer Project.rank;
    
    @Column(name = "ACTIVE", columnDefinition = "INT")
    private Integer Project.active;
    
    @Column(name = "DATE_CREATED", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar Project.dateCreated;
    
    @Column(name = "DATE_UPDATED", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar Project.dateUpdated;
    
    public Set<CommentUserProject> Project.getCommentUserProjects() {
        return commentUserProjects;
    }
    
    public void Project.setCommentUserProjects(Set<CommentUserProject> commentUserProjects) {
        this.commentUserProjects = commentUserProjects;
    }
    
    public Set<DonationUserProject> Project.getDonationUserProjects() {
        return donationUserProjects;
    }
    
    public void Project.setDonationUserProjects(Set<DonationUserProject> donationUserProjects) {
        this.donationUserProjects = donationUserProjects;
    }
    
    public Set<FollowUserProject> Project.getFollowUserProjects() {
        return followUserProjects;
    }
    
    public void Project.setFollowUserProjects(Set<FollowUserProject> followUserProjects) {
        this.followUserProjects = followUserProjects;
    }
    
    public Set<ManageUserProject> Project.getManageUserProjects() {
        return manageUserProjects;
    }
    
    public void Project.setManageUserProjects(Set<ManageUserProject> manageUserProjects) {
        this.manageUserProjects = manageUserProjects;
    }
    
    public Set<TechnologyProjectEnumeration> Project.getTechnologyProjectEnumerations() {
        return technologyProjectEnumerations;
    }
    
    public void Project.setTechnologyProjectEnumerations(Set<TechnologyProjectEnumeration> technologyProjectEnumerations) {
        this.technologyProjectEnumerations = technologyProjectEnumerations;
    }
    
    public Set<UploadedFile> Project.getUploadedFiles() {
        return uploadedFiles;
    }
    
    public void Project.setUploadedFiles(Set<UploadedFile> uploadedFiles) {
        this.uploadedFiles = uploadedFiles;
    }
    
    public Enumeration Project.getTypeId() {
        return typeId;
    }
    
    public void Project.setTypeId(Enumeration typeId) {
        this.typeId = typeId;
    }
    
    public String Project.getName() {
        return name;
    }
    
    public void Project.setName(String name) {
        this.name = name;
    }
    
    public String Project.getDescription() {
        return description;
    }
    
    public void Project.setDescription(String description) {
        this.description = description;
    }
    
    public String Project.getPictureUrl() {
        return pictureUrl;
    }
    
    public void Project.setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
    
    public byte[] Project.getPictureBytes() {
        return pictureBytes;
    }
    
    public void Project.setPictureBytes(byte[] pictureBytes) {
        this.pictureBytes = pictureBytes;
    }
    
    public Date Project.getStartDate() {
        return startDate;
    }
    
    public void Project.setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date Project.getMinEndDate() {
        return minEndDate;
    }
    
    public void Project.setMinEndDate(Date minEndDate) {
        this.minEndDate = minEndDate;
    }
    
    public Date Project.getEffectiveEndDate() {
        return effectiveEndDate;
    }
    
    public void Project.setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }
    
    public Date Project.getMaxEndDate() {
        return maxEndDate;
    }
    
    public void Project.setMaxEndDate(Date maxEndDate) {
        this.maxEndDate = maxEndDate;
    }
    
    public Integer Project.getFund() {
        return fund;
    }
    
    public void Project.setFund(Integer fund) {
        this.fund = fund;
    }
    
    public Integer Project.getRank() {
        return rank;
    }
    
    public void Project.setRank(Integer rank) {
        this.rank = rank;
    }
    
    public Integer Project.getActive() {
        return active;
    }
    
    public void Project.setActive(Integer active) {
        this.active = active;
    }
    
    public Calendar Project.getDateCreated() {
        return dateCreated;
    }
    
    public void Project.setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    public Calendar Project.getDateUpdated() {
        return dateUpdated;
    }
    
    public void Project.setDateUpdated(Calendar dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
    
}
