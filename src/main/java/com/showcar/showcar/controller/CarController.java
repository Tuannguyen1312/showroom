package com.showcar.showcar.controller;

import com.showcar.showcar.dto.CarDTO;
import com.showcar.showcar.model.Car;
import com.showcar.showcar.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CarController {
    
    private final CarService carService;
    
    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable String id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }
    
    @GetMapping("/brand/{brandId}")
    public ResponseEntity<List<CarDTO>> getCarsByBrand(@PathVariable String brandId) {
        return ResponseEntity.ok(carService.getCarsByBrand(brandId));
    }
    
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<CarDTO>> getCarsByCategory(@PathVariable String categoryId) {
        return ResponseEntity.ok(carService.getCarsByCategory(categoryId));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<CarDTO>> getCarsByStatus(@PathVariable Car.CarStatus status) {
        return ResponseEntity.ok(carService.getCarsByStatus(status));
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<CarDTO>> searchCars(@RequestParam String keyword) {
        return ResponseEntity.ok(carService.searchCars(keyword));
    }
    
    @GetMapping("/price-range")
    public ResponseEntity<List<CarDTO>> getCarsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        return ResponseEntity.ok(carService.getCarsByPriceRange(minPrice, maxPrice));
    }
    
    @PostMapping
    public ResponseEntity<CarDTO> createCar(@RequestBody CarDTO carDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(carService.createCar(carDTO));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CarDTO> updateCar(
            @PathVariable String id,
            @RequestBody CarDTO carDTO) {
        return ResponseEntity.ok(carService.updateCar(id, carDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable String id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}
