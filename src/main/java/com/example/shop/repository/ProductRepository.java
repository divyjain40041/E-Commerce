package com.example.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.shop.models.Category;
import com.example.shop.models.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, Long>, CustomProductRepository{
	Page<Product> findByCategory(Category category,Pageable pageable);
}
