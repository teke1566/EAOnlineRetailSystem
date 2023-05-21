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

    @OneToMany
    @JoinColumn(name = "itemId")
    private List<Review> reviews;

    @ManyToOne //Many item can be owned by one merchant
    private User merchant;
}

