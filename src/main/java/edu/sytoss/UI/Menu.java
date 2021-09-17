package edu.sytoss.UI;

import edu.sytoss.model.communication.Answer;
import edu.sytoss.model.communication.Commentary;
import edu.sytoss.model.order.Order;
import edu.sytoss.model.product.Product;
import edu.sytoss.model.user.Communication;
import edu.sytoss.model.user.Subscription;
import edu.sytoss.model.user.UserAccount;
import edu.sytoss.service.UserAccountAPI;
import edu.sytoss.service.impl.CommentaryApiImpl;
import edu.sytoss.service.impl.ProductService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@NoArgsConstructor
@Getter
@Setter

@Service
public class Menu {
    @Autowired
    ProductService productService;
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

            }
        }
    }

    private void findUserAccount() {
        System.out.println("-1. Quit");
        System.out.println("1. Find User by id");
        System.out.println("2. Find User by Name and/or Surname or Login" + "\n" +
                "   (You can use path of Name/Surname/Login)");
        System.out.println("3. Find User by Role");
        long countUser = userAccountAPI.findAllUserAccount().size();
        switch (scanner.nextInt()) {
            case -1:
                return;
            case 1:
                System.out.println("What you want to see?");
                System.out.println("1. Show UserAccount with main info");
                System.out.println("2. Show UserAccount with Communication");
                long userAccountId;
                switch (scanner.nextInt()) {
                    case 1:
                        System.out.println("Write UserAccount id(0 for all)");
                        userAccountId = scanner.nextInt();
                        if (userAccountId == 0) {
                            new UserAccountPrinter();
                        } else {
                            new UserAccountPrinter(userAccountId);
                            break;
                        }
                        break;
                    case 2:
                        System.out.println("Write UserAccount id(0 for all)");
                        userAccountId = scanner.nextInt();
                        if (userAccountId == 0) {
                            for (long id = 1; id<= countUser; id++) {
                                new UserAccountPrinter(id);
                                new CommunicationPrinter(id);
                            }
                        } else {
                            new UserAccountPrinter(userAccountId);
                            new CommunicationPrinter(userAccountId);
                            break;
                        }
                    case 3:

                }
                break;
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
            List<Product> products = productService.findAll();
            for (Product product : products) {
                System.out.println(product.getId() + ". " + product.getName() + " has " +
                        commentaryApi.countCommentariesForProduct(product) + " commentaries");
            }
        } else {
            System.out.println("Write product id");
            productId = scanner.nextInt();
        }
        new CommentaryPrinter(productId);
    }

    private class CommentaryPrinter {
        private CommentaryPrinter(long productId) {
            printCommentariesPerProduct(productId);
        }

        private void printCommentariesPerProduct(Long productId) {
            Product product = productService.findById(productId);
            List<Commentary> reviews = new ArrayList<Commentary>(commentaryApi.findReviewsForProduct(product));
            List<Commentary> questions = new ArrayList<Commentary>(commentaryApi.findQuestionsForProduct(product));

            System.out.println(MessageFormat.format(
                    "{0} has {1} commentaries.",
                    product.getName(),
                    commentaryApi.countCommentariesForProduct(product)
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
        private UserAccountPrinter() {
            printAllUserAccount();
        }

        private UserAccountPrinter(long userAccountId) {
            printUserAccountById(userAccountId);
        }

        private UserAccountPrinter(String surnameNameLogin) {
            printUserAccountBySurnameNameLogin(surnameNameLogin);
        }

        private void printAllUserAccount() {
            List<UserAccount> userAccounts = userAccountAPI.findAllUserAccount();
            for (UserAccount userAccount : userAccounts) {
                System.out.println(userAccount.toString());
            }
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

    private class CommunicationPrinter {

        private CommunicationPrinter(long userAccountId) {
            printCommunicationById(userAccountId);
        }

        private void printAllCommunication() {
            List<Communication> communications = userAccountAPI.findAllCommunication();
            for (Communication communication : communications) {
                System.out.println(communication.toString());
            }
        }

        private void printCommunicationById(Long userAccountId) {
            Communication communications = userAccountAPI.findCommunicationById(new UserAccount(userAccountId));
            System.out.println(communications.toString());
        }

    }

  /*  private class SubscriptionPrinter {

        private SubscriptionPrinter(long userAccountId) {
            printOrderById(userAccountId);
        }

        private void printAllOrders() {
            List<Subscription> Subscription = userAccountAPI.findAllSubscription();
            for (Communication communication : communications) {
                System.out.println(communication.toString());
            }
        }

        private void printOrderById(Long userAccountId) {
            Communication communications = userAccountAPI.findCommunicationById(new UserAccount(userAccountId));
            System.out.println(communications.toString());
        }

    }*/

}
