package edu.sytoss.repository;

import edu.sytoss.model.order.Order;
import edu.sytoss.model.product.ProductCard;
import edu.sytoss.model.user.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.id = ?1")
    Order findById(Long id);

    @Query("select o from Order o " +
            "left join fetch o.products p " +
            "left join fetch p.productCard pc " +
            "where o.id = ?1")
    Order findByIdWithAllPrices(Long id);

    @Query("select o from Order o left join fetch o.products where o.id = ?1")
    Order findOrderWithProductsById(Long id);

    /* @Query(" select o from Order o" +
             "left join fetch o.product p left join fetch p.productCard " +
             "where o.id = ?1")*/
    @Query("select o from Order o " +
            "left join fetch o.products p left join fetch p.productCard " +
            "where o.id=?1")
    Order findOrderWithProductCartsById(Long id);

    @Query("select o from Order o " +
            "left join fetch o.products p " +
            "left join fetch p.productCard pc " +
            "left join fetch p.kit k " +
            "left join fetch k.productCards " +
            "left join fetch pc.prices pr on pr.startDate > current_date and pr.endDate < current_date " +
            "where o.id = ?1")
    Order findByIdWithAllProductsAndProductCardsAndPrices(Long id);
}
