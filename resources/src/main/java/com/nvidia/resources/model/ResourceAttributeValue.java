package com.nvidia.resources.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

/**
 * The persistent class for the resource_attribute_value database table.
 * 
 */
@Entity
@Table(name = "resource_attribute_value")
@Data
public class ResourceAttributeValue implements Serializable {

	private static final long serialVersionUID = 555273236125268258L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "RESOURCE_ATTRIBUTE")
	private String resourceAttribute;

	@Lob
	@Column(name = "RESOURCE_ATTRIBUTE_VALUE")
	private String resAttributeValue;

	@Column(name = "RESOURCE_ID")
	private Long resourceId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResourceAttribute() {
		return resourceAttribute;
	}

	public void setResourceAttribute(String resourceAttribute) {
		this.resourceAttribute = resourceAttribute;
	}

	public String getResAttributeValue() {
		return resAttributeValue;
	}

	public void setResAttributeValue(String resAttributeValue) {
		this.resAttributeValue = resAttributeValue;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

}