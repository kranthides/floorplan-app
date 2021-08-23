package com.fieldwire.floorplan.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "project")
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true, ignoreUnknown=true)
public class Project {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	@Transient
	private String imageUrl; 
	
	@Column(nullable = false, updatable = false, name="creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
	private Date createdAt;

	
    @Column(name="last_update_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate  
	private Date updatedAt;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="project", cascade = CascadeType.ALL)
    @JsonManagedReference 
 	private List<FloorPlan> floorPlans;

	
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
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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
	public List<FloorPlan> getFloorPlans() {
		return floorPlans;
	}
	public void setFloorPlans(List<FloorPlan> floorPlans) {
		this.floorPlans = floorPlans;
	} 
	
	
	
}
