package cs544.ea.OnlineRetailSystem.service.Impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs544.ea.OnlineRetailSystem.domain.Order;
import cs544.ea.OnlineRetailSystem.domain.OrderStatus;
import cs544.ea.OnlineRetailSystem.domain.User;
import cs544.ea.OnlineRetailSystem.domain.dto.response.OrderResponse;
import cs544.ea.OnlineRetailSystem.repository.OrderRepository;
import cs544.ea.OnlineRetailSystem.repository.UserRepository;
import cs544.ea.OnlineRetailSystem.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public List<OrderResponse> getAllOrders() {
		return mapOrderToOrderResponse(orderRepository.findAll());
	}

	@Override
	public OrderResponse getOrderById(Long orderId) {
		return mapper.map(orderRepository.findById(orderId), OrderResponse.class);
	}

	@Override
	public List<OrderResponse> getOrdersByStatus(OrderStatus orderStatus) {
		return mapOrderToOrderResponse(orderRepository.findOrdersByStatus(orderStatus));
	}

	@Override
	public List<OrderResponse> getCustomerAllOrders(Long userId) {
		return mapOrderToOrderResponse(orderRepository.findOrdersByUserId(userId));
	}

	@Override
	public List<OrderResponse> getCustomerOrderByStatus(Long userId, OrderStatus orderStatus) {
		return mapOrderToOrderResponse(orderRepository.findOrdersByUserIdAndStatus(userId, orderStatus));
	}

	@Override
	public OrderResponse getCustomerOrderById(Long userId, Long orderId) {
		Optional<Order> order = orderRepository.findOrderByUserIdAndOrderId(userId, orderId);
		if (order.isPresent())
			return mapper.map(order.get(), OrderResponse.class);
		throw new EntityNotFoundException("Order not found");
	}

	@Override
	public OrderResponse addCustomerOrder(Long userId, Order order) {
		Optional<User> customer = userRepository.findById(userId);
		if (customer.isPresent()) {
			order.setCustomer(customer.get());
			return mapper.map(orderRepository.save(order), OrderResponse.class);
		}
		throw new EntityNotFoundException("Customer not found");
	}

	@Override
	public OrderResponse updateCustomerOrderById(Long userId, Long orderId, Order order) throws Exception {
		Optional<Order> foundOrder = orderRepository.findOrderByUserIdAndOrderId(userId, orderId);
		if (foundOrder.isPresent()) {
			Order existingOrder = foundOrder.get();
			if (existingOrder.getStatus() == OrderStatus.NEW) {
				BeanUtils.copyProperties(order, existingOrder, "orderId");
				return mapper.map(orderRepository.save(existingOrder), OrderResponse.class);
			} else throw new Exception("Order cannot be updated");
		}
		throw new EntityNotFoundException("Order not found");
	}

	@Override
	public void deleteCustomerOrderById(Long userId, Long orderId) throws Exception {
		Optional<Order> foundOrder = orderRepository.findById(orderId);
		if (foundOrder.isPresent()) {
			Order existingOrder = foundOrder.get();
			if (existingOrder.getStatus() == OrderStatus.NEW) {
				orderRepository.deleteById(orderId);
			} else throw new Exception("Order cannot be deleted");
		} else throw new EntityNotFoundException("Order not found");
	}

	private List<OrderResponse> mapOrderToOrderResponse(List<Order> orders) {
		return orders.stream()
				.map(order -> mapper.map(order, OrderResponse.class))
				.toList();
	}
}
