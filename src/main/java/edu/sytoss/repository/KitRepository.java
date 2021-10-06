package edu.sytoss.repository;

import edu.sytoss.model.product.Kit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KitRepository extends JpaRepository<Kit, Long> {
    @Query("select k from Kit k where k.id = ?1")
    Kit findById(Long id);

    @Query("select k from Kit k " +
            "left join fetch k.productCards pc " +
            "left join fetch pc.products p " +
            "where k.id = ?1 and p.status = ?2 ")
    Kit findKitByIdAndProductStatusWithProducts(Long id, String productStatus);

    @Query("select k from Kit k " +
            "left join fetch k.productCards pc " +
            "where pc.id = ?1")
    List<Kit> findKitByProductCard(long productCardId);
    @Query("select k from Kit k " +
            "left join fetch k.productCards pc "+
            "left join fetch pc.products p " +
            "left join fetch p.warehouse w " +
            "left join fetch w.owner " +
            "where pc.id = ?1 and p.status = ?2")
    Kit findKitByIdAndProductStatusWithShopAndProducts(Long id, String productStatus);
}


