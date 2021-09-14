package edu.sytoss.repository;

import edu.sytoss.model.media.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    Media findById(Long id);
}
