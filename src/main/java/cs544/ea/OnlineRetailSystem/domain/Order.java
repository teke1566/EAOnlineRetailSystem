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
}
