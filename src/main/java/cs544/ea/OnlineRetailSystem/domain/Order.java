package cs544.ea.OnlineRetailSystem.domain;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ordertable")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderid")
	private Long id;

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name = "customer")
	private User customer;

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name = "shippingaddressid")
	private Address shippingAddress;

	@Column(name = "orderstatus")
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@Column(name = "orderdate")
	private LocalDateTime orderDate;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "orderid")
	private List<LineItem> lineItems;

}
