package com.showcar.showcar.repository;

import com.showcar.showcar.model.CarImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarImageRepository extends JpaRepository<CarImage, String> {
    
    List<CarImage> findByCarId(String carId);
    
    Optional<CarImage> findByCarIdAndIsPrimaryTrue(String carId);
    
    void deleteByCarId(String carId);
}
