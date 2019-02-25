package com.nvidia.resources.service;

import java.util.List;
import java.util.Map;

import com.nvidia.resources.model.Product;

public abstract interface ProductsService extends GenericService<Product, Long> {
	public abstract Map<String, List<Map<String, Object>>> findAllProducts();
}
