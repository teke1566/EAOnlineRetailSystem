package cs544.ea.OnlineRetailSystem.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
public class CompositeItem extends Item{
    @OneToMany
    private List<Item> items;
}
