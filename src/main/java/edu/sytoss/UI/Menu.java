package edu.sytoss.UI;

import edu.sytoss.dto.UserAccountDTO;
import edu.sytoss.model.communication.Answer;
import edu.sytoss.model.communication.Commentary;
import edu.sytoss.model.product.*;
import edu.sytoss.service.ProductApi;
import edu.sytoss.service.UserAccountAPI;
import edu.sytoss.service.impl.CommentaryApiImpl;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;

@NoArgsConstructor

@Service
public class Menu {
    @Autowired
    ProductApi productApi;
    @Autowired
    CommentaryApiImpl commentaryApi;
    @Autowired
    UserAccountAPI userAccountAPI;


    Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("What you want to see?");
            System.out.println("-1. Quit");
            System.out.println("1. User with contacts");
            System.out.println("2. User with all orders and full price");
            System.out.println("3. Shop with all products and warehouses");
            System.out.println("4. Product with all commentaries");
            System.out.println("5. User with claims on him");
            System.out.println("6. Catalog with all possible characteristics");

            switch (scanner.nextInt()) {
                case -1:
                    return;
                case 1:
                    System.out.println(userAccountAPI.findUserAccount(new UserAccountDTO(1L)));
                    System.out.println(userAccountAPI.findUserAccount(new UserAccountDTO("Alma")));
                    break;
                case 4:
                    printCommentaries();
                    break;
                case 6:
                    printAllPossibleCharacteristics();
                    break;

            }
        }
    }

    private void printCommentaries() {
        System.out.println("Write product id or 0 to show all products");
        long productId = scanner.nextInt(); // I don't think that in test you will use BIG number;
        if (productId == 0) {
            List<ProductCard> productCards = productApi.findAllProducts();
            for (ProductCard productCard : productCards) {
                System.out.println(productCard.getId() + ". " + productCard.getName() + " has " +
                        commentaryApi.countCommentariesForProductCard(productCard) + " commentaries");
            }
            System.out.println("Write product id");
            productId = scanner.nextInt();
        }
        new CommentaryPrinter(productId);
    }

    private void printAllPossibleCharacteristics() {
        System.out.println("Write category id or 0 to show all catalogs");
        long categoryId = scanner.nextInt();
        CatalogPrinter catalogPrinter = new CatalogPrinter();
        if (categoryId == 0) {
            catalogPrinter.printAll();
            System.out.println("Write catalog id");
            categoryId = scanner.nextInt();
        }
        catalogPrinter.printPerCategory(categoryId);
    }


    private class CatalogPrinter {
        private CatalogPrinter() {
        }

        private void printAll() {
            List<Category> categories = productApi.findAllCategories();
            for (Category category : categories) {
                printPerCategory(category.getId());
            }
        }


        private void printPerCategory(Long categoryId) {
            Map<String, List<String>> characteristics = new HashMap<>();
            List<Characteristic> characteristicsList = productApi.findCharacteristicsPerCategory(categoryId);
            for (Characteristic characteristic : characteristicsList) {
                if (characteristics.containsKey(characteristic.getName()) &&
                        !characteristics.get(characteristic.getName()).contains(characteristic.getValue())) {
                    characteristics.get(characteristic.getName()).add(characteristic.getValue());
                } else {
                    characteristics.put(characteristic.getName(), new ArrayList<String>());
                    characteristics.get(characteristic.getName()).add(characteristic.getValue());
                }
            }
            System.out.println(categoryId + ". " + productApi.findCategoryById(categoryId).getName());
            for (String key : characteristics.keySet()) {
                System.out.println("Characteristic: " + key);
                for (String value : characteristics.get(key)) {
                    System.out.println("  | " + value);
                }
            }
        }
    }

    private class CommentaryPrinter {
        private CommentaryPrinter(long productId) {
            printCommentariesPerProduct(productId);
        }

        private void printCommentariesPerProduct(Long productId) {
            ProductCard productCard = productApi.findProductById(productId);
            List<Commentary> reviews = new ArrayList<Commentary>(commentaryApi.findReviewsForProductCard(productCard));
            List<Commentary> questions = new ArrayList<Commentary>(commentaryApi.findQuestionsForProductCard(productCard));

            System.out.println(MessageFormat.format(
                    "{0} has {1} commentaries.",
                    productCard.getName(),
                    commentaryApi.countCommentariesForProductCard(productCard)
            ));
            System.out.println(reviews.size() + " reviews");
            printCommentaries(reviews);
            System.out.println(questions.size() + " questions");
            printCommentaries(questions);
        }

        private void printCommentaries(List<Commentary> commentaries) {
            for (Commentary commentary : commentaries) {
                printRepeat(0);
                System.out.println(commentary.getAuthor().getName() + ":");
                printRepeat(0);
                System.out.println(commentary.getContent());
                printRepeat(0);
                System.out.println("has " + commentaryApi.findAnswersByRootCommentary(commentary.getId()).size() + " answers");
                printAnswers(commentary, 1);
                System.out.println(" __________________________________________");

            }
        }

        private void printAnswers(Commentary commentary, int depth) {
            List<Answer> answers = commentaryApi.findAnswersByRootCommentary(commentary.getId());
            if (answers.isEmpty())
                return;
            for (Answer answer : answers) {
                printRepeat(depth);
                System.out.println("*" + answer.getAuthor().getName() + " reply to " + commentary.getAuthor().getName());
                printRepeat(depth);
                System.out.println(answer.getContent());
                printRepeat(depth);
                System.out.println("has " + commentaryApi.findAnswersByRootCommentary(answer.getId()).size() + " answers:");
                printAnswers(answer, depth + 1);
            }
        }

        private void printRepeat(int depth) {
            for (int i = 0; i < depth; i++) {
                System.out.print(" ");
            }
            System.out.print(" |");
        }
    }
}