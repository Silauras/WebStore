package edu.sytoss.repository;

import edu.sytoss.model.product.ProductTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTemplateRepository extends JpaRepository<ProductTemplate, Long> {
    ProductTemplate findById(Long id);

    List<ProductTemplate> findAllByCategory_Id(Long category_id);
}
