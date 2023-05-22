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
	@Column(name = "reviewId")
	private Long id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "numberOfStars")
	private int numberOfStars;
	
	@Column(name = "reviewDate")
	private LocalDateTime reviewDate;
	
	@ManyToOne
	private User buyer;

	@ManyToOne
	@JoinColumn(name = "itemId")
   private Item item;

	public void setItem(Item item) {
		if(this.item != null) {
			this.item.getReviews().remove(this);
		}
		this.item = item;
		if(item != null) {
			item.getReviews().add(this);
		}
	}
}
