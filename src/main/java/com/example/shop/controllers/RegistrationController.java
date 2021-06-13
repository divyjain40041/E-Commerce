package com.example.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.models.request.RegistrationRequest;
import com.example.shop.services.RegistrationService;

@RestController
@CrossOrigin
@RequestMapping("/register")
public class RegistrationController {
	@Autowired
	private RegistrationService registrationService;
	@RequestMapping(value = "/customer", method = RequestMethod.POST)
	public ResponseEntity<?> registerCustomer(@RequestBody RegistrationRequest registrationRequest)
	{
		try
		{
		registrationService.register(registrationRequest);
		return ResponseEntity.ok("created");
		}
		catch(IllegalStateException exception)
		{
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
		}
	}
	@RequestMapping(value = "/confirm-customer", method =RequestMethod.GET)
	public String confirmCustomer(@RequestParam("token")String token)
	{
		try
		{
		registrationService.confirm(token);
		return "confirmed";
		}
		catch(IllegalStateException exception)
		{
			return exception.getMessage();
		}
	}
}
