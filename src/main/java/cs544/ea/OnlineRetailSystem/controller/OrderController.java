package cs544.ea.OnlineRetailSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cs544.ea.OnlineRetailSystem.DTO.OrderResponse;
import cs544.ea.OnlineRetailSystem.domain.Order;
import cs544.ea.OnlineRetailSystem.service.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping
	public List<OrderResponse> getAllOrders() {
		return orderService.getAllOrders();
	}
	
	@PostMapping
	public OrderResponse addOrder(@RequestBody Order order) {
		return orderService.addOrder(order);
	}
	
	@GetMapping("/{orderId}")
	public OrderResponse getOrderById(@PathVariable Long orderId) {
		return orderService.getOrderById(orderId);
	}
	
	@PutMapping("/{orderId}")
	public OrderResponse updateOrderById(@PathVariable Long orderId, @RequestBody Order order) {
		return orderService.updateOrderById(orderId, order);
	}
	
	@DeleteMapping("/{orderId}")
	public void deleteOrderById(@PathVariable Long orderId) {
		orderService.deleteOrderById(orderId);
	}
	
}
