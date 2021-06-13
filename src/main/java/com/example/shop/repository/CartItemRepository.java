package com.example.shop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.shop.models.CartItem;
@Repository
public interface CartItemRepository extends MongoRepository<CartItem, String>{
	Page<CartItem> findByCustomerId(long customerId, Pageable pageable);
	List<CartItem> findByCustomerId(long customerId);
}
