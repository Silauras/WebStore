package edu.sytoss.repository;

import edu.sytoss.model.product.Characteristic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {
    Characteristic findById(Long id);

    List<Characteristic> findAllByTemplate_Id(Long template_id);

}
