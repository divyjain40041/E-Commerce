package com.example.shop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.models.CartItem;
import com.example.shop.models.Product;
import com.example.shop.services.CartItemService;

@RestController
@CrossOrigin
@RequestMapping("/cart-item")
public class CartItemController {
	private CartItemService cartItemService;
	
	@Autowired
	public CartItemController(CartItemService cartItemService) {
		super();
		this.cartItemService = cartItemService;
	}
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Page<CartItem> getAll(@RequestParam(defaultValue = "0")int page, 
    							@RequestParam(defaultValue ="20")int size)
    {
    	return cartItemService.getAll(page, size);
    }
    @RequestMapping(value = "/customer/{username}", method = RequestMethod.GET)
    public List<Product> getAll(@PathVariable("username")String username)
    {
    	return cartItemService.getByCustomerId(username);
    }
    @RequestMapping(value ="/add/customer/{customerId}/product/{productId}", method = RequestMethod.POST)
    public void add(@PathVariable("customerId")long customerId,
    					  @PathVariable("productId")long productId)
    {
    	 cartItemService.add(customerId, productId);
    }
	@RequestMapping(value = "/add/product/{productId}", method = RequestMethod.POST)
	public ResponseEntity<?> add(@PathVariable("productId")long productId,
								@RequestBody RequestToken RequestToken)
	{
		try
		{
			this.cartItemService.add(productId, RequestToken.getToken());
			return ResponseEntity.ok("true");
		}
		catch(Exception exception)
		{
			System.out.println(exception.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}

class RequestToken
{
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
