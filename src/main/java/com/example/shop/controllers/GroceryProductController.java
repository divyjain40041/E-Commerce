package com.example.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.models.GroceryProduct;
import com.example.shop.services.GroceryProductService;

@RestController
@CrossOrigin
@RequestMapping("/grocery-product")
public class GroceryProductController {
	private GroceryProductService groceryProductService;
	
	@Autowired
	public GroceryProductController(GroceryProductService groceryProductService)
	{
		this.groceryProductService = groceryProductService;
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Slice<GroceryProduct> getAll(@RequestParam(defaultValue = "0")int page, 
										@RequestParam(defaultValue = "20")int size)
	{
		return groceryProductService.getAll(page, size);
	}
	
	
}
