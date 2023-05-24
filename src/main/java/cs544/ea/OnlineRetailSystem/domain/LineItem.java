package cs544.ea.OnlineRetailSystem.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "lineitem")
@NoArgsConstructor
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
	@JsonBackReference
	private Cart cart;
	
	public LineItem(Item item, int quantity, double discount, Cart cart) {
		this.setCart(cart);
		this.setItem(item);
		this.setQuantity(quantity);
		this.setDiscount(discount);
	}
	
	public LineItem(Item item, double discount, Cart cart) {
		this.setCart(cart);
		this.setQuantity(0);
		this.setItem(item);
		this.setDiscount(discount);
	}
}
