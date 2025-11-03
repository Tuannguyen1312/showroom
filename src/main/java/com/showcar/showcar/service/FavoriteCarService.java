package com.showcar.showcar.service;

import com.showcar.showcar.dto.FavoriteCarDTO;
import com.showcar.showcar.model.Car;
import com.showcar.showcar.model.Customer;
import com.showcar.showcar.model.FavoriteCar;
import com.showcar.showcar.repository.CarRepository;
import com.showcar.showcar.repository.CustomerRepository;
import com.showcar.showcar.repository.FavoriteCarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteCarService {
    
    private final FavoriteCarRepository favoriteCarRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;
    
    // Lấy tất cả favorite cars của customer
    public List<FavoriteCarDTO> getFavoriteCarsByCustomer(Integer customerId) {
        return favoriteCarRepository.findByCustomer_CustomerId(customerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Thêm xe vào favorite
    public FavoriteCarDTO addFavoriteCar(Integer customerId, Integer carId) {
        // Kiểm tra đã tồn tại chưa
        if (favoriteCarRepository.existsByCustomer_CustomerIdAndCar_CarId(customerId, carId)) {
            throw new RuntimeException("Car is already in favorites");
        }
        
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));
        
        FavoriteCar favoriteCar = new FavoriteCar();
        favoriteCar.setCustomer(customer);
        favoriteCar.setCar(car);
        
        FavoriteCar savedFavorite = favoriteCarRepository.save(favoriteCar);
        return convertToDTO(savedFavorite);
    }
    
    // Xóa xe khỏi favorite
    public void removeFavoriteCar(Integer customerId, Integer carId) {
        if (!favoriteCarRepository.existsByCustomer_CustomerIdAndCar_CarId(customerId, carId)) {
            throw new RuntimeException("Favorite car not found");
        }
        favoriteCarRepository.deleteByCustomer_CustomerIdAndCar_CarId(customerId, carId);
    }
    
    // Kiểm tra xe có trong favorite không
    public boolean isFavorite(Integer customerId, Integer carId) {
        return favoriteCarRepository.existsByCustomer_CustomerIdAndCar_CarId(customerId, carId);
    }
    
    // Convert Entity to DTO
    private FavoriteCarDTO convertToDTO(FavoriteCar favoriteCar) {
        FavoriteCarDTO dto = new FavoriteCarDTO();
        dto.setFavoriteCarId(favoriteCar.getFavoriteCarId());
        dto.setCustomerId(favoriteCar.getCustomer().getCustomerId());
        dto.setCarId(favoriteCar.getCar().getCarId());
        dto.setCarName(favoriteCar.getCar().getName());
        dto.setCarBrand(favoriteCar.getCar().getBrand().getName());
        dto.setAddedAt(favoriteCar.getAddedAt());
        return dto;
    }
}
