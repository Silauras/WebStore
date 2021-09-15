package edu.sytoss.service;

import edu.sytoss.dto.UserAccountDTO;
import edu.sytoss.model.user.Communication;
import edu.sytoss.model.user.Subscription;
import edu.sytoss.model.user.UserAccount;

import java.util.List;

public interface UserAccountAPI {

    Subscription findSubscriptionById(Long id);

    /*-------------------------------------------------------------------*/
    Communication findCommunicationById(Long id);

    List<Communication> showAllCommunication();

    /*-------------------------------------------------------------------*/
    List<UserAccount> findUserAccount(UserAccountDTO userAccountDTO);
}
