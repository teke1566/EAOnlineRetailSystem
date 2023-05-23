package cs544.ea.OnlineRetailSystem.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "lineitem")
//LineItem: Represents a specific item in a particular order and cart,
// including the quantity and any applied discounts.
// It would be used to manage the details of individual items within an order
public class LineItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lineitemid")
	private Long lineItemId;

	@ManyToOne (cascade = CascadeType.ALL)//one item can be sold many times
	@JoinColumn(name = "itemid")
	private Item item;

	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "discount")
	private double discount;

	//newly added Bidirectional relation with cart

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cart_id")
	private Cart cart;
}
