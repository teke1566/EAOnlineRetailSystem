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
	@Column(name = "cartid")
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "customer")
	private User customer;

	@OneToMany(mappedBy = "cart")
	private List<LineItem> lineItems;


}
