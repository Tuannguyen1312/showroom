package com.showcar.showcar.repository;

import com.showcar.showcar.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    
    List<Car> findByBrand_BrandId(Integer brandId);
    
    List<Car> findByCategory_CategoryId(Integer categoryId);
    
    List<Car> findByStatus(Car.CarStatus status);
    
    List<Car> findByConditions(Car.CarCondition conditions);
    
    List<Car> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    @Query("SELECT c FROM Car c WHERE c.brand.brandId = :brandId AND c.status = :status")
    List<Car> findByBrandAndStatus(@Param("brandId") Integer brandId, @Param("status") Car.CarStatus status);
    
    @Query("SELECT c FROM Car c WHERE c.category.categoryId = :categoryId AND c.status = :status")
    List<Car> findByCategoryAndStatus(@Param("categoryId") Integer categoryId, @Param("status") Car.CarStatus status);
    
    @Query("SELECT c FROM Car c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(c.brand.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Car> searchByKeyword(@Param("keyword") String keyword);
    
    @Query("SELECT c FROM Car c WHERE c.status = 'Available' ORDER BY c.createdAt DESC")
    List<Car> findAvailableCars();
}
