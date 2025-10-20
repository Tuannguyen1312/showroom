package com.showcar.showcar.controller;

import com.showcar.showcar.dto.BrandDTO;
import com.showcar.showcar.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BrandController {
    
    private final BrandService brandService;
    
    @GetMapping
    public ResponseEntity<List<BrandDTO>> getAllBrands() {
        return ResponseEntity.ok(brandService.getAllBrands());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BrandDTO> getBrandById(@PathVariable String id) {
        return ResponseEntity.ok(brandService.getBrandById(id));
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<BrandDTO> getBrandByName(@PathVariable String name) {
        return ResponseEntity.ok(brandService.getBrandByName(name));
    }
    
    @PostMapping
    public ResponseEntity<BrandDTO> createBrand(@RequestBody BrandDTO brandDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(brandService.createBrand(brandDTO));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<BrandDTO> updateBrand(
            @PathVariable String id,
            @RequestBody BrandDTO brandDTO) {
        return ResponseEntity.ok(brandService.updateBrand(id, brandDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable String id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }
}
