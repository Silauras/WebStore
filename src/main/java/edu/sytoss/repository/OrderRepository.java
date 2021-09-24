package edu.sytoss.repository;

import edu.sytoss.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findById(Long id);

    @Query("select o from Order o " +
            "left join fetch o.products p " +
            "left join fetch p.productCard pc " +
//            "left join fetch pc.prices pr " +
//            "left join fetch pr.promotion prom " +
//            "left join fetch prom.prices prices on prom.promotionType = \"buy_together\" " +
//            "left join fetch prices.productCard " +
            "where o.id = ?1")
    Order findByIdWithAllPrices(Long id);
}
