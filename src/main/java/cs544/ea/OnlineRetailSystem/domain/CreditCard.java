package cs544.ea.OnlineRetailSystem.domain;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "creditcard")
public class CreditCard {
	
	@Id
	@GeneratedValue
	@Column(name = "creditCardId")
	private int id;
	
	@Column(name = "cardNumber")
	private String cardNumber;
	
	@Column(name = "expirationDate")
	private LocalDate expirationDate;
	
	@Column(name = "securityCode")
	private String securityCode;

}
