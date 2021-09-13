package edu.sytoss.repository;

import edu.sytoss.model.communication.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
    Claim findById(Long id);
}
