package cs544.ea.OnlineRetailSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cs544.ea.OnlineRetailSystem.domain.OrderStatus;
import cs544.ea.OnlineRetailSystem.domain.dto.response.OrderResponse;
import cs544.ea.OnlineRetailSystem.service.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping
	public List<OrderResponse> getAllOrders(@RequestParam OrderStatus orderStatus) {
		if (orderStatus != null)
			return orderService.getOrdersByStatus(orderStatus);
		return orderService.getAllOrders();
	}
		
	@GetMapping("/{orderId}")
	public OrderResponse getOrderById(@PathVariable Long orderId) {
		return orderService.getOrderById(orderId);
	}
		
}
