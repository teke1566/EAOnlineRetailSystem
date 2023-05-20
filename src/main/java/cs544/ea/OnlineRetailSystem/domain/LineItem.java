package cs544.ea.OnlineRetailSystem.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
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
}
