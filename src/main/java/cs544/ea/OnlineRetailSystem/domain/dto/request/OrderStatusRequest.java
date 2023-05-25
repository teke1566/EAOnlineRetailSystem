package cs544.ea.OnlineRetailSystem.domain.dto.request;

import cs544.ea.OnlineRetailSystem.domain.OrderStatus;
import lombok.Data;

@Data
public class OrderStatusRequest {
	private OrderStatus orderStatus;
}
