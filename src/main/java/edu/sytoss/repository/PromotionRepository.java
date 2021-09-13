package edu.sytoss.repository;

import edu.sytoss.model.shop.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion,Long> {
    Promotion findById(Long id);
}
