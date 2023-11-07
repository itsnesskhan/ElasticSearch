package com.elasticsearch.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.elasticsearch.entity.Product;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product, Integer> {

    List<Product> findByNameContaining(String name);
}
