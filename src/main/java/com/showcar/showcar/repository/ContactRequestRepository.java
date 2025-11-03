package com.showcar.showcar.repository;

import com.showcar.showcar.model.ContactRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRequestRepository extends JpaRepository<ContactRequest, Integer> {
    
    List<ContactRequest> findByEmail(String email);
    
    List<ContactRequest> findByPhone(String phone);
}
