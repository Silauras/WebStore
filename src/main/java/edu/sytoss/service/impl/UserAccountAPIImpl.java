package edu.sytoss.service.impl;

import edu.sytoss.repository.CommentaryRepository;
import edu.sytoss.repository.CommunicationRepository;
import edu.sytoss.repository.SubscriptionRepository;
import edu.sytoss.repository.UserAccountRepository;
import edu.sytoss.service.UserAccountAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public String showSubscription(Long id) {
        return subscriptionRepository.findById(id).toString();
    }

    @Override
    public String showCommunication(Long id) {
        return communicationRepository.findById(id).toString();
    }

}
