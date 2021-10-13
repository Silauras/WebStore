package edu.sytoss.repository;

import edu.sytoss.model.order.Order;
import edu.sytoss.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedNativeQuery;
import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.id = ?1")
    Product findById(Long id);

    @Query("select p from Product p where p.order = ?1 ")
    List<Product> findProductByOrder(Order order);

    @Query(value = "select p from Product p " +
            "inner join p.order o " +
            "inner join p.productCard pc " +
            "left join p.kit k " +
            "left join k.productCards " +
            "left join pc.sales s " +
            "where o.id = ?1 ")
    Set<Product> findByOrderIdWithAllPriceFetches(Long orderId);


    @Query("select distinct p from Product p " +
            "inner join fetch p.productCard " +
            "inner join fetch p.order o " +
            "where o.id =:id")
    Set<Product> findByOrderId(@Param("id") Long orderId);
}
