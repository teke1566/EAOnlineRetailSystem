package cs544.ea.OnlineRetailSystem.domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "review")
//only buyers of an item can review the item.order status have to be DELIVERED
public class Review {
	@Id
	@GeneratedValue
	@Column(name = "reviewid")
	private Long id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "numberofstars")
	private int numberOfStars;
	
	@Column(name = "reviewdate")
	private LocalDateTime reviewDate;
	
	@ManyToOne
	@JoinColumn(name = "buyer")
	private User buyer;

	@ManyToOne
	@JoinColumn(name = "itmeid")
	private Item item;

}
