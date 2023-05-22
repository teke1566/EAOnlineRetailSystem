package cs544.ea.OnlineRetailSystem.DTO;

import lombok.Data;

@Data
public class LineItemResponse {
	private Long lineItemId;
	private ItemResponse item;
	private int quantity;
	private double discount;
}
