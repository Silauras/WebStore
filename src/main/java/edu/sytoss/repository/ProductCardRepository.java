package edu.sytoss.repository;

import edu.sytoss.model.product.ProductCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCardRepository extends JpaRepository<ProductCard, Long> {
    ProductCard findById(Long id);
}
