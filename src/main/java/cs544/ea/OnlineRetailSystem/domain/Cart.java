package cs544.ea.OnlineRetailSystem.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "cart")
@NoArgsConstructor
public class Cart {

	@Id
	@GeneratedValue
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
