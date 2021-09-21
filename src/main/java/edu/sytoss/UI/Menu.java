package edu.sytoss.UI;

import edu.sytoss.model.communication.Answer;
import edu.sytoss.model.communication.Claim;
import edu.sytoss.model.communication.Commentary;
import edu.sytoss.model.communication.Dialog;
import edu.sytoss.model.order.Order;
import edu.sytoss.model.user.Communication;
import edu.sytoss.model.user.Subscription;
import edu.sytoss.model.user.UserAccount;
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
            System.out.println("1. Show UserAccount");
            System.out.println("2. Product with all commentaries");
            System.out.println("3. Catalog with all possible characteristics");
            System.out.println("4. Update User");
            System.out.println("5. Create User");
            switch (scanner.nextInt()) {
                case -1:
                    return;
                case 1:
                    findUserAccount();
                    break;
                case 2:
                    printCommentaries();
                    break;
                case 3:
                    printAllPossibleCharacteristics();
                    break;
                case 4:
                    System.out.println("Write id UserAccount");
                    scanner.nextLine();
                    int id = scanner.nextInt();
                    createUserAccount(id);
                    break;
                case 5:
                    createUserAccount(0);
                    break;
            }
        }
    }

    private void createUserAccount(int id) {
        System.out.println("Write your Surname and Name:");
        scanner.nextLine();
        String[] surnameAndName = scanner.nextLine().split(" ");
        String surname = surnameAndName[0];
        String name = surnameAndName[1];
        System.out.println("Write your Login: ");
        String login = scanner.nextLine();
        System.out.println("Write your Password:");
        String password = scanner.nextLine();
        Date registrationDate = new Date();
        System.out.println("Wait 2 second");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Date lastActivityDate = new Date();
        String newRole = "CUSTOMER";
        UserAccount newUserAccount = new UserAccount(surname, name, login, password, registrationDate, lastActivityDate, newRole);
        if (id == 0) {
            new UserAccountCreator(newUserAccount);
        } else {
            new UserAccountCreator(newUserAccount, id);
        }
    }

    private void findUserAccount() {
        System.out.println("-1. Quit");
        System.out.println("1. Find User by id");
        System.out.println("2. Find User by Name and/or Surname or Login" + "\n" +
                "   (You can use path of Name/Surname/Login)");
        System.out.println("3. Find User by Role");
        long countUser = userAccountAPI.countAllUserAccount();
        switch (scanner.nextInt()) {
            case -1:
                return;
            case 1:
                System.out.println("What you want to see?");
                System.out.println("1. Show UserAccount with main info");
                System.out.println("2. Show UserAccount with Communication");
                System.out.println("3. Show UserAccount with Subscription");
                System.out.println("4. Show UserAccount with Orders");
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
                            for (long id = 1; id <= countUser; id++) {
                                new UserAccountPrinter(id);
                                new CommunicationPrinter(id);
                            }
                        } else {
                            new UserAccountPrinter(userAccountId);
                            new CommunicationPrinter(userAccountId);
                            break;
                        }
                    case 3:
                        System.out.println("Write UserAccount id(0 for all)");
                        userAccountId = scanner.nextInt();
                        if (userAccountId == 0) {
                            for (long id = 1; id <= countUser; id++) {
                                new UserAccountPrinter(id);
                                new SubscriptionPrinter(id);
                            }
                        } else {
                            new UserAccountPrinter(userAccountId);
                            new SubscriptionPrinter(userAccountId);
                            break;
                        }
                    case 4:
                        System.out.println("Write UserAccount id(0 for all)");
                        userAccountId = scanner.nextInt();
                        if (userAccountId == 0) {
                            for (long id = 1; id <= countUser; id++) {
                                new UserAccountPrinter(id);
                                new OrderPrinter(id);
                            }
                        } else {
                            new UserAccountPrinter(userAccountId);
                            new OrderPrinter(userAccountId);
                            break;
                        }
                    }
                break;
            case 2:
                System.out.println("Write Name and/or Surname or Login(if Login start with'@')");
                scanner.nextLine();
                String surnameNameLogin = scanner.nextLine();
                new UserAccountPrinter(surnameNameLogin);
                break;
            case 3:
                System.out.println("Write Role start with '$'");
                scanner.nextLine();
                String role = scanner.nextLine();
                new UserAccountPrinter(role);
                break;
        }
    }

    private void printCommentaries() {
        System.out.println("Write product id or 0 to show all products");
        long productId = scanner.nextInt(); // I don't think that in test you will use BIG number;
        if (productId == 0) {
            List<ProductCard> productCards = productApi.findAllProductCards();
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
                if (!characteristics.containsKey(characteristic.getName()))
                    characteristics.put(characteristic.getName(), new ArrayList<String>());
                if (!characteristics.get(characteristic.getName()).contains(characteristic.getValue())) {
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

    private class SubscriptionPrinter {

        private SubscriptionPrinter(long userAccountId) {
            printSubscriptionsById(userAccountId);
        }

        private void printAllSubscriptions() {
            List<Subscription> subscriptions = userAccountAPI.findAllSubscription();
            for (Subscription subscription : subscriptions) {
                System.out.println(subscription.toString());
            }
        }

        private void printSubscriptionsById(Long userAccountId) {
            List<Subscription> subscriptions = userAccountAPI.findAllSubscriptionOnUserAccountById(new UserAccount(userAccountId));

            for (Subscription subscription : subscriptions) {
                System.out.println(subscription.toString());

            }
        }

    }

    private class OrderPrinter {

        private OrderPrinter(long userAccountId) {
            printOrderById(userAccountId);
        }

        private void printAllOrdersPrinter() {
            List<Subscription> subscriptions = userAccountAPI.findAllSubscription();
            for (Subscription subscription : subscriptions) {
                System.out.println(subscription.toString());
            }
        }

        private void printOrderById(Long userAccountId) {
            List<Order> orders = userAccountAPI.findAllOrderOnUserAccountById(new UserAccount(userAccountId));
            for (Order order : orders) {
                System.out.println(order.toString());

            }
        }
    }

    private class UserAccountCreator {


        private UserAccountCreator(UserAccount userAccount) {
            createUserAccount(userAccount);
        }

        public UserAccountCreator(UserAccount newUserAccount, long id) {
            updateUserAccount(newUserAccount, id);
        }

        private void createUserAccount(UserAccount userAccount) {
            userAccountAPI.createUserAccount(userAccount);
        }

        private void updateUserAccount(UserAccount userAccount, long id) {
            userAccountAPI.updateUserAccount(userAccount, id);
        }
    }
}