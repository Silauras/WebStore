package edu.sytoss.service.impl;

import edu.sytoss.model.user.Communication;
import edu.sytoss.model.user.Subscription;
import edu.sytoss.model.user.UserAccount;
import edu.sytoss.repository.CommunicationRepository;
import edu.sytoss.repository.SubscriptionRepository;
import edu.sytoss.repository.UserAccountRepository;
import edu.sytoss.service.UserAccountAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserAccountAPIImpl implements UserAccountAPI {

    @Autowired
    UserAccountRepository userAccountRepository;
    @Autowired
    CommunicationRepository communicationRepository;
    @Autowired
    SubscriptionRepository subscriptionRepository;


    @Override
    public Subscription findSubscriptionById(UserAccount userAccount) {
        return null;
    }

    @Override
    public List<Subscription> findAllSubscription() {
        return null;
    }

    /*-------------------------------------------------------------------*/
    @Override
    public Communication findCommunicationById(UserAccount userAccount) {
        return communicationRepository.findById(userAccount.getId());
    }

    @Override
    public List<Communication> findAllCommunication() {
        return communicationRepository.findAll();
    }

    /*-------------------------------------------------------------------*/
    @Override
    public List<UserAccount> findUserAccount(UserAccount userAccount) {
        List<UserAccount> userAccounts = new ArrayList<>();
        if (userAccount.getId() != null) {
            userAccounts.add(userAccountRepository.findById(userAccount.getId()));
        }
        if (userAccount.getLogin() != null) {
            userAccounts.addAll(userAccountRepository.findUserByLoginStartingWithIgnoreCase(userAccount.getLogin()));
        }
        if (userAccount.getName() != null && userAccount.getSurname() != null
                && userAccount.getSurname().equals(userAccount.getName())) {
            userAccounts.addAll(userAccountRepository.findUserByNameStartingWithOrSurnameStartingWithIgnoreCase(userAccount.getName()));
        }
        if (userAccount.getName() != null && userAccount.getSurname() != null
                && !userAccount.getSurname().equals(userAccount.getName())) {
            userAccounts.addAll(userAccountRepository
                    .findUserByNameStartingWithAndSurnameStartingWithIgnoreCase(userAccount.getSurname(), userAccount.getName()));
            userAccounts.addAll(userAccountRepository
                    .findUserBySurnameStartingWithAndNameStartingWithIgnoreCase(userAccount.getSurname(), userAccount.getName()));
        }
        if (userAccount.getRole() != null) {
            userAccounts.addAll(userAccountRepository.findByRoleStartingWithIgnoreCase(userAccount.getRole()));
        }
        return userAccounts;
    }

    @Override
    public List<UserAccount> findAllUserAccount() {
        return userAccountRepository.findAll();
    }


}
