package com.nvidia.resources.dao.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.nvidia.resources.dao.ProductCategoriesDao;
import com.nvidia.resources.model.ProductCategory;

@Repository
public class ProductCategoriesDaoImpl extends GenericDaoJpaImpl<ProductCategory, Long> implements ProductCategoriesDao {
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductCategory> findAll(Long productId) {
		StringBuilder strQuery = new StringBuilder("FROM ProductCategory productCategory");
		boolean isProductIdExists = Optional.ofNullable(productId).isPresent() && productId != -1;
		if (isProductIdExists) {
			strQuery.append(" WHERE productCategory.product.id=:productId");
		}
		Query query = this.entityManager.createQuery(strQuery.toString());

		if (isProductIdExists)
			query.setParameter("productId", (Object) productId);

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getResourceIds(Long productId, Long categoryId) {
		Query query = this.entityManager.createQuery(
				"select productCategory.resource.id FROM ProductCategory productCategory WHERE productCategory.product.id=:productId and productCategory.category.id=:categoryId");
		query.setParameter("productId", (Object) productId);
		query.setParameter("categoryId", (Object) categoryId);
		return query.getResultList();
	}

}
