package cs544.ea.OnlineRetailSystem.service.Impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs544.ea.OnlineRetailSystem.DTO.OrderResponse;
import cs544.ea.OnlineRetailSystem.domain.Order;
import cs544.ea.OnlineRetailSystem.domain.OrderStatus;
import cs544.ea.OnlineRetailSystem.repository.OrderRepository;
import cs544.ea.OnlineRetailSystem.service.OrderService;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ModelMapper mapper;
	
	@Override
	public List<OrderResponse> getAllOrders() {
		List<Order> orders = orderRepository.findAll();
		List<OrderResponse> ordersResponse = orders.stream()
				.map(order -> mapper.map(orders, OrderResponse.class))
				.toList();
		return ordersResponse;
	}

	@Override
	public OrderResponse getOrderById(Long orderId) {
		return mapper.map(orderRepository.findById(orderId), OrderResponse.class);
	}

	@Override
	public OrderResponse addOrder(Order order) {
		return mapper.map(orderRepository.save(order), OrderResponse.class);
	}

	@Override
	public OrderResponse updateOrderById(Long orderId, Order order) {
		Optional<Order> foundOrder = orderRepository.findById(orderId);
		if (foundOrder.isPresent()) {
			Order existingOrder = foundOrder.get();
			if (existingOrder.getStatus() == OrderStatus.NEW) {
				BeanUtils.copyProperties(order, existingOrder, "orderId");
				return mapper.map(orderRepository.save(existingOrder), OrderResponse.class);
			}
		}
		return null;
	}

	@Override
	public void deleteOrderById(Long orderId) {
		orderRepository.deleteById(orderId);		
	}

	@Override
	public List<OrderResponse> getOrdersByUserId(Long userId) {
		List<Order> orders = orderRepository.getOrderByUserId(userId);
		List<OrderResponse> ordersResponse = orders.stream()
				.map(order -> mapper.map(order, OrderResponse.class))
				.toList();
		return ordersResponse;
	}

}
