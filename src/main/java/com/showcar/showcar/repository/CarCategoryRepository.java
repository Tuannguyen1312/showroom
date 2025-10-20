package com.showcar.showcar.repository;

import com.showcar.showcar.model.CarCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarCategoryRepository extends JpaRepository<CarCategory, String> {
    Optional<CarCategory> findByName(String name);
    boolean existsByName(String name);
}
