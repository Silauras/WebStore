package edu.sytoss.repository;

import edu.sytoss.model.product.AlterPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlterPriceRepository extends JpaRepository<AlterPrice, Long> {
    AlterPrice findById(Long id);
}
