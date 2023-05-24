package cs544.ea.OnlineRetailSystem.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "cart")
@NoArgsConstructor
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cartid")
	private Long id;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "customer")
	private User customer;

	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<LineItem> lineItems;

	public Cart(User customer) {
		this.setCustomer(customer);
		this.setLineItems(new ArrayList<LineItem>());
	}

	public void addLineItem(LineItem lineItem) {
		this.lineItems.add(lineItem);
	}
	
	public void clearCart() {
		this.setLineItems(new ArrayList<LineItem>());
	}
}
