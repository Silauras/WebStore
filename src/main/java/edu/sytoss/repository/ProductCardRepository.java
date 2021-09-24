package edu.sytoss.repository;

import edu.sytoss.model.product.ProductCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCardRepository extends JpaRepository<ProductCard, Long> {
    ProductCard findById(Long id);

    @Query( "select p from ProductCard p " +
            "left join fetch p.characteristics c " +
            "left join fetch p.productTemplate pt left join fetch pt.category " +
            "where p.id = ?1")
    ProductCard findByIdWithCharacteristicsAndCategory(Long id);
}
