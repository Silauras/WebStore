package edu.sytoss.repository;

import edu.sytoss.model.communication.Review;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CommentaryRepository<Review> {
}
