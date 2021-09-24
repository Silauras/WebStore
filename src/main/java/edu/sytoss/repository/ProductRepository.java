package edu.sytoss.repository;

import edu.sytoss.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(Long id);
}
