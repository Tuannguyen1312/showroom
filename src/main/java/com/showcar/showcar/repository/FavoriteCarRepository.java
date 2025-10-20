package com.showcar.showcar.repository;

import com.showcar.showcar.model.FavoriteCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteCarRepository extends JpaRepository<FavoriteCar, String> {
    
    List<FavoriteCar> findByCustomerId(String customerId);
    
    Optional<FavoriteCar> findByCustomerIdAndCarId(String customerId, String carId);
    
    boolean existsByCustomerIdAndCarId(String customerId, String carId);
    
    void deleteByCustomerIdAndCarId(String customerId, String carId);
}
