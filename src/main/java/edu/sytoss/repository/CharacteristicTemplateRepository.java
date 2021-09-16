package edu.sytoss.repository;

import edu.sytoss.model.product.Characteristic;
import edu.sytoss.model.product.CharacteristicTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacteristicTemplateRepository extends JpaRepository<CharacteristicTemplate, Long> {
    CharacteristicTemplate findById(Long id);
    @Query("select c from CharacteristicTemplate c left join c.productTemplates productTemplates where productTemplates.id = ?1")
    List<CharacteristicTemplate> findAllByProductTemplateId(Long productTemplateId);
}
