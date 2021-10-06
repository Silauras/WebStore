package edu.sytoss.UI;

import edu.sytoss.model.communication.Answer;
import edu.sytoss.model.communication.Question;
import edu.sytoss.model.communication.Review;
import edu.sytoss.model.product.Category;
import edu.sytoss.model.product.Characteristic;
import edu.sytoss.model.product.ProductCard;
import edu.sytoss.repository.ProductCardFilterRepository;
import edu.sytoss.repository.ProductCardRepository;
import edu.sytoss.service.ProductApi;
import edu.sytoss.service.impl.CommentaryApiImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;

import static edu.sytoss.UI.MenuUtils.*;

@Service
public class ProductCardMenu {

    @Autowired
    ProductApi productApi;
    @Autowired
    CommentaryApiImpl commentaryApi;
    @Autowired
    ProductCardFilterRepository productCardFilterRepository;
    @Autowired
    ProductCardRepository productCardRepository;

    public void start() {
        printMenu(
                "-1. Quit",
                "1. Print commentaries for product",
                "2. Print Product with full description",
                "3. Print all possible characteristics for category",
                "4. Product Card filter"
        );
        switch (scanInt()) {
            case -1:
                return;
            case 1:
                printCommentaries();
                break;
            case 2:
                printProducts();
                break;
            case 3:
                printAllPossibleCharacteristics();
                break;
            case 4:
                printProductCardsByFilter();
                break;
        }
    }

    private void printProductCardsByFilter() {
        Map<String, List<String>> params = new HashMap<>();

        long orderId = scanInt("write category id");

        String startPrice = scanLine("write start price or -1 to don't add");
        if (!startPrice.equals("-1")) {
            params.put("startPrice", new ArrayList<>(Collections.singletonList(startPrice)));
        }
        String endPrice = scanLine("write end price or -1 to don't add");
        if (!endPrice.equals("-1")) {
            params.put("endPrice", new ArrayList<>(Collections.singletonList(endPrice)));
        }
        String statusString = scanLine("write statuses using , or -1 to don't add");
        List<String> statues = statusString.equals("-1") ?
                null : new ArrayList<>(Arrays.asList(statusString.split(",")));
        if (statues != null) {
            params.put("productStatus", statues);
        }
        printMenu("Select sort type",
                "1. mostExpensive",
                "2. mostCheap",
                "3. mostCommented",
                "4. bestRated");
        switch (scanInt()) {
            case 1:
                params.put("sortType", new ArrayList<>(Collections.singletonList("mostExpensive")));
                break;
            case 2:
                params.put("sortType", new ArrayList<>(Collections.singletonList("mostCheap")));
                break;
            case 3:
                params.put("sortType", new ArrayList<>(Collections.singletonList("mostCommented")));
                break;
            case 4:
                params.put("sortType", new ArrayList<>(Collections.singletonList("bestRated")));
                break;
        }

        params.put("sortType", new ArrayList<String>());
        params.get("sortType").add("mostCommented");
        for (Object o : productCardFilterRepository
                .findProductCardsByFilter(orderId, params)) {
            System.out.println(o.toString());
        }
    }

    private void printAllPossibleCharacteristics() {
        long categoryId = scanInt("Write category id or 0 to show all catalogs");
        if (categoryId == 0) {
            printAllCategoriesWithCharacteristics();
            categoryId = scanInt("Write catalog id");
        }
        printPerCategory(categoryId);
    }

    private void printAllCategoriesWithCharacteristics() {
        List<Category> categories = productApi.findAllCategories();
        for (Category category : categories) {
            printPerCategory(category.getId());
        }
    }

    private void printPerCategory(Long categoryId) {
        Map<String, List<String>> characteristics = new HashMap<>();
        List<Characteristic> characteristicsList = productApi.findCharacteristicsPerCategory(categoryId);
        for (Characteristic characteristic : characteristicsList) {
            if (!characteristics.containsKey(characteristic.getName()))
                characteristics.put(characteristic.getName(), new ArrayList<String>());
            if (!characteristics.get(characteristic.getName()).contains(characteristic.getValue())) {
                characteristics.get(characteristic.getName()).add(characteristic.getValue());
            }

        }
        System.out.println(categoryId + ". " + productApi.findCategoryById(categoryId).getName());
        for (String key : characteristics.keySet()) {
            printClassName("Characteristic: " + key);
            for (String value : characteristics.get(key)) {
                System.out.println(value);
            }
        }
    }

    private void printProducts() {
        long productId = scanInt("Write product id");
        printProduct(productId);
    }

    private void printProduct(Long productId) {
        ProductCard productCard = productApi.findProductCardByIdWithCharacteristicsAndCategory(productId);
        printClassName("Product");
        printField("id", productCard.getId().toString());
        printField("status", productCard.getStatus());
        printField("short description", productCard.getShortDescription());
        printField("full description", productCard.getFullDescription());
        for (Characteristic characteristic : productCard.getCharacteristics())
            if (characteristic.getAmount() > 1)
                printField(characteristic.getName(),
                        characteristic.getAmount() + "x" + characteristic.getValue());
            else
                printField(characteristic.getName(), characteristic.getValue());
        printField("category", productCard.getProductTemplate().getCategory().getName());
    }

    private void printCommentaries() {
        // I don't think that in test you will use BIG number;
        long productId = scanInt("Write product id or 0 to show all products");
        if (productId == 0) {
            printAllProductsWithCountOfCommentaries();
            productId = scanInt("Write product id");
        }
        printCommentariesPerProduct(productId);
    }

    private void printAllProductsWithCountOfCommentaries() {
        List<ProductCard> productCards = productApi.findAllProductCards();
        for (ProductCard productCard : productCards) {
            System.out.println(colors.ANSI_BLUE +
                    productCard.getId() + ". " + productCard.getName() +
                    colors.ANSI_GREEN +
                    " has " + commentaryApi.countCommentariesForProductCard(productCard) + " commentaries" +
                    colors.ANSI_RESET
            );
        }
    }

    private void printCommentariesPerProduct(Long productId) {
        ProductCard productCard = productApi.findProductCardById(productId);
        List<Review> reviews = new ArrayList<>(commentaryApi.findReviewsForProductCard(productCard));
        List<Question> questions = new ArrayList<>(commentaryApi.findQuestionsForProductCard(productCard));

        System.out.println(MessageFormat.format(
                "{0} has {1} commentaries.",
                productCard.getName(),
                commentaryApi.countCommentariesForProductCard(productCard)
        ));
        System.out.println(reviews.size() + " reviews");
        printReviews(reviews);
        System.out.println(questions.size() + " questions");
        printQuestions(questions);
    }

    private void printReviews(List<Review> reviews) {
        for (Review review : reviews) {
            printClassName("Review");
            printField("id", String.valueOf(review.getId()));
            printField("author", review.getAuthor().getName());
            printField("content", review.getContent());
            printField("rating", String.valueOf(review.getRating()));
            List<Answer> answers = commentaryApi.findAnswersByRootCommentary(review.getId());
            if (answers.size() > 0) {
                printField("answers", String.valueOf(answers.size()));
                printAnswers(answers);
            }
        }
    }

    private void printQuestions(List<Question> questions) {
        for (Question question : questions) {
            printClassName("Question");
            printField("id", String.valueOf(question.getId()));
            printField("author", question.getAuthor().getName());
            printField("content", question.getContent());
            List<Answer> answers = commentaryApi.findAnswersByRootCommentary(question.getId());
            if (answers.size() > 0) {
                printField("answers", String.valueOf(answers.size()));
                printAnswers(answers);
            }
        }
    }

    private void printAnswers(List<Answer> answers) {
        for (Answer answer : answers) {
            printClassName("Answer");
            printField("id", String.valueOf(answer.getId()));
            printField("author", answer.getAuthor().getName());
            printField("reply to",
                    answer.getRootCommentary().getId() + ". " + answer.getRootCommentary().getAuthor().getName());
            printField("content", answer.getContent());
            List<Answer> subAnswers = commentaryApi.findAnswersByRootCommentary(answer.getId());
            if (subAnswers.size() > 0) {
                printAnswers(subAnswers);
            }
        }
    }

}
