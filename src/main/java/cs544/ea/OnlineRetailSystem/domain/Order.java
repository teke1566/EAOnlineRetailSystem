package cs544.ea.OnlineRetailSystem.domain;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Order")
public class Order {
	
	@Id
	@GeneratedValue
	@Column(name = "orderId")
	private int id;
	
	@ManyToOne
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "shippingAddressId")
	private Address shippingAddress;
	
	@OneToMany
	@JoinColumn(name = "lineItemId")
	@OrderColumn(name = "sequence")
	private List<LineItem> lineItems;
	
	@Column(name = "OrderStatus")
	private OrderStatus status;
	
	@Column(name = "orderDate")
	private LocalDateTime orderDate;
}
