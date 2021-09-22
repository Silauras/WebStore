package edu.sytoss.repository;

import edu.sytoss.model.user.Communication;
import edu.sytoss.model.user.UserAccount;
import edu.sytoss.model.user.UserAccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    @Query("select u from UserAccount u where u.id = ?1")
    UserAccount findById(Long id);

    @Query("select u from UserAccount u where upper(u.login) like upper(concat(?1, '%'))")
    List<UserAccount> findUserByLoginStartingWithIgnoreCase(String str);

    @Query("select u from UserAccount u where u.name like ?1% or upper(u.surname) like upper(concat(?1, '%'))")
    List<UserAccount> findUserByNameStartingWithOrSurnameStartingWithIgnoreCase(String str);

    @Query("select u from UserAccount u where u.name like ?1% and upper(u.surname) like upper(concat(?2, '%'))")
    List<UserAccount> findUserByNameStartingWithAndSurnameStartingWithIgnoreCase(String name, String surname);

    @Query("select u from UserAccount u where u.surname like ?1% and upper(u.name) like upper(concat(?2, '%'))")
    List<UserAccount> findUserBySurnameStartingWithAndNameStartingWithIgnoreCase(String surname, String name);

    @Query("select u from UserAccount u where upper(u.role) like upper(concat(?1, '%'))")
    List<UserAccount> findByRoleStartingWithIgnoreCase(String role);

    @Query("select u from UserAccount u left join fetch u.communication where u.id = ?1")
    UserAccount findUserAccountWithCommunicationById(Long id);

    @Query("select u from UserAccount u left join fetch    u.reactions   where u.id = ?1")
    UserAccount findUserAccountWithReactionById(Long id);

    @Query("select u from UserAccount u left join fetch    u.orders   where u.id = ?1")
    UserAccount findUserAccountWithOrderById(Long id);


   /* @Query("select u\n" +
            "from UserAccount u\n" +
            "left join fetch subscription_subscribers ss on u.id = ss.subscriber_id\n" +
            "left join fetch Subscription s on s.id =ss.subscription_id\n" +
            "where u.id =?1\n"? )
    UserAccount findUserAccountWithSubscriptionById(Long id);*/

    @Query("select u from UserAccount u left join fetch    u.subscriptions   where u.id = ?1")
    UserAccount findUserAccountWithSubscriptionsById(Long id);
}
