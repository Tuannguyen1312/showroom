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
@Transactional
public class BrandService {
    
    private final BrandRepository brandRepository;
    
    // Lấy tất cả brands
    public List<BrandDTO> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Lấy brand theo ID
    public BrandDTO getBrandById(Integer id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));
        return convertToDTO(brand);
    }
    
    // Tạo brand mới
    public BrandDTO createBrand(BrandDTO brandDTO) {
        if (brandRepository.existsByName(brandDTO.getName())) {
            throw new RuntimeException("Brand with name " + brandDTO.getName() + " already exists");
        }
        
        Brand brand = convertToEntity(brandDTO);
        Brand savedBrand = brandRepository.save(brand);
        return convertToDTO(savedBrand);
    }
    
    // Cập nhật brand
    public BrandDTO updateBrand(Integer id, BrandDTO brandDTO) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));
        
        brand.setName(brandDTO.getName());
        brand.setOrigin(brandDTO.getOrigin());
        brand.setDescription(brandDTO.getDescription());
        
        Brand updatedBrand = brandRepository.save(brand);
        return convertToDTO(updatedBrand);
    }
    
    // Xóa brand
    public void deleteBrand(Integer id) {
        if (!brandRepository.existsById(id)) {
            throw new RuntimeException("Brand not found with id: " + id);
        }
        brandRepository.deleteById(id);
    }
    
    // Tìm brand theo tên
    public BrandDTO findByName(String name) {
        Brand brand = brandRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Brand not found with name: " + name));
        return convertToDTO(brand);
    }
    
    // Convert Entity to DTO
    private BrandDTO convertToDTO(Brand brand) {
        BrandDTO dto = new BrandDTO();
        dto.setBrandId(brand.getBrandId());
        dto.setName(brand.getName());
        dto.setOrigin(brand.getOrigin());
        dto.setDescription(brand.getDescription());
        return dto;
    }
    
    // Convert DTO to Entity
    private Brand convertToEntity(BrandDTO dto) {
        Brand brand = new Brand();
        brand.setBrandId(dto.getBrandId());
        brand.setName(dto.getName());
        brand.setOrigin(dto.getOrigin());
        brand.setDescription(dto.getDescription());
        return brand;
    }
}
