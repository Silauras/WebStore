package edu.sytoss.service.impl;

import edu.sytoss.model.communication.Dialog;
import edu.sytoss.model.order.Order;
import edu.sytoss.model.user.Communication;
import edu.sytoss.model.user.Subscription;
import edu.sytoss.model.user.UserAccount;
import edu.sytoss.repository.CommunicationRepository;
import edu.sytoss.repository.SubscriptionRepository;
import edu.sytoss.repository.UserAccountRepository;
import edu.sytoss.service.UserAccountAPI;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public List<Subscription> findAllSubscriptionOnUserAccountById(UserAccount userAccount) {
        List<Subscription> subscriptions = userAccountRepository.findById(userAccount.getId()).getSubscriptions();
        Hibernate.initialize(subscriptions);
        return subscriptions;
    }

    @Override
    public List<Order> findAllOrderOnUserAccountById(UserAccount userAccount) {
        System.out.println(" dfdsf df sdf ds fdsf ");
        UserAccount ua = userAccountRepository.findById(8l);
        System.out.println(ua.getOrders().toString());
        List<Order> orders = new ArrayList<>();
        Hibernate.initialize(orders);
        return orders;
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


    @Override
    public List<Order> findAllOrder() {
        return null;
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
