package edu.sytoss.UI;

import edu.sytoss.model.communication.Reaction;
import edu.sytoss.model.order.Order;
import edu.sytoss.model.user.Communication;
import edu.sytoss.model.user.Subscription;
import edu.sytoss.model.user.UserAccount;
import edu.sytoss.service.UserAccountAPI;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Service
public class UserAccountMenu {
    @Autowired
    UserAccountAPI userAccountAPI;

    public void start() {
        MenuUtils.printMenu(
                "-1. Quit",
                "1. Show UserAccount",
                "2. Create UserAccount",
                "3. Update UserAccount"
        );
        switch (MenuUtils.scanInt("Your choice")) {
            case -1:
                return;
            case 1:
                findUserAccount();
                break;
            case 2:
                createUserAccount(0);
                break;
            case 3:
                int id = MenuUtils.scanInt("Write id UserAccount: ");
                createUserAccount(id);
                break;
        }
    }

    private void findUserAccount() {
        MenuUtils.printMenu(
                "-1.Quit",
                "1. Find User by id",
                "2. Find User by Name and/or Surname or Login",
                "   (You can use path of Name/Surname/Login)",
                "3. Find User by Role"
        );

        switch (MenuUtils.scanInt("")) {
            case -1:
                return;
            case 1:
                findUserById();
                break;
            case 2:
                String surnameNameLogin = MenuUtils.scanLine("Write Name and/or Surname or Login\n" +
                        "(if Login start with'@'): ");
                new UserAccountPrinter(surnameNameLogin);
                break;
            case 3:
                String role = MenuUtils.scanLine("Write Role start with '$'");
                new UserAccountPrinter(role);
                break;
        }
    }

    private void findUserById() {
        MenuUtils.printMenu(
                "What you want to see?",
                "1. Show UserAccount with main info",
                "2. Show UserAccount with Communication",
                "3. Show UserAccount with Subscription",
                "4. Show UserAccount with Orders",
                "5. Show UserAccount with Reaction"
        );
        long userAccountId;
        switch (MenuUtils.scanInt("")) {
            case 1:
                userAccountId = MenuUtils.scanInt("Write UserAccount id(0 for all): ");
                if (userAccountId == 0) {
                    new UserAccountPrinter();
                } else {
                    new UserAccountPrinter(userAccountId);
                    break;
                }
                break;
            case 2:
                userAccountId = MenuUtils.scanInt("Write UserAccount id(0 for all): ");
                if (userAccountId == 0) {
                    List<UserAccount> userAccounts = userAccountAPI.findAllUserAccount();
                    for (UserAccount userAccount : userAccounts) {
                        new UserAccountPrinter(userAccount.getId());
                        new CommunicationPrinter(userAccount.getId());
                    }
                } else {
                    new UserAccountPrinter(userAccountId);
                    new CommunicationPrinter(userAccountId);
                    break;
                }
                break;
            case 3:
                userAccountId = MenuUtils.scanInt("Write UserAccount id(0 for all): ");
                if (userAccountId == 0) {
                    List<UserAccount> userAccounts = userAccountAPI.findAllUserAccount();
                    for (UserAccount userAccount : userAccounts) {
                        new UserAccountPrinter(userAccount.getId());
                        new SubscriptionPrinter(userAccount.getId());
                    }
                } else {
                    new UserAccountPrinter(userAccountId);
                    new SubscriptionPrinter(userAccountId);
                    break;
                }
                break;
            case 4:
                userAccountId = MenuUtils.scanInt("Write UserAccount id(0 for all): ");
                if (userAccountId == 0) {
                    List<UserAccount> userAccounts = userAccountAPI.findAllUserAccount();
                    for (UserAccount userAccount : userAccounts) {
                        new UserAccountPrinter(userAccount.getId());
                        new OrderPrinter(userAccount.getId());
                    }
                } else {
                    new UserAccountPrinter(userAccountId);
                    new OrderPrinter(userAccountId);
                    break;
                }
                break;
            case 5:
                userAccountId = MenuUtils.scanInt("Write UserAccount id(0 for all): ");
                if (userAccountId == 0) {
                    List<UserAccount> userAccounts = userAccountAPI.findAllUserAccount();
                    for (UserAccount userAccount : userAccounts) {
                        new UserAccountPrinter(userAccount.getId());
                        new ReactionPrinter(userAccount.getId());
                    }
                } else {
                    new UserAccountPrinter(userAccountId);
                    new ReactionPrinter(userAccountId);
                    break;
                }
        }
    }

    private void printUserAccountById(Long userAccountId) {
        List<UserAccount> userAccounts = userAccountAPI.findUserAccount(new UserAccount(userAccountId));
        System.out.println(userAccounts.toString());
    }

    private void createUserAccount(int id) {
        String[] surnameAndName = MenuUtils
                .scanLine("Write your Surname and Name:").split(" ");
        String surname = surnameAndName[0];
        String name = surnameAndName[1];
        String login = MenuUtils.scanLine("Write your Login: ");
        String password = MenuUtils.scanLine("Write your Password:");
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

        private void printCommunicationById(Long userAccountId) {
            List<Communication> communications = userAccountAPI.findCommunicationInUserAccountById(new UserAccount(userAccountId));
            System.out.println(communications.toString());
        }

    }

    private class OrderPrinter {

        private OrderPrinter(long userAccountId) {
            printOrderById(userAccountId);
        }

        private void printOrderById(Long userAccountId) {
            List<Order> orders = userAccountAPI.findAllOrderInUserAccountById(new UserAccount(userAccountId));
            System.out.println(orders.toString());
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

    private class ReactionPrinter {
        private ReactionPrinter(long userAccountId) {
            printReactionById(userAccountId);
        }

        private void printReactionById(Long userAccountId) {
            List<Reaction> reactions = userAccountAPI.findAllReactionInUserAccountById(new UserAccount(userAccountId));
            System.out.println(reactions.toString());
        }
    }

    private class SubscriptionPrinter {
        private SubscriptionPrinter(long userAccountId) {
            printSubscriptionById(userAccountId);
        }

        private void printSubscriptionById(Long userAccountId) {
            List<Subscription> subscriptions = userAccountAPI.findAllSubscriptionOnUserAccountById(new UserAccount(userAccountId));
            System.out.println(subscriptions.toString());
        }

    }
}
