package com.example.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.models.Product;
import com.example.shop.services.ProductService;

@RestController
@CrossOrigin
@RequestMapping("/product")
public class ProductController {
	private ProductService productService;
	
	@Autowired
	public ProductController(ProductService productService)
	{
		this.productService = productService;
	}
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Page<Product> getAll(@RequestParam(defaultValue = "0")int page, 
								@RequestParam(defaultValue ="20")int size,
								@RequestParam(defaultValue ="NONE")String sortOrder)
	{
		return productService.getAll(page, size, sortOrder);
	}
	@RequestMapping(value = "/category/{category}", method = RequestMethod.GET)
	public Page<Product> getByCategory(@PathVariable("category")String category,
								@RequestParam(defaultValue = "0")int page, 
								@RequestParam(defaultValue ="20")int size)
	{
		return productService.getByCategory(category, page, size);
	}
	@RequestMapping(value = "/product-id/{productId}", method = RequestMethod.GET)
	public ResponseEntity<Product> getById(@PathVariable("productId")long productId)
	{
		Product product = this.productService.getById(productId);
		if(product == null)
		{
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}
		else
		{
			return ResponseEntity.ok(product);
		}
	}
}
