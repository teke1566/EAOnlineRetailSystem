package cs544.ea.OnlineRetailSystem.domain;

import java.util.List;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cart")
public class Cart {
	
	@Id
	@GeneratedValue
	@Column(name = "cartId")
	private int id;
	
	@OneToOne
	private User customer;

	@OneToMany
	@JoinColumn(name = "cartId")
	private List<LineItem> lineItems;


}
