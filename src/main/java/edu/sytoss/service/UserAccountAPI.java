package edu.sytoss.service;

import edu.sytoss.model.order.Order;
import edu.sytoss.model.user.Communication;
import edu.sytoss.model.user.Subscription;
import edu.sytoss.model.user.UserAccount;

import java.util.List;

public interface UserAccountAPI {
    /*------------------------Subscription-----------------------------*/
    List<Subscription> findAllSubscriptionOnUserAccountById(UserAccount userAccount);
    List<Subscription> findAllSubscription();

    /*------------------------Communication----------------------------*/
    List<Communication> findCommunicationInUserAccountById(UserAccount userAccount);
    List<Communication> findAllCommunication();

    /*-------------------------UserAccount----------------------------*/
    List<UserAccount> findUserAccount(UserAccount userAccount);
    List<UserAccount> findAllUserAccount();
    long countAllUserAccount();
    boolean createUserAccount(UserAccount userAccount);
    boolean updateUserAccount(UserAccount userAccount,long id);

    /*---------------------------Order-------------------------------*/
    List<Order> findAllOrderOnUserAccountById(UserAccount userAccount);
    List<Order> findAllOrder();

}
