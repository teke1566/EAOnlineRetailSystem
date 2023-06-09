package cs544.ea.OnlineRetailSystem.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "ordertable")
@NoArgsConstructor
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderid")
	private Long id;

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name = "customer")
	private User customer;

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name = "shippingaddressid")
	private Address shippingAddress;

	@Column(name = "orderstatus")
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@Column(name = "orderdate")
	private LocalDateTime orderDate;

	@OneToMany(cascade = CascadeType.ALL)
	private List<LineItem> lineItems;
  
	public Order(User customer) {
		this.setCustomer(customer);
		this.setShippingAddress(customer.getDefaultShippingAddress());
		this.setStatus(OrderStatus.NEW);
		this.setOrderDate(LocalDateTime.now());
		this.setLineItems(new ArrayList<LineItem>());
	}

	public boolean isDelivered(Order order) {
		if (order.getStatus().equals(OrderStatus.DELIVERED)){
			return true;
		}
		return false;
	}
	
	public void addLineItem(LineItem lineItem) {
		this.lineItems.add(lineItem);
	}
	
}
