package com.example.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.shop.models.BuyOrder;
import com.example.shop.models.PaymentStatus;
import com.example.shop.repository.BuyOrderRepository;

@Component
public class SampleRunner implements CommandLineRunner{

	@Autowired
	BuyOrderRepository buyOrder;
	public void run(String... args) throws Exception {
	
	}
	
}
