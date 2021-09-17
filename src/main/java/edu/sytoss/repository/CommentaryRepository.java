package edu.sytoss.repository;

import edu.sytoss.model.communication.Commentary;
import edu.sytoss.model.product.ProductCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaryRepository<T extends Commentary> extends JpaRepository<T,Long> {
    Commentary findById(Long id);
    List<T> findAllByProductCard(ProductCard productCard);
    long countAllByProductCard(ProductCard productCard);

}
