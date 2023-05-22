package cs544.ea.OnlineRetailSystem.DTO;

import java.util.List;

import lombok.Data;

@Data
public class ItemResponse {
    private Long itemId;
    private String name;
    private String description;
    private double price;
    private String image;//serialized string
    private String barcode;
    private Integer quantityInStock;
    private List<ReviewResponse> reviews;
    private UserResponse merchant;
}

