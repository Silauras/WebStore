package edu.sytoss.repository;

import edu.sytoss.model.product.ProductTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTemplateRepository extends JpaRepository<ProductTemplate, Long> {
    ProductTemplate findById(Long id);
}
