package edu.sytoss.repository;

import edu.sytoss.model.shop.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,Long> {
    Warehouse findById(Long id);
}
