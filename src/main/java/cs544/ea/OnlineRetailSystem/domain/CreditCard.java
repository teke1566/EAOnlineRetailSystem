package cs544.ea.OnlineRetailSystem.domain;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "creditcard")
public class CreditCard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "creditcardid")
	private Long id;
	
	@Column(name = "cardnumber")
	private String cardNumber;
	
	@Column(name = "expirationdate")
	private LocalDate expirationDate;
	
	@Column(name = "securitycode")
	private String securityCode;

	@Column(name = "balance")
	private double balance;

}
