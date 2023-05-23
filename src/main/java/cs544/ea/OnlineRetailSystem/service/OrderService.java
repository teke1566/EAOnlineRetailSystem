package cs544.ea.OnlineRetailSystem.service;

import java.util.List;

import cs544.ea.OnlineRetailSystem.domain.Order;
import cs544.ea.OnlineRetailSystem.domain.OrderStatus;
import cs544.ea.OnlineRetailSystem.domain.dto.response.OrderResponse;

public interface OrderService {
	
	public List<OrderResponse> getAllOrders();
	public List<OrderResponse> getOrdersByStatus(OrderStatus orderStatus);
	public OrderResponse getOrderById(Long orderId);
	
	public List<OrderResponse> getCustomerAllOrders(Long userId);
	public List<OrderResponse> getCustomerOrderByStatus(Long userId, OrderStatus orderStatus);
	public OrderResponse getCustomerOrderById(Long userId, Long orderId);
	
	public OrderResponse addCustomerOrder(Long userId, Order order);
	public OrderResponse updateCustomerOrderById(Long userId, Long orderId, Order order) throws Exception; //include update status
	public void deleteCustomerOrderById(Long userId, Long orderId) throws Exception;

}