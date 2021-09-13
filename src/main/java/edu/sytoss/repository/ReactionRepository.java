package edu.sytoss.repository;

import edu.sytoss.model.communication.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction,Long> {
    Reaction findById(Long id);
}
