package com.showcar.showcar.service;

import com.showcar.showcar.dto.CarCategoryDTO;
import com.showcar.showcar.model.CarCategory;
import com.showcar.showcar.repository.CarCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CarCategoryService {
    
    private final CarCategoryRepository carCategoryRepository;
    
    // Lấy tất cả categories
    public List<CarCategoryDTO> getAllCategories() {
        return carCategoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Lấy category theo ID
    public CarCategoryDTO getCategoryById(Integer id) {
        CarCategory category = carCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return convertToDTO(category);
    }
    
    // Tạo category mới
    public CarCategoryDTO createCategory(CarCategoryDTO categoryDTO) {
        if (carCategoryRepository.existsByName(categoryDTO.getName())) {
            throw new RuntimeException("Category with name " + categoryDTO.getName() + " already exists");
        }
        
        CarCategory category = convertToEntity(categoryDTO);
        CarCategory savedCategory = carCategoryRepository.save(category);
        return convertToDTO(savedCategory);
    }
    
    // Cập nhật category
    public CarCategoryDTO updateCategory(Integer id, CarCategoryDTO categoryDTO) {
        CarCategory category = carCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        
        CarCategory updatedCategory = carCategoryRepository.save(category);
        return convertToDTO(updatedCategory);
    }
    
    // Xóa category
    public void deleteCategory(Integer id) {
        if (!carCategoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        carCategoryRepository.deleteById(id);
    }
    
    // Convert Entity to DTO
    private CarCategoryDTO convertToDTO(CarCategory category) {
        CarCategoryDTO dto = new CarCategoryDTO();
        dto.setCategoryId(category.getCategoryId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }
    
    // Convert DTO to Entity
    private CarCategory convertToEntity(CarCategoryDTO dto) {
        CarCategory category = new CarCategory();
        category.setCategoryId(dto.getCategoryId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }
}
