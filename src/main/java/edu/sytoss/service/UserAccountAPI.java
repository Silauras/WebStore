package edu.sytoss.service;

import edu.sytoss.model.user.Communication;
import edu.sytoss.model.user.Subscription;
import edu.sytoss.model.user.UserAccount;

import java.util.List;

public interface UserAccountAPI {
    /*------------------------Subscription-----------------------------*/
    Subscription findSubscriptionById(Long id);

    /*------------------------Communication----------------------------*/
    Communication findCommunicationById(Long id);

    List<Communication> showAllCommunication();

    /*--------------------------UserAccount---------------------------*/
    List<UserAccount> findUserAccount(UserAccount userAccount);
    List<UserAccount> findAllUserAccount();

    
}
