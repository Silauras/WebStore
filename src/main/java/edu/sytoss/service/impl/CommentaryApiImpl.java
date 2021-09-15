package edu.sytoss.service.impl;

import edu.sytoss.model.communication.Answer;
import edu.sytoss.model.communication.Commentary;
import edu.sytoss.model.communication.Question;
import edu.sytoss.model.communication.Review;
import edu.sytoss.model.product.Product;
import edu.sytoss.repository.AnswerRepository;
import edu.sytoss.repository.CommentaryRepository;
import edu.sytoss.repository.QuestionRepository;
import edu.sytoss.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentaryApiImpl {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    CommentaryRepository<Commentary> commentaryRepository;

    public List<Answer> findAnswersForProduct(Product product) {
        return answerRepository.findAllByProduct(product);
    }

    public List<Review> findReviewsForProduct(Product product){
        return reviewRepository.findAllByProduct(product);
    }

    public List<Question> findQuestionsForProduct(Product product){
        return questionRepository.findAllByProduct(product);
    }


    public long countCommentariesForProduct(Product product){
        return commentaryRepository.countAllByProduct(product);
    }


    public long countReviewsForProduct(Product product){
        return reviewRepository.countAllByProduct(product);
    }


    public long countQuestionsForProduct(Product product){
        return questionRepository.countAllByProduct(product);
    }

    public List<Answer> findAnswersByRootCommentary(Long commentaryId){
        return answerRepository.findAnswersByRootCommentary(commentaryRepository.findById(commentaryId));
    }
}
