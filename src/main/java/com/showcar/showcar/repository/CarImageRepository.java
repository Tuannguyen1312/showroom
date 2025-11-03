package com.showcar.showcar.repository;

import com.showcar.showcar.model.CarImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarImageRepository extends JpaRepository<CarImage, Integer> {
    
    List<CarImage> findByCar_CarId(Integer carId);
    
    Optional<CarImage> findByCar_CarIdAndIsPrimary(Integer carId, Boolean isPrimary);
    
    void deleteByCar_CarId(Integer carId);
}
