package edu.sytoss.repository;


import edu.sytoss.model.product.ProductCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCardRepository extends JpaRepository<ProductCard, Long> {
    @Query("select p from ProductCard p where p.id = ?1")
    ProductCard findById(Long id);

    @Query("select p from ProductCard p " +
            "left join fetch p.characteristics c " +
            "left join fetch p.productTemplate pt " +
            "left join fetch pt.category " +
            "where p.id = ?1")
    ProductCard findByIdWithCharacteristicsAndCategory(Long id);

    @Query("select pc from ProductCard pc " +
            "left join fetch pc.products p " +
            "left join fetch p.warehouse w " +
            "left join fetch w.owner " +
            "where pc.id = ?1 and p.status = ?2 and p.status <> 'BLOCKED'" )
    ProductCard findProductCardByIdAndProductStatusWithShopAndProducts(Long id, String productStatus);

    @Query("select pc from ProductCard pc left join fetch pc.products p where pc.id = ?1 and p.status = ?2")
    ProductCard findProductCarByIdAndProductStatusWithProducts(Long id, String productStatus);

    @Query("select p from ProductCard p where p.productTemplate.category.id = ?1")
    List<ProductCard> findProductCardsByProductTemplateCategoryId(Long categoryId);

    @Query("select pc from ProductCard pc left join fetch pc.kits k where pc.id = ?1")
    ProductCard findByIdWithKits(Long id);
}
