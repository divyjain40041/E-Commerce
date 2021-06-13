package com.example.shop.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.example.shop.models.BuyOrder;
import com.example.shop.models.CartItem;
import com.example.shop.models.Customer;
import com.example.shop.models.OrderStatus;
import com.example.shop.models.PaymentStatus;
import com.example.shop.models.PaymentType;
import com.example.shop.models.Product;
import com.example.shop.repository.BuyOrderRepository;
import com.example.shop.repository.CartItemRepository;
import com.example.shop.repository.CustomerRepository;
import com.example.shop.repository.ProductRepository;
import com.example.shop.util.JwtUtil;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@Service
public class BuyOrderService {
	
	private BuyOrderRepository buyOrderRepository;
	@Autowired 
	private CartItemRepository cartItemRepository;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CustomerRepository customerRepository;
	
	
	@Autowired
	public BuyOrderService(BuyOrderRepository buyOrderRepository)
	{
		this.buyOrderRepository = buyOrderRepository;
	}
	public Slice<BuyOrder> getAll(int page, int size)
	{
		Pageable pageable = PageRequest.of(page, size);
		return buyOrderRepository.findAll(pageable);
	}
	public Slice<BuyOrder> getByCustomerId(long customerId, int page, int size)
	{
		Pageable pageable = PageRequest.of(page, size);
		return buyOrderRepository.findByCustomerId(customerId, pageable);
	}
	public Slice<BuyOrder> getBySellerId(long sellerId, int page, int size)
	{
		Pageable pageable = PageRequest.of(page, size);
		return buyOrderRepository.findBySellerId(sellerId, pageable);
	}
	public Slice<BuyOrder> getByProductId(long productId, int page, int size)
	{
		Pageable pageable = PageRequest.of(page, size);
		return buyOrderRepository.findByProductId(productId, pageable);
	}
	public Slice<BuyOrder> getByPaymentType(String paymentType, int page, int size)
	{
		Pageable pageable = PageRequest.of(page, size);
		try
		{
			PaymentType value = PaymentType.valueOf(paymentType.toUpperCase());
			return buyOrderRepository.findByPaymentType(value, pageable);
		}
		catch(Exception exception)
		{
			return buyOrderRepository.findByPaymentType(null, pageable);
		}
	}
	public Slice<BuyOrder> getByOrderStatus(String orderStatus, int page, int size)
	{
		Pageable pageable = PageRequest.of(page, size);
		try
		{
			OrderStatus value = OrderStatus.valueOf(orderStatus.toUpperCase());
			return buyOrderRepository.findByOrderStatus(value, pageable);
		}
		catch(Exception exception)
		{
			return buyOrderRepository.findByOrderStatus(null, pageable);
		}
	}
	public Slice<BuyOrder> getByShippingCity(String shippingCity, int page, int size)
	{
		Pageable pageable = PageRequest.of(page, size);
		return buyOrderRepository.findByShippingCityIgnoreCase(shippingCity, pageable);
	}
	public Slice<BuyOrder> getByShippingState(String shippingState, int page, int size)
	{
		Pageable pageable = PageRequest.of(page, size);
		return buyOrderRepository.findByShippingStateIgnoreCase(shippingState, pageable);
	}
	public Slice<BuyOrder> getByShippingAddress(String shippingAddress, int page, int size)
	{
		Pageable pageable = PageRequest.of(page, size);
		return buyOrderRepository.findByShippingAddressIgnoreCase(shippingAddress, pageable);
	}
	public Slice<BuyOrder> getByPincode(long pincode, int page, int size)
	{
		Pageable pageable = PageRequest.of(page, size);
		return buyOrderRepository.findByPincode(pincode, pageable);
	}
	public String createOrder(Map<String, Object> map) throws Exception 
	{
		System.out.println(map);
		final String keyId = "rzp_test_NUfcQVU44SeBMy";
		final String keySecret = "OH7nkScaCXkQsZfumrmt5GgM";
		RazorpayClient razorpayClient = new RazorpayClient(keyId, keySecret);
		JSONObject options = new JSONObject();
		int amount = Integer.parseInt(map.get("amount").toString());
		Map<String,Object> attributes = (Map)map.get("attributes");
		String token = (String)attributes.get("token");
		// extract other info from fields 
		String username = jwtUtil.extractUsername(token);
		System.out.println("username:"+ username);
		Customer customer = customerRepository.findByEmailIgnoreCase(username);
		if(customer == null)
		{
			throw new IllegalStateException("invalid username");
		}
		List<CartItem> cartItems = cartItemRepository.findByCustomerId(customer.getId());
		List<BuyOrder> orderList = new ArrayList<>();
		int dbAmt = 0;
		for(CartItem cartItem: cartItems)
		{
			long customerId = cartItem.getCustomerId();
			long productId = cartItem.getProductsId();
			Optional<Product> product = productRepository.findById(productId);
			if(product.isEmpty())
			{
				throw new IllegalStateException("Product does not exists:"+ productId);
			}
			BuyOrder buyOrder = new BuyOrder();
			buyOrder.setId(UUID.randomUUID().toString());
			buyOrder.setCustomerId(customerId);
			//buyOrder.setSellerId(product.get().getSellerId());
			buyOrder.setSellerId(12345);
			buyOrder.setProductId(productId);
			buyOrder.setOrderDate(LocalDate.now());
			buyOrder.setOrderShipDate(LocalDate.now().plusDays(4));
			buyOrder.setPaymentStatus(PaymentStatus.PENDING);
			buyOrder.setPaymentType(PaymentType.COD);
			buyOrder.setOrderStatus(OrderStatus.PROCESSING);
			buyOrder.setShippingCity("test_city");
			buyOrder.setShippingState("test_state");
			buyOrder.setShippingAddress("test_address");
			buyOrder.setPincode(123456);
			System.out.println(buyOrder);
			//dbAmt += Integer.parseInt((String)product.get().getAttribute("price"));
			orderList.add(buyOrder);
		}
		for(BuyOrder order: orderList)
		{
			buyOrderRepository.save(order);
			System.out.println(order.getId() + ":saved");
		}
		options.put("amount", amount*100);
		options.put("currency", "INR");
		options.put("receipt", "txn_123456");
		Order order = razorpayClient.Orders.create(options);
			
		
		String responseString = "{\"order\":" + order.toString() +  
								", \"order_id\": " + orderList.toString()+ "}";
		System.out.println(responseString);
		return order.toString();
	}
}
