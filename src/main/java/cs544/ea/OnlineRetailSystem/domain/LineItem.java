package cs544.ea.OnlineRetailSystem.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

	@ManyToOne (cascade = CascadeType.ALL) //one item can be sold many times
	@JoinColumn(name = "itemid", referencedColumnName = "itemid", nullable = false)
	private Item item;

	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "discount")
	private double discount;
	
	public LineItem(Item item, int quantity, double discount) {
		this.setItem(item);
		this.setQuantity(quantity);
		this.setDiscount(discount);
	}

}
