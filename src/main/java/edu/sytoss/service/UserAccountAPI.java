package edu.sytoss.service;

import edu.sytoss.model.user.Communication;
import edu.sytoss.model.user.Subscription;
import edu.sytoss.model.user.UserAccount;

import java.util.List;

public interface UserAccountAPI {
    /*------------------------Subscription-----------------------------*/
    Subscription findSubscriptionById(UserAccount userAccount);
    List<Subscription> findAllSubscription();

    /*------------------------Communication----------------------------*/
    Communication findCommunicationById(UserAccount userAccount);
    List<Communication> findAllCommunication();

    /*-------------------------UserAccount----------------------------*/
    List<UserAccount> findUserAccount(UserAccount userAccount);
    List<UserAccount> findAllUserAccount();


}
