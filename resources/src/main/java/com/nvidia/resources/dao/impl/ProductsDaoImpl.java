package com.nvidia.resources.dao.impl;

import org.springframework.stereotype.Repository;

import com.nvidia.resources.dao.ProductsDao;
import com.nvidia.resources.model.Product;

@Repository
public class ProductsDaoImpl extends GenericDaoJpaImpl<Product, Long> implements ProductsDao {
}