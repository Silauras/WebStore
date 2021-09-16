package edu.sytoss.UI;

import edu.sytoss.model.communication.Answer;
import edu.sytoss.model.communication.Commentary;
import edu.sytoss.model.product.*;
import edu.sytoss.service.ProductApi;
import edu.sytoss.service.UserAccountAPI;
import edu.sytoss.service.impl.CommentaryApiImpl;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
            System.out.println("1. Show UserAccount");
            System.out.println("2. User with all orders and full price");
            System.out.println("3. Shop with all products and warehouses");
            System.out.println("4. Product with all commentaries");
            System.out.println("5. User with claims on him");
            System.out.println("6. Catalog with all possible characteristics");
            System.out.println("7. Show UserAccount and ");

            switch (scanner.nextInt()) {
                case -1:
                    return;
                case 1:
                    findUserAccount();
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

    private void findUserAccount() {
        System.out.println("-1. Quit");
        System.out.println("1. Find User by id");
        System.out.println("2. Find User by Name and/or Surname or Login" + "\n" +
                "   (You can use path of Name/Surname/Login)");
        System.out.println("3. Find User by Role");
        switch (scanner.nextInt()) {
            case -1:
                return;
            case 1:
                System.out.println("Write UserAccount id(0 for all)");
                long userAccountId = scanner.nextInt();
                if (userAccountId == 0) {
                    List<UserAccount> userAccounts = userAccountAPI.findAllUserAccount();
                    for (UserAccount userAccount : userAccounts) {
                        System.out.println(userAccount.toString());
                    }
                } else {
                    new UserAccountPrinter(userAccountId);
                    break;
                }
            case 2:
                System.out.println("Write Name and/or Surname or Login(if Login start with'@')");
                String surnameNameLogin = scanner.nextLine();
                String surnameNameLogin1 = scanner.nextLine();
                new UserAccountPrinter(surnameNameLogin1);
                break;
            case 3:
                System.out.println("Write Role start with '$'");
                String r = scanner.nextLine();
                String role = scanner.nextLine();
                new UserAccountPrinter(role);
                break;
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
        } else {
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

    private class UserAccountPrinter {
        private UserAccountPrinter(long userAccountId) {
            printUserAccountById(userAccountId);
        }

        private UserAccountPrinter(String surnameNameLogin) {
            printUserAccountBySurnameNameLogin(surnameNameLogin);
        }


        private void printUserAccountById(Long userAccountId) {
            List<UserAccount> userAccounts = userAccountAPI.findUserAccount(new UserAccount(userAccountId));
            System.out.println(userAccounts.toString());
        }

        private void printUserAccountBySurnameNameLogin(String surnameNameLogin) {
            List<UserAccount> userAccounts = userAccountAPI.findUserAccount(new UserAccount(surnameNameLogin));
            for (UserAccount useraccount : userAccounts) {
                System.out.println(useraccount.toString());
            }
        }
    }
}