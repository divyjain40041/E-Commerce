package com.example.shop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.shop.models.GroceryProduct;

@Repository
public interface GroceryProductRepository extends MongoRepository<GroceryProduct, Long>{

}
