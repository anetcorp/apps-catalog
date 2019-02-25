package com.nvidia.resources.dao;

import java.util.List;

import com.nvidia.resources.model.ProductCategory;

public abstract interface ProductCategoriesDao extends GenericDao<ProductCategory, Long> {

	List<ProductCategory> findAll(Long productId);

	List<Long> getResourceIds(Long productId, Long categoryId);
}
