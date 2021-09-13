package edu.sytoss.repository;

import edu.sytoss.model.user.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {
    Subscription findById(Long id);
}
