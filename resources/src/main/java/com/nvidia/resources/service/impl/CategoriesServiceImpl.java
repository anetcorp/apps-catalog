package com.nvidia.resources.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import com.nvidia.resources.dao.ProductCategoriesDao;
import com.nvidia.resources.model.Category;
import com.nvidia.resources.service.CategoriesService;

@Service
public class CategoriesServiceImpl extends GenericServiceImpl<Category, Long> implements CategoriesService {

	@Autowired
	private ProductCategoriesDao productCategoriesDao;

	@Override
	@Cacheable("productCategories")	
	public Map<String, List<Map<String, Object>>> findAll(Long productId) {
		List<Map<String, Object>> categoryList = new ArrayList<>();
		this.productCategoriesDao.findAll(productId).forEach(productCategory -> {
			Map<String, Object> categoryMap = getCategoryMap(categoryList,
					productCategory.getCategory().getCategoryName());
			if (categoryMap != null) {
				@SuppressWarnings("unchecked")
				List<Long> resourceIds = (List<Long>) categoryMap.get("resourceIds");
				resourceIds
						.addAll(productCategoriesDao.getResourceIds(productCategory.getProduct().getId(), productCategory.getCategory().getId()));
				categoryMap.put("resourceIds", resourceIds.stream().distinct().collect(Collectors.toList()));
			} else {
				categoryMap = new HashMap<>();
				categoryMap.put("id", productCategory.getCategory().getId());
				categoryMap.put("name", productCategory.getCategory().getCategoryName());
				categoryMap.put("resourceIds",
						productCategoriesDao.getResourceIds(productCategory.getProduct().getId(), productCategory.getCategory().getId()));
				categoryList.add(categoryMap);
			}
		});
		Map<String, List<Map<String, Object>>> map = new HashMap<>();
		/* Sort list by product name */
		Collections.sort(categoryList, (lhs, rhs) -> {
			return lhs.get("name").toString().compareTo(rhs.get("name").toString());
		});
		map.put("categories", categoryList);
		return map;
	}

	Map<String, Object> getCategoryMap(List<Map<String, Object>> categoryList, String name) {
		Map<String, Object> categoryMap = null;
		for (Map<String, Object> catyMap : categoryList) {
			if (catyMap.get("name").equals(name)) {
				categoryMap = catyMap;
				break;
			}
		}
		return categoryMap;
	}

}
