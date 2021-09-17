package edu.sytoss.repository;

import edu.sytoss.model.communication.Question;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends CommentaryRepository<Question> {
}
