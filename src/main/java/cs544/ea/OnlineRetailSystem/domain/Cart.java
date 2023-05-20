package cs544.ea.OnlineRetailSystem.domain;

import java.util.List;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderColumn;
import lombok.Data;

@Data
@Entity
public class Cart {
	
	@Id
	@GeneratedValue
	@Column(name = "cartId")
	private int id;
	
	@OneToOne
	private Customer customer;
	
	@OneToMany
	@JoinColumn(name = "lineItemId")
	//@OrderColumn(name = "sequence")
	private List<LineItem> lineItems;
}
