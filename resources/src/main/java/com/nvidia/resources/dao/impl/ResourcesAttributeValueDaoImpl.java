package com.nvidia.resources.dao.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.nvidia.resources.dao.ResourcesAttributeValueDao;
import com.nvidia.resources.model.ResourceAttributeValue;

@Repository
public class ResourcesAttributeValueDaoImpl extends GenericDaoJpaImpl<ResourceAttributeValue, Long>
		implements ResourcesAttributeValueDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceAttributeValue> findAllResourcesById(Long resourceId) {
		Query query = this.entityManager
				.createQuery("FROM ResourceAttributeValue resource WHERE resource.resourceId=:resourceId");
		query.setParameter("resourceId", resourceId);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> search(String search, List<Long> resourceIds) {
		StringBuilder strQuery = new StringBuilder(
				"select distinct(resourceId) FROM ResourceAttributeValue resource WHERE resource.resAttributeValue like :search");
		boolean isResourceIdsExists = Optional.ofNullable(resourceIds).isPresent() && !resourceIds.isEmpty();
		if (isResourceIdsExists) {
			strQuery.append(" and resource.resourceId in(:resourceIds)");
		}
		Query query = this.entityManager.createQuery(strQuery.toString());
		query.setParameter("search", "%" + search + "%");
		if (isResourceIdsExists) {
			query.setParameter("resourceIds", resourceIds);
		}
		return query.getResultList();
	}
}