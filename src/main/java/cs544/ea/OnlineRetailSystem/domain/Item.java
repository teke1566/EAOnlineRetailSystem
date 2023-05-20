package cs544.ea.OnlineRetailSystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "item")
@Inheritance(strategy = InheritanceType.JOINED)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    private String name;
    private String description;
    private double price;
    private String image;//serialized string
    private String barcode;
    private Integer quantityInStock;
    @OneToMany
    @JoinColumn(name = "reviewId")
    private List<Review> reviews;

}
