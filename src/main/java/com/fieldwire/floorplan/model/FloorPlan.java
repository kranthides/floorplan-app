package com.fieldwire.floorplan.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "floorplan")
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true, ignoreUnknown=true)
public class FloorPlan {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	private String original; 
	private String thumbNail;	
	private String large; 
	 	
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name="project_id")
    @JsonBackReference
	private Project project;

	
	@Column(nullable = false, updatable = false, name="creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
	private Date createdAt;

	
    @Column(name="last_update_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate  
	private Date updatedAt;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Project getProject() {
		return project;
	}


	public void setProject(Project project) {
		this.project = project;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	public Date getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}


	public String getOriginal() {
		return original;
	}


	public void setOriginal(String original) {
		this.original = original;
	}


	public String getThumbNail() {
		return thumbNail;
	}


	public void setThumbNail(String thumbNail) {
		this.thumbNail = thumbNail;
	}


	public String getLarge() {
		return large;
	}


	public void setLarge(String large) {
		this.large = large;
	}


	@Override
	public String toString() {
		return "FloorPlan [id=" + id + ", name=" + name + ", original=" + original + ", thumbNail=" + thumbNail
				+ ", large=" + large + ", project=" + project + "]";
	}

 	
    
    
}
