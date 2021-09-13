package edu.sytoss.repository;

import edu.sytoss.model.user.Communication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunicationRepository extends JpaRepository<Communication,Long> {
    Communication findById(Long id);
}
