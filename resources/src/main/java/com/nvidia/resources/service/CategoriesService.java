package com.nvidia.resources.service;

import java.util.List;
import java.util.Map;

import com.nvidia.resources.model.Category;

public interface CategoriesService extends GenericService<Category, Long> {
	 Map<String, List<Map<String, Object>>> findAll(Long paramLong);
}
