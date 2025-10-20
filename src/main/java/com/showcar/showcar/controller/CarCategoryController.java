package com.showcar.showcar.controller;

import com.showcar.showcar.dto.CarCategoryDTO;
import com.showcar.showcar.service.CarCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CarCategoryController {
    
    private final CarCategoryService carCategoryService;
    
    @GetMapping
    public ResponseEntity<List<CarCategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(carCategoryService.getAllCategories());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CarCategoryDTO> getCategoryById(@PathVariable String id) {
        return ResponseEntity.ok(carCategoryService.getCategoryById(id));
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<CarCategoryDTO> getCategoryByName(@PathVariable String name) {
        return ResponseEntity.ok(carCategoryService.getCategoryByName(name));
    }
    
    @PostMapping
    public ResponseEntity<CarCategoryDTO> createCategory(@RequestBody CarCategoryDTO categoryDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(carCategoryService.createCategory(categoryDTO));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CarCategoryDTO> updateCategory(
            @PathVariable String id,
            @RequestBody CarCategoryDTO categoryDTO) {
        return ResponseEntity.ok(carCategoryService.updateCategory(id, categoryDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        carCategoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
