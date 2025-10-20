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
public class CarCategoryService {
    
    private final CarCategoryRepository carCategoryRepository;
    
    public List<CarCategoryDTO> getAllCategories() {
        return carCategoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public CarCategoryDTO getCategoryById(String id) {
        CarCategory category = carCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return convertToDTO(category);
    }
    
    public CarCategoryDTO getCategoryByName(String name) {
        CarCategory category = carCategoryRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Category not found with name: " + name));
        return convertToDTO(category);
    }
    
    @Transactional
    public CarCategoryDTO createCategory(CarCategoryDTO categoryDTO) {
        if (carCategoryRepository.existsByName(categoryDTO.getName())) {
            throw new RuntimeException("Category already exists with name: " + categoryDTO.getName());
        }
        
        CarCategory category = new CarCategory();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        
        CarCategory savedCategory = carCategoryRepository.save(category);
        return convertToDTO(savedCategory);
    }
    
    @Transactional
    public CarCategoryDTO updateCategory(String id, CarCategoryDTO categoryDTO) {
        CarCategory category = carCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        
        if (categoryDTO.getName() != null) category.setName(categoryDTO.getName());
        if (categoryDTO.getDescription() != null) category.setDescription(categoryDTO.getDescription());
        
        CarCategory updatedCategory = carCategoryRepository.save(category);
        return convertToDTO(updatedCategory);
    }
    
    @Transactional
    public void deleteCategory(String id) {
        if (!carCategoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found");
        }
        carCategoryRepository.deleteById(id);
    }
    
    private CarCategoryDTO convertToDTO(CarCategory category) {
        CarCategoryDTO dto = new CarCategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }
}
