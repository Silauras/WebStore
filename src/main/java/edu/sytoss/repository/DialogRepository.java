package edu.sytoss.repository;

import edu.sytoss.model.communication.Dialog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DialogRepository extends JpaRepository<Dialog, Long> {
    Dialog findById(Long id);
}
