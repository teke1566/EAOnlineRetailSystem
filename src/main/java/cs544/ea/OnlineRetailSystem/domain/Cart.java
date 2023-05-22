package cs544.ea.OnlineRetailSystem.domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "cart")
public class Cart {
	
	@Id
	@GeneratedValue
	@Column(name = "cartId")
	private Long id;
	
	@OneToOne
	private User customer;

	@OneToMany
	@JoinColumn(name = "cartId")
	private List<LineItem> lineItems;


}
