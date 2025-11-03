package com.showcar.showcar.service;

import com.showcar.showcar.dto.CarImageDTO;
import com.showcar.showcar.model.Car;
import com.showcar.showcar.model.CarImage;
import com.showcar.showcar.repository.CarImageRepository;
import com.showcar.showcar.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CarImageService {

    private final CarImageRepository carImageRepository;
    private final CarRepository carRepository;
    private final FileStorageService fileStorageService;

    public List<CarImageDTO> getImagesByCarId(Integer carId) {
        return carImageRepository.findByCar_CarId(carId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CarImageDTO uploadImage(Integer carId, MultipartFile file, Boolean isPrimary) throws Exception {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        // Store file
        String imageUrl = fileStorageService.storeFile(file);

        // If this is primary, unset other primary images
        if (Boolean.TRUE.equals(isPrimary)) {
            carImageRepository.findByCar_CarId(carId).forEach(img -> {
                if (img.getIsPrimary()) {
                    img.setIsPrimary(false);
                    carImageRepository.save(img);
                }
            });
        }

        // Create CarImage entity
        CarImage carImage = new CarImage();
        carImage.setCar(car);
        carImage.setImageUrl(imageUrl);
        carImage.setIsPrimary(isPrimary != null ? isPrimary : false);

        CarImage savedImage = carImageRepository.save(carImage);
        return convertToDTO(savedImage);
    }

    public void deleteImage(Integer imageId) {
        CarImage carImage = carImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        // Delete file from storage
        fileStorageService.deleteFile(carImage.getImageUrl());

        // Delete from database
        carImageRepository.delete(carImage);
    }

    public CarImageDTO setPrimaryImage(Integer imageId) {
        CarImage carImage = carImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        // Unset other primary images for this car
        carImageRepository.findByCar_CarId(carImage.getCar().getCarId()).forEach(img -> {
            if (!img.getCarImageId().equals(imageId) && img.getIsPrimary()) {
                img.setIsPrimary(false);
                carImageRepository.save(img);
            }
        });

        // Set this as primary
        carImage.setIsPrimary(true);
        CarImage saved = carImageRepository.save(carImage);
        return convertToDTO(saved);
    }

    private CarImageDTO convertToDTO(CarImage carImage) {
        CarImageDTO dto = new CarImageDTO();
        dto.setCarImageId(carImage.getCarImageId());
        dto.setCarId(carImage.getCar().getCarId());
        dto.setImageUrl(carImage.getImageUrl());
        dto.setIsPrimary(carImage.getIsPrimary());
        return dto;
    }
}

