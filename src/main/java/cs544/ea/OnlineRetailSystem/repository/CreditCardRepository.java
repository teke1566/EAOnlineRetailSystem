package cs544.ea.OnlineRetailSystem.repository;

import cs544.ea.OnlineRetailSystem.domain.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard,Long> {
}
