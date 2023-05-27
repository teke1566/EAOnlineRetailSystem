package cs544.ea.OnlineRetailSystem.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cartid")
	private Long id;
	
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "customer")
	private User customer;

	@OneToMany(cascade = CascadeType.ALL)
	private List<LineItem> lineItems;

	public Cart(User customer) {
		this.setCustomer(customer);
		this.setLineItems(new ArrayList<LineItem>());
	}

	public void addLineItem(LineItem lineItem) {
		this.lineItems.add(lineItem);
	}
	
	public void removeLineItem(Long lineItemId) {
		Optional<LineItem> lineItem = this.lineItems.stream()
						.filter(li -> li.getLineItemId().equals(lineItemId))
						.findFirst();
		if (lineItem.isPresent())
			this.lineItems.remove(lineItem.get());
	}
	
	public void clearCart() {
		this.setLineItems(new ArrayList<LineItem>());
	}
}
