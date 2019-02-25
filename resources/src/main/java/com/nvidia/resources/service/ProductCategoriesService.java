package com.nvidia.resources.service;

import java.util.Map;

import com.nvidia.resources.model.ProductCategory;

public interface ProductCategoriesService extends GenericService<ProductCategory, Long> {
	 Map<Long, Map<String, Map<Long, String>>> findAllProductCategory();
}
