package cs544.ea.OnlineRetailSystem.domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Review")
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

	@ManyToOne
	@JoinColumn(name = "itemId")
	private Item item;
}
