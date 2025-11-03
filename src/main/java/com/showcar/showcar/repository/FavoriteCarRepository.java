package com.showcar.showcar.repository;

import com.showcar.showcar.model.FavoriteCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteCarRepository extends JpaRepository<FavoriteCar, Integer> {
    
    List<FavoriteCar> findByCustomer_CustomerId(Integer customerId);
    
    Optional<FavoriteCar> findByCustomer_CustomerIdAndCar_CarId(Integer customerId, Integer carId);
    
    boolean existsByCustomer_CustomerIdAndCar_CarId(Integer customerId, Integer carId);
    
    void deleteByCustomer_CustomerIdAndCar_CarId(Integer customerId, Integer carId);
}
