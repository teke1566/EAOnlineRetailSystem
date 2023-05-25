package cs544.ea.OnlineRetailSystem.domain.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class CartResponse {

	private Long id;
	private UserResponse customer;
	private List<LineItemResponse> lineItems;

}
