package cs544.ea.OnlineRetailSystem.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
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
