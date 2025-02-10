package com.example.demoRazorpayIntegration.controller;

import org.springframework.stereotype.Controller;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.example.demoRazorpayIntegration.model.Orders;
import com.example.demoRazorpayIntegration.service.OrderService;

@Controller
public class OrdersController {
	
	@Autowired
	private OrderService orderservice;
	
	@GetMapping("/orders")
	public String ordersPage() {
		return "orders";
	}
	
	@PostMapping(value = "/createOrder", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Orders> createOrder(@RequestBody Orders orders) {
	    System.out.println("Received order: " + orders);
	    Orders razorpayOrder = orderservice.createOrder(orders);
	    return new ResponseEntity<>(razorpayOrder, HttpStatus.CREATED);
	}
	//receives an Orders object in the request body, creates an order using the OrderService, 
    //	and returns the created order along with an HTTP status of CREATED.
	
	@PostMapping("/paymentCallBack")
	public String paymentCallBack(@RequestParam Map<String, String> response) {
		orderservice.updateStatus(response);
		return "success";
	}
	// receives a map of parameters from the request, updates the order status using the OrderService,
	//and returns the name of the view (in this case, “success”).
}
