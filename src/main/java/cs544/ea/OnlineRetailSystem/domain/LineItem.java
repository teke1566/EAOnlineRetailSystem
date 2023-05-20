package cs544.ea.OnlineRetailSystem.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "LineItem")
public class LineItem {
	
	@Id
	@GeneratedValue
	@Column(name = "lineItemId")
	private int id;
	
	@OneToOne
	@JoinColumn(name = "itemId")
	private Item item;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "discount")
	private double discount;

//	//newly added attributes
//	@ManyToOne
//	@JoinColumn(name = "cartId")
//	private Cart cart;
//
//	@ManyToOne
//	@JoinColumn(name = "orderId")
//	private Order order;

}
