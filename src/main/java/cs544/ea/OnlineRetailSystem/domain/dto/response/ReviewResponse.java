package cs544.ea.OnlineRetailSystem.domain.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReviewResponse {
	private Long id;
	private String title;
	private String description;
	private int numberOfStars;
	private LocalDateTime reviewDate;
	private UserResponse buyer;

}