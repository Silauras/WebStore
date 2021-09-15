package edu.sytoss.repository;

import edu.sytoss.model.communication.Commentary;
import edu.sytoss.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaryRepository<T extends Commentary> extends JpaRepository<T,Long> {
    Commentary findById(Long id);
    List<T> findAllByProduct(Product product);
    long countAllByProduct(Product product);

}
