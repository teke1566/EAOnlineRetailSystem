package cs544.ea.OnlineRetailSystem.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Review {
	
	@Id
	@GeneratedValue
	@Column(name = "reviewId")
	private int id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "numberOfStar")
	private int numberOfStar;
	
	@Column(name = "reviewDate")
	private LocalDateTime reviewDate;
	
	@ManyToOne
	private Customer reviewer;
}
