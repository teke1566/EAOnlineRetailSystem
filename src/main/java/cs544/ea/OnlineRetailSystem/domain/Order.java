package cs544.ea.OnlineRetailSystem.domain;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "orderTable")
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
	//@OrderColumn(name = "sequence")
	private List<LineItem> lineItems;
	
	@Column(name = "OrderStatus")
	@Enumerated
	private OrderStatus status;
	
	@Column(name = "orderDate")
	private LocalDateTime orderDate;
}
