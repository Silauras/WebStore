package edu.sytoss.repository;

import edu.sytoss.model.product.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Price,Long> {
    Price findById(Long id);
}
