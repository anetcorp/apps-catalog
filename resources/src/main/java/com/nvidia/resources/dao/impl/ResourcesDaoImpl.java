package com.nvidia.resources.dao.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.nvidia.resources.dao.ResourcesDao;
import com.nvidia.resources.model.Resource;

@Repository
public class ResourcesDaoImpl extends GenericDaoJpaImpl<Resource, Long> implements ResourcesDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Resource> findAllResources(Long resourceType, Long countryLocaleId) {
		Query query = this.entityManager.createQuery(
				"FROM Resource resource WHERE resource.resourceType.id=:resourceTypeId and resource.countryLocaleId=:countryLocaleId");
		query.setParameter("resourceTypeId", resourceType);
		query.setParameter("countryLocaleId", countryLocaleId);

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Resource> findAll(List<Long> resourceIds, Long resourceType, Long countryLocaleId) {
		StringBuilder strQuery = new StringBuilder(
				"FROM Resource resource WHERE resource.resourceType.id=:resourceTypeId and resource.countryLocaleId=:countryLocaleId");

		boolean isResourceIdsExists = Optional.ofNullable(resourceIds).isPresent() && !resourceIds.isEmpty();
		if (isResourceIdsExists) {
			strQuery.append(" and resource.id in (:idList)");
		}
		Query query = this.entityManager.createQuery(strQuery.toString());
		query.setParameter("resourceTypeId", resourceType);
		query.setParameter("countryLocaleId", countryLocaleId);

		if (isResourceIdsExists) {
			query.setParameter("idList", resourceIds);
		}
		return query.getResultList();
	}

}