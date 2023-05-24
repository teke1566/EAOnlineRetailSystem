package cs544.ea.OnlineRetailSystem.domain.dto.request;

import lombok.Data;

@Data
public class ItemRequest {
	private Long itemId;
	private int quantity;
	private double discount;
}
