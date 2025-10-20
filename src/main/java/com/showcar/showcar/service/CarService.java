package com.showcar.showcar.service;

import com.showcar.showcar.dto.CarDTO;
import com.showcar.showcar.dto.CarImageDTO;
import com.showcar.showcar.model.Car;
import com.showcar.showcar.model.Brand;
import com.showcar.showcar.model.CarCategory;
import com.showcar.showcar.model.CarImage;
import com.showcar.showcar.repository.CarRepository;
import com.showcar.showcar.repository.BrandRepository;
import com.showcar.showcar.repository.CarCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {
    
    private final CarRepository carRepository;
    private final BrandRepository brandRepository;
    private final CarCategoryRepository carCategoryRepository;
    
    public List<CarDTO> getAllCars() {
        return carRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public CarDTO getCarById(String id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
        return convertToDTO(car);
    }
    
    public List<CarDTO> getCarsByBrand(String brandId) {
        return carRepository.findByBrandId(brandId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<CarDTO> getCarsByCategory(String categoryId) {
        return carRepository.findByCategoryId(categoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<CarDTO> getCarsByStatus(Car.CarStatus status) {
        return carRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<CarDTO> getCarsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return carRepository.findByPriceBetween(minPrice, maxPrice).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<CarDTO> searchCars(String keyword) {
        return carRepository.searchByKeyword(keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public CarDTO createCar(CarDTO carDTO) {
        Brand brand = brandRepository.findById(carDTO.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        
        CarCategory category = carCategoryRepository.findById(carDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        
        Car car = new Car();
        car.setBrand(brand);
        car.setCategory(category);
        car.setName(carDTO.getName());
        car.setModelYear(carDTO.getModelYear());
        car.setPrice(carDTO.getPrice());
        car.setColor(carDTO.getColor());
        car.setCondition(carDTO.getCondition());
        car.setStatus(carDTO.getStatus());
        car.setSpecifications(carDTO.getSpecifications());
        car.setDescription(carDTO.getDescription());
        
        Car savedCar = carRepository.save(car);
        return convertToDTO(savedCar);
    }
    
    @Transactional
    public CarDTO updateCar(String id, CarDTO carDTO) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));
        
        if (carDTO.getBrandId() != null) {
            Brand brand = brandRepository.findById(carDTO.getBrandId())
                    .orElseThrow(() -> new RuntimeException("Brand not found"));
            car.setBrand(brand);
        }
        
        if (carDTO.getCategoryId() != null) {
            CarCategory category = carCategoryRepository.findById(carDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            car.setCategory(category);
        }
        
        if (carDTO.getName() != null) car.setName(carDTO.getName());
        if (carDTO.getModelYear() != null) car.setModelYear(carDTO.getModelYear());
        if (carDTO.getPrice() != null) car.setPrice(carDTO.getPrice());
        if (carDTO.getColor() != null) car.setColor(carDTO.getColor());
        if (carDTO.getCondition() != null) car.setCondition(carDTO.getCondition());
        if (carDTO.getStatus() != null) car.setStatus(carDTO.getStatus());
        if (carDTO.getSpecifications() != null) car.setSpecifications(carDTO.getSpecifications());
        if (carDTO.getDescription() != null) car.setDescription(carDTO.getDescription());
        
        Car updatedCar = carRepository.save(car);
        return convertToDTO(updatedCar);
    }
    
    @Transactional
    public void deleteCar(String id) {
        if (!carRepository.existsById(id)) {
            throw new RuntimeException("Car not found");
        }
        carRepository.deleteById(id);
    }
    
    private CarDTO convertToDTO(Car car) {
        CarDTO dto = new CarDTO();
        dto.setId(car.getId());
        dto.setBrandId(car.getBrand().getId());
        dto.setBrandName(car.getBrand().getName());
        dto.setCategoryId(car.getCategory().getId());
        dto.setCategoryName(car.getCategory().getName());
        dto.setName(car.getName());
        dto.setModelYear(car.getModelYear());
        dto.setPrice(car.getPrice());
        dto.setColor(car.getColor());
        dto.setCondition(car.getCondition());
        dto.setStatus(car.getStatus());
        dto.setSpecifications(car.getSpecifications());
        dto.setDescription(car.getDescription());
        dto.setCreatedAt(car.getCreatedAt());
        dto.setUpdatedAt(car.getUpdatedAt());
        
        if (car.getImages() != null && !car.getImages().isEmpty()) {
            List<CarImageDTO> imageDTOs = car.getImages().stream()
                    .map(this::convertImageToDTO)
                    .collect(Collectors.toList());
            dto.setImages(imageDTOs);
            
            car.getImages().stream()
                    .filter(CarImage::getIsPrimary)
                    .findFirst()
                    .ifPresent(img -> dto.setPrimaryImageUrl(img.getImageUrl()));
        }
        
        return dto;
    }
    
    private CarImageDTO convertImageToDTO(CarImage image) {
        CarImageDTO dto = new CarImageDTO();
        dto.setId(image.getId());
        dto.setCarId(image.getCar().getId());
        dto.setImageUrl(image.getImageUrl());
        dto.setIsPrimary(image.getIsPrimary());
        return dto;
    }
}
