package com.showcar.showcar.service;

import com.showcar.showcar.dto.CarDTO;
import com.showcar.showcar.dto.CarImageDTO;
import com.showcar.showcar.model.Brand;
import com.showcar.showcar.model.Car;
import com.showcar.showcar.model.CarCategory;
import com.showcar.showcar.model.CarImage;
import com.showcar.showcar.repository.BrandRepository;
import com.showcar.showcar.repository.CarCategoryRepository;
import com.showcar.showcar.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CarService {
    
    private final CarRepository carRepository;
    private final BrandRepository brandRepository;
    private final CarCategoryRepository carCategoryRepository;
    
    // Lấy tất cả xe
    public List<CarDTO> getAllCars() {
        return carRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Lấy xe theo ID
    public CarDTO getCarById(Integer id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
        return convertToDTO(car);
    }
    
    // Tạo xe mới
    public CarDTO createCar(CarDTO carDTO) {
        Brand brand = brandRepository.findById(carDTO.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        
        CarCategory category = carCategoryRepository.findById(carDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        
        Car car = convertToEntity(carDTO);
        car.setBrand(brand);
        car.setCategory(category);
        
        Car savedCar = carRepository.save(car);
        return convertToDTO(savedCar);
    }
    
    // Cập nhật xe
    public CarDTO updateCar(Integer id, CarDTO carDTO) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
        
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
        
        car.setName(carDTO.getName());
        car.setModelYear(carDTO.getModelYear());
        car.setPrice(carDTO.getPrice());
        car.setColor(carDTO.getColor());
        car.setConditions(Car.CarCondition.valueOf(carDTO.getConditions()));
        car.setStatus(Car.CarStatus.valueOf(carDTO.getStatus()));
        car.setSpecifications(carDTO.getSpecifications());
        car.setDescription(carDTO.getDescription());
        
        Car updatedCar = carRepository.save(car);
        return convertToDTO(updatedCar);
    }
    
    // Xóa xe
    public void deleteCar(Integer id) {
        if (!carRepository.existsById(id)) {
            throw new RuntimeException("Car not found with id: " + id);
        }
        carRepository.deleteById(id);
    }
    
    // Tìm xe theo brand
    public List<CarDTO> getCarsByBrand(Integer brandId) {
        return carRepository.findByBrand_BrandId(brandId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Tìm xe theo category
    public List<CarDTO> getCarsByCategory(Integer categoryId) {
        return carRepository.findByCategory_CategoryId(categoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Tìm xe theo status
    public List<CarDTO> getCarsByStatus(String status) {
        Car.CarStatus carStatus = Car.CarStatus.valueOf(status);
        return carRepository.findByStatus(carStatus).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Tìm xe available
    public List<CarDTO> getAvailableCars() {
        return carRepository.findAvailableCars().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Tìm xe theo khoảng giá
    public List<CarDTO> getCarsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return carRepository.findByPriceBetween(minPrice, maxPrice).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Tìm kiếm xe theo keyword
    public List<CarDTO> searchCars(String keyword) {
        return carRepository.searchByKeyword(keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Convert Entity to DTO
    private CarDTO convertToDTO(Car car) {
        CarDTO dto = new CarDTO();
        dto.setCarId(car.getCarId());
        dto.setBrandId(car.getBrand().getBrandId());
        dto.setBrandName(car.getBrand().getName());
        dto.setCategoryId(car.getCategory().getCategoryId());
        dto.setCategoryName(car.getCategory().getName());
        dto.setName(car.getName());
        dto.setModelYear(car.getModelYear());
        dto.setPrice(car.getPrice());
        dto.setColor(car.getColor());
        dto.setConditions(car.getConditions().name());
        dto.setStatus(car.getStatus().name());
        dto.setSpecifications(car.getSpecifications());
        dto.setDescription(car.getDescription());
        dto.setCreatedAt(car.getCreatedAt());
        dto.setUpdatedAt(car.getUpdatedAt());
        
        // Convert images
        if (car.getImages() != null) {
            List<CarImageDTO> imageDTOs = car.getImages().stream()
                    .map(this::convertImageToDTO)
                    .collect(Collectors.toList());
            dto.setImages(imageDTOs);
        }
        
        return dto;
    }
    
    // Convert DTO to Entity
    private Car convertToEntity(CarDTO dto) {
        Car car = new Car();
        car.setCarId(dto.getCarId());
        car.setName(dto.getName());
        car.setModelYear(dto.getModelYear());
        car.setPrice(dto.getPrice());
        car.setColor(dto.getColor());
        if (dto.getConditions() != null) {
            car.setConditions(Car.CarCondition.valueOf(dto.getConditions()));
        }
        if (dto.getStatus() != null) {
            car.setStatus(Car.CarStatus.valueOf(dto.getStatus()));
        }
        car.setSpecifications(dto.getSpecifications());
        car.setDescription(dto.getDescription());
        return car;
    }
    
    // Convert CarImage to DTO
    private CarImageDTO convertImageToDTO(CarImage image) {
        CarImageDTO dto = new CarImageDTO();
        dto.setCarImageId(image.getCarImageId());
        dto.setCarId(image.getCar().getCarId());
        dto.setImageUrl(image.getImageUrl());
        dto.setIsPrimary(image.getIsPrimary());
        return dto;
    }
}
