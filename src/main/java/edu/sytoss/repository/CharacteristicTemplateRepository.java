package edu.sytoss.repository;

import edu.sytoss.model.product.CharacteristicTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacteristicTemplateRepository extends JpaRepository<CharacteristicTemplate, Long> {
    CharacteristicTemplate findById(Long id);
}
