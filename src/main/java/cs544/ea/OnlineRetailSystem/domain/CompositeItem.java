package cs544.ea.OnlineRetailSystem.domain;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class CompositeItem extends Item{
    List<Item> items;
}
