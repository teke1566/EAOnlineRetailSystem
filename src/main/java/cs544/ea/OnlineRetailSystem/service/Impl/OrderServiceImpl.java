package cs544.ea.OnlineRetailSystem.service.Impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import cs544.ea.OnlineRetailSystem.domain.Item;
import cs544.ea.OnlineRetailSystem.domain.Order;
import cs544.ea.OnlineRetailSystem.domain.OrderStatus;
import cs544.ea.OnlineRetailSystem.domain.User;
import cs544.ea.OnlineRetailSystem.domain.dto.response.OrderResponse;
import cs544.ea.OnlineRetailSystem.helper.GetUser;
import cs544.ea.OnlineRetailSystem.repository.ItemRepository;
import cs544.ea.OnlineRetailSystem.repository.OrderRepository;
import cs544.ea.OnlineRetailSystem.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private GetUser getUser;

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ItemRepository itemRepository;

//	@Autowired
//	private JmsTemplate jmsTemplate;// used to send a message to the JMS queue when an order is placed

//	@Autowired
//	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	private List<OrderResponse> mapOrderToOrderResponse(List<Order> orders) {
		return orders.stream()
				.map(order -> mapper.map(order, OrderResponse.class))
				.toList();
	}


	private OrderStatus getOrderStatus(String orderStatus) {
		OrderStatus status = OrderStatus.NEW;
		switch(orderStatus) {
		case "PLACED":
			status = OrderStatus.PLACED;
			break;
		case "PROCESSED":
			status = OrderStatus.PROCESSED;
			break;
		case "DELIVERED":
			status = OrderStatus.DELIVERED;
			break;
		case "RETURNED":
			status = OrderStatus.RETURNED;
			break;
		case "SHIPPED":
			status = OrderStatus.SHIPPED;
			break;
		}
		return status;
	}

	@Override
	public List<OrderResponse> getAllOrders(OrderStatus orderStatus) {
		if (orderStatus != null) {
			return mapOrderToOrderResponse(orderRepository.findOrdersByStatus(orderStatus));
		}
		return mapOrderToOrderResponse(orderRepository.findAll());
	}


	@Override
	public OrderResponse getOrderById(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new EntityNotFoundException("Order not found"));
		return mapper.map(order, OrderResponse.class);
	}
	@Override
	public OrderResponse updateOrderStatus(Long orderId, OrderStatus orderStatus) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found"));
		order.setStatus(orderStatus);
		return mapper.map(orderRepository.save(order), OrderResponse.class);
	}

	@Override
	public List<OrderResponse> getOrdersByStatus(OrderStatus orderStatus) {
		return mapOrderToOrderResponse(orderRepository.findOrdersByStatus(getOrderStatus(String.valueOf(orderStatus))));
	}

	@Override
	public void deleteOrderById(Long orderId) {
		orderRepository.deleteById(orderId);
	}

//	@Override
//	public OrderResponse updateOrderStatus(Long orderId, OrderStatus orderStatus) {
//		Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found"));
//		order.setStatus(orderStatus);
//		return mapper.map(orderRepository.save(order), OrderResponse.class);
//	}

	@Override
	public List<OrderResponse> getCustomerAllOrders() {
		User customer = getUser.getUser();
		return mapOrderToOrderResponse(orderRepository.findOrdersByUserId(customer.getId()));
	}

	@Override
	public List<OrderResponse> getCustomerOrderByStatus(String orderStatus) {
		User customer = getUser.getUser();
		return mapOrderToOrderResponse(orderRepository.findOrdersByUserIdAndStatus(customer.getId(), getOrderStatus(orderStatus)));
	}

	@Override
	public OrderResponse getCustomerOrderById(Long orderId) {
		User customer = getUser.getUser();
		Optional<Order> order = orderRepository.findOrderByUserIdAndOrderId(customer.getId(), orderId);
		if (order.isPresent())
			return mapper.map(order.get(), OrderResponse.class);
		throw new EntityNotFoundException("Order not found");
	}


	@Override
	public OrderResponse placeOrder(Long orderId) throws Exception {
		User customer = getUser.getUser();
		Order order = orderRepository.findOrderByUserIdAndOrderId(customer.getId(), orderId).orElseThrow(() -> new EntityNotFoundException("Order not found"));
		if (order.getStatus() == OrderStatus.NEW) {
			order.getLineItems().forEach(lineItem -> {
				Item item = lineItem.getItem();
				item.decreaseQuantityInStock(lineItem.getQuantity());
				itemRepository.save(item);
			});
			order.setStatus(OrderStatus.PLACED);

			// send a message to the "OrderPlacedQueue" queue with the order ID

//			 sendOrderPlacedMessage(order);

			return mapper.map(orderRepository.save(order), OrderResponse.class);
		}
		throw new Exception("Order cannot be placed");
	}

//	public void sendOrderPlacedMessage(Order order) {
//		jmsTemplate.convertAndSend("OrderPlacedQueue", order.getId());
//	}
}
