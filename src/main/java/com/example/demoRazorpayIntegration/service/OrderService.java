package com.example.demoRazorpayIntegration.service;

import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.example.demoRazorpayIntegration.model.Orders;
import com.example.demoRazorpayIntegration.repository.OrdersRepository;
import jakarta.annotation.PostConstruct;

@Service
public class OrderService {
	
	@Autowired
	private OrdersRepository ordersRepository;
	
	@Value("${razorpay.key.id}")
	private String razorpayId;
	@Value("${razorpay.key.secret}")
	private String razorpaySecret;
	
	private RazorpayClient razorpayClient;
	
	@PostConstruct
	public void init() throws RazorpayException {
		this.razorpayClient = new RazorpayClient(razorpayId, razorpaySecret);
	}
	
	public Orders createOrder(Orders order) throws RazorpayException {
        JSONObject options = new JSONObject();
        options.put("amount", order.getAmount() * 100); // amount in paise
        options.put("currency", "INR");
        options.put("receipt", order.getEmail());
        Order razorpayOrder = razorpayClient.orders.create(options);
        if (razorpayOrder != null) {
            order.setRazorpayOrderId(razorpayOrder.get("id"));
            order.setOrderStatus(razorpayOrder.get("status"));
        }
        return ordersRepository.save(order);
    }
	//This method creates a new order with Razorpay and saves it to the database. It takes an Orders object as input, 
	//constructs the order details, and interacts with the Razorpay API to create the order. If successful, it updates 
	//the Orders object with the Razorpay order ID and status before saving it to the repository.
	
	
	public Orders updateStatus(Map<String, String> map) {
    	String razorpayId = map.get("razorpay_order_id");
    	Orders order = ordersRepository.findByRazorpayOrderId(razorpayId);
    	order.setOrderStatus("PAYMENT DONE");
    	return ordersRepository.save(order);
    	
    }
	// This method updates the status of an order based on the Razorpay order ID. It retrieves the order from the repository using the Razorpay order ID,
	//updates its status to “PAYMENT DONE”, and saves the updated order back to the repository.
}
