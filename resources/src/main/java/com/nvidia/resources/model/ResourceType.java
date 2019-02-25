package com.nvidia.resources.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;


/**
 * The persistent class for the resource_types database table.
 * 
 */
@Entity
@Table(name="resource_types")
@Data
public class ResourceType implements Serializable {

	private static final long serialVersionUID = 1078956644437747330L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="RESOURCE_TYPE_NAME")
	private String resourceTypeName;

	@Column(name="UNIQUE_PARAMETER")
	private String uniqueParameter;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getResourceTypeName() {
		return resourceTypeName;
	}

	public void setResourceTypeName(String resourceTypeName) {
		this.resourceTypeName = resourceTypeName;
	}

	public String getUniqueParameter() {
		return uniqueParameter;
	}

	public void setUniqueParameter(String uniqueParameter) {
		this.uniqueParameter = uniqueParameter;
	}

}