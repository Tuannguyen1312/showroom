package com.showcar.showcar.repository;

import com.showcar.showcar.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
    
    Optional<UserAccount> findByUsername(String username);
    
    boolean existsByUsername(String username);
    
    Optional<UserAccount> findByCustomerId(String customerId);
}
