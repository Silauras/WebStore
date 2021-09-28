package edu.sytoss.repository;

import edu.sytoss.model.order.Order;
import edu.sytoss.model.product.Product;
import edu.sytoss.model.product.ProductCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.id = ?1")
    Product findById(Long id);

    @Query("select p from Product p where p.order = ?1 ")
    List<Product> findProductByOrder(Order order);
}
