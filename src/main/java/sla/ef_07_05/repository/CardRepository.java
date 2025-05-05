package sla.ef_07_05.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sla.ef_07_05.model.Card;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByOwnerName(String ownerName);
}

