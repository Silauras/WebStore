package edu.sytoss.repository;

import edu.sytoss.model.communication.Answer;
import edu.sytoss.model.communication.Commentary;
import edu.sytoss.model.product.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends CommentaryRepository<Answer> {

    @Query("select a from Answer a where a.rootCommentary = ?1")
    List<Answer> findAnswersByRootCommentary(Commentary commentary);
}
