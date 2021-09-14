package edu.sytoss.service.impl;

import edu.sytoss.repository.UserAccountRepository;
import edu.sytoss.service.UserAccountAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserAccountAPIImpl /*implements UserAccountAPI */{
    @Autowired
    UserAccountRepository userAccountRepository;

   /* @Override*/
    public String showUserAccountAndContact(Long id) {
       return userAccountRepository.findById(id).getCommunication().toString();
    }
}
