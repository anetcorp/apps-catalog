package com.nvidia.resources.service.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nvidia.resources.dao.ResourcesAttributeValueDao;
import com.nvidia.resources.dao.ResourcesDao;
import com.nvidia.resources.model.Resource;
import com.nvidia.resources.service.ResourcesService;

@Service
public class ResourcesServiceImpl extends GenericServiceImpl<Resource, Long> implements ResourcesService {

	@Autowired
	private ResourcesDao resourcesDao;

	@Autowired
	private ResourcesAttributeValueDao resourcesAttributeValueDao;

	@Override
	@Transactional
	public List<Map<String, String>> findAllResources(Long resourceType, Long countryLocaleId) {
		List<Map<String, String>> resources = new LinkedList<>();
		resourcesDao.findAllResources(resourceType, countryLocaleId).forEach(resource -> {
			Map<String, String> resourceAttributeMap = new LinkedHashMap<>();
			resourcesAttributeValueDao.findAllResourcesById(resource.getId()).forEach(resourceAttributeValue -> {

				resourceAttributeMap.put(resourceAttributeValue.getResourceAttribute(),
						"supported_features".equalsIgnoreCase(resourceAttributeValue.getResourceAttribute())
								? resourceAttributeValue.getResAttributeValue().replaceAll("\n", "<br/>")
								: resourceAttributeValue.getResAttributeValue());
			});
			resources.add(resourceAttributeMap);
		});
		return resources;
	}

	@Override
	public List<Map<String, String>> searchResources(List<Long> rIds, Long resourceType, Long countryLocaleId,
			String search) {

		List<Map<String, String>> resources = new LinkedList<>();
		List<Long> resourceIds = resourcesAttributeValueDao.search(search, rIds);
		if (Optional.ofNullable(resourceIds).isPresent() && !resourceIds.isEmpty()) {
			resourcesDao.findAll(resourceIds, resourceType, countryLocaleId).forEach(resource -> {
				Map<String, String> resourceAttributeMap = new LinkedHashMap<>();
				resourcesAttributeValueDao.findAllResourcesById(resource.getId()).forEach(resourceAttributeValue -> {
					resourceAttributeMap.put(resourceAttributeValue.getResourceAttribute(),
							"supported_features".equalsIgnoreCase(resourceAttributeValue.getResourceAttribute())
									? resourceAttributeValue.getResAttributeValue().replaceAll("\n", "<br/>")
									: resourceAttributeValue.getResAttributeValue());
				});
				resources.add(resourceAttributeMap);
			});
		}
		return resources;
	}
}