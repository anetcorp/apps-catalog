package com.nvidia.resources.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nvidia.resources.dao.ProductsDao;
import com.nvidia.resources.model.Product;
import com.nvidia.resources.service.ProductsService;

@Service
public class ProductServiceImpl extends GenericServiceImpl<Product, Long> implements ProductsService {

	@Autowired
	private ProductsDao productsDao;

	@Override
	public Map<String, List<Map<String, Object>>> findAllProducts() {
		List<Map<String, Object>> productList = new ArrayList<>();
		this.productsDao.findAll().forEach(product -> {
			Map<String, Object> productMap = new HashMap<>();
			productMap.put("id", product.getId());
			productMap.put("name", product.getProductName());
			productList.add(productMap);
		});

		Map<String, List<Map<String, Object>>> map = new HashMap<>();

		/* Sort list by product name */
		Collections.sort(productList, (lhs, rhs) -> {
			return lhs.get("name").toString().compareTo(rhs.get("name").toString());
		});
		map.put("products", productList);
		return map;
	}
}