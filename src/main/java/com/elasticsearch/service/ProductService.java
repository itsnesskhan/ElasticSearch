package com.elasticsearch.service;

import com.elasticsearch.entity.Product;

import java.util.List;

public interface ProductService {

	Product addProduct(Product product);
	
	Iterable<Product> getAllProduct();
	
	Product getById(int id);
	
	Product updateProduct(Product product);
	
	void deleteProduct(int id);

	List<Product> findByNamePartialSearch(String name);
}
