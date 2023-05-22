package cs544.ea.OnlineRetailSystem.domain.dto.response;

import lombok.Data;

@Data
public class LineItemResponse {
	private Long lineItemId;
	private ItemResponse item;
	private int quantity;
	private double discount;
}
