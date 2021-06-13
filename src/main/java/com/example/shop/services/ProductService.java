package com.example.shop.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.shop.models.Category;
import com.example.shop.models.Product;
import com.example.shop.repository.ProductRepository;

@Service
public class ProductService {
	private ProductRepository productRepository;
	
	@Autowired 
	public ProductService(ProductRepository productRepository)
	{
		this.productRepository =productRepository;
	}
	
	public Page<Product> getAll(int page, int size)
	{
		Pageable pageble = PageRequest.of(page, size);
		return productRepository.findAll(pageble);
	}
	public Page<Product> getByCategory(String category, int page, int size)
	{
		Pageable pageable = PageRequest.of(page, size);
		try
		{
		Category value = Category.valueOf(category.toUpperCase());
		return productRepository.findByCategory(value,pageable);
		}
		catch(Exception exception)
		{
			return productRepository.findByCategory(null, pageable);
		}
		
	}
	public Product getById(long id)
	{
		Optional product = this.productRepository.findById(id);
		if(product.isEmpty())
			return null;
		else
			return (Product)product.get();
	}

	public Page<Product> getAll(int page, int size, String sortOrder) {
		if(sortOrder.equalsIgnoreCase("none"))
		{
			return this.getAll(page, size);
		}
		else if(sortOrder.equalsIgnoreCase("ASC"))
		{
			Pageable pageable = PageRequest.of(page, size,Sort.Direction.ASC, "attributes.price");
			return this.productRepository.findAll(pageable);
		}
		else 
		{
			Pageable pageable = PageRequest.of(page, size, Direction.DESC,"attributes.price");
			return this.productRepository.findAll(pageable);
		}
	}
}
