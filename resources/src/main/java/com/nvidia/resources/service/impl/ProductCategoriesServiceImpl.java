package com.nvidia.resources.service.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nvidia.resources.dao.ProductCategoriesDao;
import com.nvidia.resources.model.ProductCategory;
import com.nvidia.resources.service.ProductCategoriesService;

@Service
public class ProductCategoriesServiceImpl extends GenericServiceImpl<ProductCategory, Long>
		implements ProductCategoriesService {

	@Autowired
	private ProductCategoriesDao productCategoriesDao;


	@Override
	public Map<Long, Map<String, Map<Long, String>>> findAllProductCategory() {
		Map<Long, Map<String, Map<Long, String>>> productCategoryMap = new LinkedHashMap<>();
		productCategoriesDao.findAll().forEach(productCategory -> {
			Map<String, Map<Long, String>> productMap = productCategoryMap.get(productCategory.getProduct().getId());
			if (productMap == null) {
				productMap = new LinkedHashMap<>();
				Map<Long, String> categoryMap = new LinkedHashMap<>();
				categoryMap.put(productCategory.getId(), productCategory.getCategory().getCategoryName());
				productMap.put(productCategory.getProduct().getProductName(), categoryMap);
				productCategoryMap.put(productCategory.getProduct().getId(), productMap);

			} else {
				Map<Long, String> categoryMap = productMap.get(productCategory.getProduct().getProductName());
				if (categoryMap != null) {
					categoryMap.put(productCategory.getId(), productCategory.getCategory().getCategoryName());
				} else {
					categoryMap = new LinkedHashMap<>();
					categoryMap.put(productCategory.getId(), productCategory.getCategory().getCategoryName());
					productMap.put(productCategory.getProduct().getProductName(), categoryMap);
				}
			}
		});
		return productCategoryMap;
	}
}