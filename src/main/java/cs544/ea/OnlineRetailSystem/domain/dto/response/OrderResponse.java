package cs544.ea.OnlineRetailSystem.domain.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import cs544.ea.OnlineRetailSystem.domain.OrderStatus;
import cs544.ea.OnlineRetailSystem.domain.User;
import lombok.Data;

@Data
public class OrderResponse {
	private Long id;
	private User customer;
	private AddressResponse shippingAddress;
	private OrderStatus status;
	private LocalDateTime orderDate;
	private List<LineItemResponse> lineItems;
}