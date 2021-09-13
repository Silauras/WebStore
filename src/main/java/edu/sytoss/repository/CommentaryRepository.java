package edu.sytoss.repository;

import edu.sytoss.model.communication.Commentary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentaryRepository extends JpaRepository<Commentary,Long> {
    Commentary findById(Long id);
}
