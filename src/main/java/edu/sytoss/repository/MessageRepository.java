package edu.sytoss.repository;

import edu.sytoss.model.communication.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Message findById(Long id);
}
