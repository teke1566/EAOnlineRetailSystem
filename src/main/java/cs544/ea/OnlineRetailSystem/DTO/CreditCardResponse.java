package cs544.ea.OnlineRetailSystem.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CreditCardResponse {
	private Long id;
	private String cardNumber;
	private LocalDate expirationDate;
	private String securityCode;
}
