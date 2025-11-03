package com.showcar.showcar.controller;

import com.showcar.showcar.dto.BrandDTO;
import com.showcar.showcar.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API Controller cho Brand (Thương hiệu xe)
 * Base URL: /api/brands
 */
@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BrandController {
    
    private final BrandService brandService;
    
    /**
     * Lấy tất cả thương hiệu xe
     * GET /api/brands
     */
    @GetMapping
    public ResponseEntity<List<BrandDTO>> getAllBrands() {
        List<BrandDTO> brands = brandService.getAllBrands();
        return ResponseEntity.ok(brands);
    }
    
    /**
     * Lấy thương hiệu theo ID
     * GET /api/brands/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<BrandDTO> getBrandById(@PathVariable Integer id) {
        try {
            BrandDTO brand = brandService.getBrandById(id);
            return ResponseEntity.ok(brand);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Tìm thương hiệu theo tên
     * GET /api/brands/search?name=Toyota
     */
    @GetMapping("/search")
    public ResponseEntity<BrandDTO> findByName(@RequestParam String name) {
        try {
            BrandDTO brand = brandService.findByName(name);
            return ResponseEntity.ok(brand);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Tạo thương hiệu mới (ADMIN)
     * POST /api/brands
     */
    @PostMapping
    public ResponseEntity<BrandDTO> createBrand(@RequestBody BrandDTO brandDTO) {
        try {
            BrandDTO created = brandService.createBrand(brandDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Cập nhật thương hiệu (ADMIN)
     * PUT /api/brands/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<BrandDTO> updateBrand(
            @PathVariable Integer id,
            @RequestBody BrandDTO brandDTO) {
        try {
            BrandDTO updated = brandService.updateBrand(id, brandDTO);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Xóa thương hiệu (ADMIN)
     * DELETE /api/brands/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Integer id) {
        try {
            brandService.deleteBrand(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
