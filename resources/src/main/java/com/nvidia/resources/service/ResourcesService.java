package com.nvidia.resources.service;

import java.util.List;
import java.util.Map;

import com.nvidia.resources.model.Resource;

public interface ResourcesService extends GenericService<Resource, Long> {
	List<Map<String, String>> findAllResources(Long resourceType, Long countryLocaleId);

	List<Map<String, String>> searchResources(List<Long> resourceIds, Long resourceType, Long countryLocaleId,
			String search);
}