package cs544.ea.OnlineRetailSystem.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Column(name = "itemid")
    private Long itemId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private double price;
    @Column(name = "image")
    private String image;//serialized string
    @Column(name = "barcode")
    private String barcode;
    @Column(name = "quantityinstock")
    private Integer quantityInStock;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Review> reviews;

    @ManyToOne(cascade = CascadeType.ALL) //Many item can be owned by one merchant
    @JoinColumn(name = "merchant")
    private User merchant;
    
    public void decreaseQuantityInStock(int quantity) {
    	this.setQuantityInStock(quantityInStock - quantity);
    }
}

