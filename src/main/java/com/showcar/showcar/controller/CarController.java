package com.showcar.showcar.controller;

import com.showcar.showcar.dto.CarDTO;
import com.showcar.showcar.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * REST API Controller cho Car (Xe)
 * Base URL: /api/cars
 * 
 * Chức năng:
 * - Danh sách xe (lọc theo brand, category, giá, status)
 * - Chi tiết xe
 * - Tìm kiếm xe
 * - CRUD xe (ADMIN)
 */
@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CarController {
    
    private final CarService carService;
    
    /**
     * Lấy tất cả xe
     * GET /api/cars
     */
    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars() {
        List<CarDTO> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }
    
    /**
     * Lấy xe available (còn hàng)
     * GET /api/cars/available
     */
    @GetMapping("/available")
    public ResponseEntity<List<CarDTO>> getAvailableCars() {
        List<CarDTO> cars = carService.getAvailableCars();
        return ResponseEntity.ok(cars);
    }
    
    /**
     * Lấy chi tiết xe theo ID
     * GET /api/cars/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable Integer id) {
        try {
            CarDTO car = carService.getCarById(id);
            return ResponseEntity.ok(car);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Lọc xe theo thương hiệu
     * GET /api/cars/brand/{brandId}
     */
    @GetMapping("/brand/{brandId}")
    public ResponseEntity<List<CarDTO>> getCarsByBrand(@PathVariable Integer brandId) {
        List<CarDTO> cars = carService.getCarsByBrand(brandId);
        return ResponseEntity.ok(cars);
    }
    
    /**
     * Lọc xe theo danh mục (Sedan, SUV, Pickup...)
     * GET /api/cars/category/{categoryId}
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<CarDTO>> getCarsByCategory(@PathVariable Integer categoryId) {
        List<CarDTO> cars = carService.getCarsByCategory(categoryId);
        return ResponseEntity.ok(cars);
    }
    
    /**
     * Lọc xe theo trạng thái (Available, Sold, Discontinued)
     * GET /api/cars/status/{status}
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<CarDTO>> getCarsByStatus(@PathVariable String status) {
        try {
            List<CarDTO> cars = carService.getCarsByStatus(status);
            return ResponseEntity.ok(cars);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Lọc xe theo khoảng giá
     * GET /api/cars/price-range?min=500000000&max=1000000000
     */
    @GetMapping("/price-range")
    public ResponseEntity<List<CarDTO>> getCarsByPriceRange(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        List<CarDTO> cars = carService.getCarsByPriceRange(min, max);
        return ResponseEntity.ok(cars);
    }
    
    /**
     * Tìm kiếm xe theo từ khóa (tên xe, thương hiệu)
     * GET /api/cars/search?keyword=Mercedes
     */
    @GetMapping("/search")
    public ResponseEntity<List<CarDTO>> searchCars(@RequestParam String keyword) {
        List<CarDTO> cars = carService.searchCars(keyword);
        return ResponseEntity.ok(cars);
    }
    
    /**
     * Tạo xe mới (ADMIN)
     * POST /api/cars
     */
    @PostMapping
    public ResponseEntity<CarDTO> createCar(@RequestBody CarDTO carDTO) {
        try {
            CarDTO created = carService.createCar(carDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Cập nhật xe (ADMIN)
     * PUT /api/cars/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<CarDTO> updateCar(
            @PathVariable Integer id,
            @RequestBody CarDTO carDTO) {
        try {
            CarDTO updated = carService.updateCar(id, carDTO);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Xóa xe (ADMIN)
     * DELETE /api/cars/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Integer id) {
        try {
            carService.deleteCar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
