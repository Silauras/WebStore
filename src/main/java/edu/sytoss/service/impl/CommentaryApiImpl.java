package edu.sytoss.service.impl;

import edu.sytoss.model.communication.Answer;
import edu.sytoss.model.communication.Commentary;
import edu.sytoss.model.communication.Question;
import edu.sytoss.model.communication.Review;
import edu.sytoss.model.product.ProductCard;
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

    public List<Review> findReviewsForProductCard(ProductCard productCard){
        return reviewRepository.findAllByProductCard(productCard);
    }

    public List<Question> findQuestionsForProductCard(ProductCard productCard){
        return questionRepository.findAllByProductCard(productCard);
    }

    public long countCommentariesForProductCard(ProductCard productCard){
        return commentaryRepository.countAllByProductCard(productCard);
    }

    public List<Answer> findAnswersByRootCommentary(Long commentaryId){
        return answerRepository.findAnswersByRootCommentary(commentaryRepository.findById(commentaryId));
    }
}
