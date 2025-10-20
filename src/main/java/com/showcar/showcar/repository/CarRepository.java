package com.showcar.showcar.repository;

import com.showcar.showcar.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, String> {
    
    List<Car> findByBrandId(String brandId);
    
    List<Car> findByCategoryId(String categoryId);
    
    List<Car> findByStatus(Car.CarStatus status);
    
    List<Car> findByCondition(Car.CarCondition condition);
    
    List<Car> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    @Query("SELECT c FROM Car c WHERE c.brand.id = :brandId AND c.status = :status")
    List<Car> findByBrandAndStatus(@Param("brandId") String brandId, @Param("status") Car.CarStatus status);
    
    @Query("SELECT c FROM Car c WHERE c.category.id = :categoryId AND c.status = :status")
    List<Car> findByCategoryAndStatus(@Param("categoryId") String categoryId, @Param("status") Car.CarStatus status);
    
    @Query("SELECT c FROM Car c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(c.brand.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Car> searchByKeyword(@Param("keyword") String keyword);
}
