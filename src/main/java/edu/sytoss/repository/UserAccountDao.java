package edu.sytoss.repository;

import edu.sytoss.model.user.UserAccount;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccountDao {
    UserAccount findById(Long id);

    void save(UserAccount userAccount);

    List<UserAccount> findAllUserAccounts();
}
