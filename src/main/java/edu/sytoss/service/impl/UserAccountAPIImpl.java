package edu.sytoss.service.impl;

import edu.sytoss.model.communication.Reaction;
import edu.sytoss.model.order.Order;
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
    /*------------------------------- Repository---------------------------------------*/
    @Autowired
    UserAccountRepository userAccountRepository;
    @Autowired
    CommunicationRepository communicationRepository;
    @Autowired
    SubscriptionRepository subscriptionRepository;

    /*-------------------------------Subscription------------------------------------*/
    @Override
    public List<Subscription> findAllSubscriptionOnUserAccountById(UserAccount userAccount) {
        UserAccount u = userAccountRepository.findUserAccountWithSubscriptionsById(userAccount.getId());
        return u.getSubscriptions();
    }

    @Override
    public List<Subscription> findAllSubscription() {
        return subscriptionRepository.findAll();
    }

    /*-----------------------------Order--------------------------------------*/

    @Override
    public List<Order> findAllOrder() {
        return null;
    }

    @Override
    public List<Order> findUserAccountWithStateOrder(UserAccount userAccount, String state) {
        UserAccount u = userAccountRepository.findUserAccountWithOrderWhereState(userAccount.getId(), state);
        List<Order> orders = new ArrayList<>();
        if (u != null) {
            orders = u.getOrders();
        }
        return orders;
    }

    @Override
    public List<Order> findAllOrderInUserAccountById(UserAccount userAccount) {
        UserAccount u = userAccountRepository.findUserAccountWithOrderById(userAccount.getId());
        return u.getOrders();
    }
    /*-----------------------------Reaction--------------------------------------*/

    @Override
    public List<Reaction> findAllReaction() {
        return null;
    }

    @Override
    public List<Reaction> findAllReactionInUserAccountById(UserAccount userAccount) {
        UserAccount u = userAccountRepository.findUserAccountWithReactionById(userAccount.getId());
        return u.getReactions();
    }

    /*-------------------------------Communication------------------------------------*/
    @Override
    public List<Communication> findCommunicationInUserAccountById(UserAccount userAccount) {
        UserAccount u = userAccountRepository.findUserAccountWithCommunicationById(userAccount.getId());
        return u.getCommunication();
    }

    @Override
    public List<Communication> findAllCommunication() {
        return communicationRepository.findAll();
    }

    /*----------------------------UserAccount---------------------------------------*/
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

    @Override
    public long countAllUserAccount() {
        return userAccountRepository.count();
    }

    @Override
    public boolean createUserAccount(UserAccount userAccount) {
        try {
            userAccountRepository.saveAndFlush(userAccount);
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    public boolean updateUserAccount(UserAccount userAccount, long id) {
        try {
            UserAccount newUserAccount = userAccountRepository.findById(id);
            newUserAccount.setName(userAccount.getName());
            newUserAccount.setSurname(userAccount.getSurname());
            newUserAccount.setLogin(userAccount.getLogin());
            newUserAccount.setPassword(userAccount.getPassword());
            userAccountRepository.save(newUserAccount);
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }
}
