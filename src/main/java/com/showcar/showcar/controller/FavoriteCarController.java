package com.showcar.showcar.controller;

import com.showcar.showcar.dto.FavoriteCarDTO;
import com.showcar.showcar.service.FavoriteCarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST API Controller cho Favorite Car (Xe yêu thích)
 * Base URL: /api/favorites
 * 
 * Chức năng:
 * - Lưu xe yêu thích
 * - Quản lý danh sách xe yêu thích
 */
@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FavoriteCarController {
    
    private final FavoriteCarService favoriteCarService;
    
    /**
     * Lấy danh sách xe yêu thích của khách hàng
     * GET /api/favorites/customer/{customerId}
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<FavoriteCarDTO>> getFavoriteCarsByCustomer(@PathVariable Integer customerId) {
        List<FavoriteCarDTO> favorites = favoriteCarService.getFavoriteCarsByCustomer(customerId);
        return ResponseEntity.ok(favorites);
    }
    
    /**
     * Kiểm tra xe có trong danh sách yêu thích không
     * GET /api/favorites/check?customerId={customerId}&carId={carId}
     */
    @GetMapping("/check")
    public ResponseEntity<Map<String, Boolean>> checkFavorite(
            @RequestParam Integer customerId,
            @RequestParam Integer carId) {
        boolean isFavorite = favoriteCarService.isFavorite(customerId, carId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isFavorite", isFavorite);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Thêm xe vào danh sách yêu thích
     * POST /api/favorites?customerId={customerId}&carId={carId}
     */
    @PostMapping
    public ResponseEntity<FavoriteCarDTO> addFavoriteCar(
            @RequestParam Integer customerId,
            @RequestParam Integer carId) {
        try {
            FavoriteCarDTO favorite = favoriteCarService.addFavoriteCar(customerId, carId);
            return ResponseEntity.status(HttpStatus.CREATED).body(favorite);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Xóa xe khỏi danh sách yêu thích
     * DELETE /api/favorites?customerId={customerId}&carId={carId}
     */
    @DeleteMapping
    public ResponseEntity<Void> removeFavoriteCar(
            @RequestParam Integer customerId,
            @RequestParam Integer carId) {
        try {
            favoriteCarService.removeFavoriteCar(customerId, carId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
