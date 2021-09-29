package edu.sytoss.repository;

import edu.sytoss.model.order.Order;
import edu.sytoss.model.product.Product;
import edu.sytoss.model.product.ProductCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.id = ?1")
    Product findById(Long id);

    @Query("select p from Product p where p.order = ?1 ")
    List<Product> findProductByOrder(Order order);

/*    @Query("select p from Product p " +
            "left join fetch p.productCard pc " +
            "left join fetch pc.price " +
            "where p.id = ?1")
    Product findByIdWithCard(Long id);*/

    @Query("select distinct p from Product p " +
            "inner join fetch p.order o " +
            "inner join fetch p.productCard pc " +
            "left join fetch p.kit k " +
            "left join fetch k.productCards " +
            "left join fetch pc.prices pr " +
            "where o.id = ?1")
    Set<Product> findByOrderIdWithAllPriceFetches(Long orderId);

    @Query("select distinct p from Product p " +
            "inner join fetch p.productCard " +
            "inner join fetch p.order o " +
            "where o.id =:id")
    Set<Product> findByOrderId(@Param("id") Long orderId);
}
