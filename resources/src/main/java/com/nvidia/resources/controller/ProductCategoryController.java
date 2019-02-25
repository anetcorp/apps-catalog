package com.nvidia.resources.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nvidia.resources.service.CategoriesService;
import com.nvidia.resources.service.ProductsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({ "/products" })
@Api(tags = { "Product Categories Services" })
public class ProductCategoryController {
	@Autowired
	private ProductsService productsService;
	@Autowired
	private CategoriesService categoriesService;

	@RequestMapping(headers = { "Accept=application/json" }, method=RequestMethod.GET)
	@ApiOperation("This service returns the list of all products.")
	public ResponseEntity<Map<String, List<Map<String, Object>>>> findAllProducts() {
		return new ResponseEntity<>(this.productsService.findAllProducts(), HttpStatus.OK);
	}

	@RequestMapping(value = { "/categories" }, headers = { "Accept=application/json" }, method=RequestMethod.GET)
	@ApiOperation("This service returns the list of categories of specific product.")
	public ResponseEntity<Map<String, List<Map<String, Object>>>> findAllCategories(
			@RequestParam(value = "product_id", required = true) Long productId) {
		return new ResponseEntity<>(this.categoriesService.findAll(productId), HttpStatus.OK);
	}
}
