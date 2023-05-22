package cs544.ea.OnlineRetailSystem.service;

import java.util.List;

import cs544.ea.OnlineRetailSystem.DTO.OrderResponse;
import cs544.ea.OnlineRetailSystem.domain.Order;

public interface OrderService {
	
	public List<OrderResponse> getAllOrders();
	public OrderResponse getOrderById(Long orderId);
	public OrderResponse addOrder(Order order);
	public OrderResponse updateOrderById(Long orderId, Order order); //include update status
	public void deleteOrderById(Long orderId);
	public List<OrderResponse> getOrdersByUserId(Long userId);
}