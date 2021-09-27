package edu.sytoss.repository;

import edu.sytoss.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(Long id);

    @Query("select p from Product p " +
            "left join fetch p.productCard pc " +
            "left join fetch pc.price " +
            "where p.id = ?1")
    Product findByIdWithCard(Long id);
}
