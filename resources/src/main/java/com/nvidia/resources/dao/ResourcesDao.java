package com.nvidia.resources.dao;

import java.util.List;

import com.nvidia.resources.model.Resource;

public abstract interface ResourcesDao extends GenericDao<Resource, Long> {
	public abstract List<Resource> findAllResources(Long resourceType, Long countryLocaleId);

	public abstract List<Resource> findAll(List<Long> resourceIds, Long resourceType, Long countryLocaleId);
}
