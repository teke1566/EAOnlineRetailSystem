package cs544.ea.OnlineRetailSystem.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "creditcard")
public class CreditCard {
	
	@Id
	@GeneratedValue
	@Column(name = "creditCardId")
	private Long id;
	
	@Column(name = "cardNumber")
	private String cardNumber;
	
	@Column(name = "expirationDate")
	private LocalDate expirationDate;
	
	@Column(name = "securityCode")
	private String securityCode;

}
