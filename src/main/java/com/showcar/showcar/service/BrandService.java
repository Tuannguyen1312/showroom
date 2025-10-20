package com.showcar.showcar.service;

import com.showcar.showcar.dto.BrandDTO;
import com.showcar.showcar.model.Brand;
import com.showcar.showcar.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandService {
    
    private final BrandRepository brandRepository;
    
    public List<BrandDTO> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public BrandDTO getBrandById(String id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));
        return convertToDTO(brand);
    }
    
    public BrandDTO getBrandByName(String name) {
        Brand brand = brandRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Brand not found with name: " + name));
        return convertToDTO(brand);
    }
    
    @Transactional
    public BrandDTO createBrand(BrandDTO brandDTO) {
        if (brandRepository.existsByName(brandDTO.getName())) {
            throw new RuntimeException("Brand already exists with name: " + brandDTO.getName());
        }
        
        Brand brand = new Brand();
        brand.setName(brandDTO.getName());
        brand.setOrigin(brandDTO.getOrigin());
        brand.setDescription(brandDTO.getDescription());
        
        Brand savedBrand = brandRepository.save(brand);
        return convertToDTO(savedBrand);
    }
    
    @Transactional
    public BrandDTO updateBrand(String id, BrandDTO brandDTO) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        
        if (brandDTO.getName() != null) brand.setName(brandDTO.getName());
        if (brandDTO.getOrigin() != null) brand.setOrigin(brandDTO.getOrigin());
        if (brandDTO.getDescription() != null) brand.setDescription(brandDTO.getDescription());
        
        Brand updatedBrand = brandRepository.save(brand);
        return convertToDTO(updatedBrand);
    }
    
    @Transactional
    public void deleteBrand(String id) {
        if (!brandRepository.existsById(id)) {
            throw new RuntimeException("Brand not found");
        }
        brandRepository.deleteById(id);
    }
    
    private BrandDTO convertToDTO(Brand brand) {
        BrandDTO dto = new BrandDTO();
        dto.setId(brand.getId());
        dto.setName(brand.getName());
        dto.setOrigin(brand.getOrigin());
        dto.setDescription(brand.getDescription());
        return dto;
    }
}
