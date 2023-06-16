package com.bantrab.proxylab.service;

import java.util.List;

import com.bantrab.proxylab.entity.Product;

public interface ProductService {
	List<Product> getAllProducts();
	Product getSingleProduct(int productId);
}
