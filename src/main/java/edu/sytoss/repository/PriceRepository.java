package edu.sytoss.repository;

import edu.sytoss.model.product.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Sale,Long> {
    Sale findById(Long id);
}
