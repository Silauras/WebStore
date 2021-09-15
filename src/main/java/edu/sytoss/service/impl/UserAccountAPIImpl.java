package edu.sytoss.service.impl;

import edu.sytoss.dto.UserAccountDTO;
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
    public Subscription findSubscriptionById(Long id) {
        return subscriptionRepository.findById(id);
    }
    /*-------------------------------------------------------------------*/
    @Override
    public Communication findCommunicationById(Long id) {
        return communicationRepository.findById(id);
    }

    @Override
    public List<Communication> showAllCommunication() {
        return communicationRepository.findAll();
    }
    /*-------------------------------------------------------------------*/
    @Override
    public List<UserAccount> findUserAccount(UserAccountDTO userAccountDTO) {
        List<UserAccount> userAccounts = new ArrayList<>();
        if(userAccountDTO.getId() != null){
            userAccounts.add(userAccountRepository.findById(userAccountDTO.getId()));
        }
        if (userAccountDTO.getFullName()!=null)
        {

        }
        return  userAccounts;
    }

}
