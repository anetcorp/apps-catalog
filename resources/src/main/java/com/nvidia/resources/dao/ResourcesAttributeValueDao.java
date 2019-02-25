package com.nvidia.resources.dao;

import java.util.List;

import com.nvidia.resources.model.ResourceAttributeValue;

public abstract interface ResourcesAttributeValueDao extends GenericDao<ResourceAttributeValue, Long> {
	public abstract List<ResourceAttributeValue> findAllResourcesById(Long resourceId);

	public abstract List<Long> search(String search,List<Long> resourceIds);
}