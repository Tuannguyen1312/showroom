package com.showcar.showcar.controller;

import com.showcar.showcar.dto.CarCategoryDTO;
import com.showcar.showcar.service.CarCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API Controller cho Car Category (Danh mục xe: Sedan, SUV, Pickup...)
 * Base URL: /api/categories
 */
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CarCategoryController {
    
    private final CarCategoryService carCategoryService;
    
    /**
     * Lấy tất cả danh mục xe
     * GET /api/categories
     */
    @GetMapping
    public ResponseEntity<List<CarCategoryDTO>> getAllCategories() {
        List<CarCategoryDTO> categories = carCategoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
    
    /**
     * Lấy danh mục theo ID
     * GET /api/categories/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<CarCategoryDTO> getCategoryById(@PathVariable Integer id) {
        try {
            CarCategoryDTO category = carCategoryService.getCategoryById(id);
            return ResponseEntity.ok(category);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Tạo danh mục mới (ADMIN)
     * POST /api/categories
     */
    @PostMapping
    public ResponseEntity<CarCategoryDTO> createCategory(@RequestBody CarCategoryDTO categoryDTO) {
        try {
            CarCategoryDTO created = carCategoryService.createCategory(categoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Cập nhật danh mục (ADMIN)
     * PUT /api/categories/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<CarCategoryDTO> updateCategory(
            @PathVariable Integer id,
            @RequestBody CarCategoryDTO categoryDTO) {
        try {
            CarCategoryDTO updated = carCategoryService.updateCategory(id, categoryDTO);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Xóa danh mục (ADMIN)
     * DELETE /api/categories/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        try {
            carCategoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
