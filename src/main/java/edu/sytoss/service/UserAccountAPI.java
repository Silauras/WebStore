package edu.sytoss.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


public interface UserAccountAPI {
    String showUserAccountAndContact(Long id);
}
