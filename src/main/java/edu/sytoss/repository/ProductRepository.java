package edu.sytoss.repository;

import edu.sytoss.model.product.ProductCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductCard, Long> {
    ProductCard findById(Long id);
}
