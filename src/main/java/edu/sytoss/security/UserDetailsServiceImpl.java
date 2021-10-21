package edu.sytoss.security;

import edu.sytoss.model.user.UserAccount;
import edu.sytoss.repository.UserAccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserDetailsServiceImpl(UserAccountRepository userAccountRepository) {
        System.out.println("UserDetailsServiceImpl.UserDetailsServiceImpl()");
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository.findByLogin(login);
        System.out.println("UserDetailsServiceImpl.loadUserByUsername() " + userAccount);
        UserDetails securityUser = SecurityUser.fromUser(userAccount);
        System.out.println("UserDetailsServiceImpl.loadUserByUsername() " +securityUser);
        return securityUser;
    }
}
