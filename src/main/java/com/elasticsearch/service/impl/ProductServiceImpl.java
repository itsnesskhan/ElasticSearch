package com.elasticsearch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elasticsearch.entity.Product;
import com.elasticsearch.repository.ProductRepository;
import com.elasticsearch.service.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product addProduct(Product product) {
		Product save = productRepository.save(product);
		return save;
	}

	@Override
	public Iterable<Product> getAllProduct() {
		return productRepository.findAll();
	}

	@Override
	public Product getById(int id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Product not found with id : " + id));

	}

	@Override
	public Product updateProduct(Product updateproduct) {
		Product product = productRepository.findById(updateproduct.getId())
				.orElseThrow(() -> new RuntimeException("Product not found with id : " + updateproduct.getId()));
		product.setName(updateproduct.getName());
		product.setPrice(updateproduct.getPrice());
		product.setQty(updateproduct.getQty());
		return productRepository.save(product);
	}

	@Override
	public void deleteProduct(int id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Product not found with id : " + id));
		productRepository.delete(product);
	}

	@Override
	public List<Product> findByNamePartialSearch(String name) {
		List<Product> nameContaining = productRepository.findByNameContaining(name);
		return nameContaining;
	}


}
