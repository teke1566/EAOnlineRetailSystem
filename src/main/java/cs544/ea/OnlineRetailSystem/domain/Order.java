package cs544.ea.OnlineRetailSystem.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "orderTable")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderId")
	private Long id;
	
	@ManyToOne
	private User customer;
	
	@ManyToOne
	@JoinColumn(name = "shippingAddressId")
	private Address shippingAddress;

	@Column(name = "OrderStatus")
	@Enumerated
	private OrderStatus status;
	
	@Column(name = "orderDate")
	private LocalDateTime orderDate;

	@OneToMany
	@JoinColumn(name = "orderId")
	private List<LineItem> lineItems;
	
	public Order(User customer, Address shippingAddress) {
		this.setCustomer(customer);
		this.setShippingAddress(shippingAddress);
		this.setStatus(OrderStatus.NEW);
		this.setOrderDate(LocalDateTime.now());
		this.setLineItems(new ArrayList<LineItem>());
	}
}
