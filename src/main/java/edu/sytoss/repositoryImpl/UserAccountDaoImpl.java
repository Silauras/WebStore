package edu.sytoss.repositoryImpl;

import edu.sytoss.model.user.UserAccount;
import edu.sytoss.repository.UserAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserAccountDaoImpl extends AbstractDao<Long, UserAccount> implements UserAccountDao {
    @Override
    public UserAccount findById(Long id) {
        return getByKey(id);
    }

    @Override
    public void save(UserAccount userAccount) {
        persist(userAccount);
    }

    @Override
    public List<UserAccount> findAllUserAccounts() {
        return (List<UserAccount>) getEntityManager()
                .createQuery("from UserAccount")
                .getResultList();
    }
}
