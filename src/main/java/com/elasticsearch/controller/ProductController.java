package com.elasticsearch.controller;

import com.elasticsearch.service.impl.ElasticSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import com.elasticsearch.entity.Product;
import com.elasticsearch.service.ProductService;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/product")
@RestController
@RequiredArgsConstructor
public class ProductController {
	
	@Autowired
	private final ProductService productService;

	@Autowired
	private final ElasticSearchService elasticSearchService;

	@PostMapping
	ResponseEntity<?> insertProduct(@RequestBody Product product){
		Product addProduct = productService.addProduct(product);
		return !ObjectUtils.isEmpty(addProduct)
				?ResponseEntity.created(null).body(addProduct)
				:ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
	}
	
	@GetMapping("/{id}")
	ResponseEntity<Product> getProductbyId(@PathVariable int id){
		Product product = productService.getById(id);
		return ResponseEntity.ok(product);
	}
	
	@GetMapping
	ResponseEntity<?> getAllProduct(){
		Iterable<Product> allProduct = productService.getAllProduct();
		return ResponseEntity.ok(allProduct);
	}
	
	@PutMapping
	ResponseEntity<Product> updateProduct(@RequestBody Product product){
		product = productService.updateProduct(product);
		return ResponseEntity.ok(product);
	}
	
	@DeleteMapping("/{id}")
	ResponseEntity<?> deleteProductbyId(@PathVariable int id){
		productService.deleteProduct(id);
		return ResponseEntity.ok("Product deleted succesfully!");
	}

	@GetMapping("/matchAll")
	ResponseEntity<?> getAllProductMatchAll() throws IOException {
		List<Product> products = elasticSearchService.matchAllService();
		return ResponseEntity.ok(products);
	}

	@GetMapping("/matchField")
	ResponseEntity<?> getProductsMatchField(
								@RequestParam(required = true) String field,
								@RequestParam(required = true) String value ) throws IOException {
		List<Product> products = elasticSearchService.matchFieldService(field, value);
		return ResponseEntity.ok(products);
	}

	@GetMapping("/name/{name}")
	ResponseEntity<?> getProductbyNamePartialSearch(@PathVariable String name){
		List<Product> products = productService.findByNamePartialSearch(name);
		return ResponseEntity.ok(products);
	}

	@GetMapping("/fuzzy/{name}")
	ResponseEntity<?> getProductbyNameFuzzy(@PathVariable String name,
											@RequestParam(required = false, defaultValue = "name") String sortBy) throws IOException {
		List<Product> products = elasticSearchService.fuzzayFiedMatchQuery(name, sortBy);
		return ResponseEntity.ok(products);
	}

}
