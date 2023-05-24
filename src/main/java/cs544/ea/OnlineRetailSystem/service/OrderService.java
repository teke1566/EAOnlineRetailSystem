package cs544.ea.OnlineRetailSystem.service;

import java.util.List;

import cs544.ea.OnlineRetailSystem.domain.OrderStatus;
import cs544.ea.OnlineRetailSystem.domain.dto.response.OrderResponse;

public interface OrderService {
	
	public List<OrderResponse> getAllOrders();
	public List<OrderResponse> getOrdersByStatus(String orderStatus);
	public OrderResponse getOrderById(Long orderId);
	public void deleteOrderById(Long orderId);
	public OrderResponse updateOrderStatus(Long orderId, OrderStatus orderStatus);
	
	public List<OrderResponse> getCustomerAllOrders();
	public List<OrderResponse> getCustomerOrderByStatus(String orderStatus);
	public OrderResponse getCustomerOrderById(Long orderId);
	public OrderResponse placeOrder(Long orderId) throws Exception; //order from NEW -> PLACED
}