package com.showcar.showcar.controller;

import com.showcar.showcar.dto.CarImageDTO;
import com.showcar.showcar.service.CarImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/cars/{carId}/images")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CarImageController {

    private final CarImageService carImageService;

    /**
     * Lấy tất cả hình ảnh của xe
     * GET /api/cars/{carId}/images
     */
    @GetMapping
    public ResponseEntity<List<CarImageDTO>> getCarImages(@PathVariable Integer carId) {
        List<CarImageDTO> images = carImageService.getImagesByCarId(carId);
        return ResponseEntity.ok(images);
    }

    /**
     * Upload hình ảnh cho xe
     * POST /api/cars/{carId}/images
     */
    @PostMapping
    public ResponseEntity<CarImageDTO> uploadImage(
            @PathVariable Integer carId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "isPrimary", required = false, defaultValue = "false") Boolean isPrimary) {
        try {
            CarImageDTO image = carImageService.uploadImage(carId, file, isPrimary);
            return ResponseEntity.status(HttpStatus.CREATED).body(image);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Xóa hình ảnh
     * DELETE /api/cars/{carId}/images/{imageId}
     */
    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Integer carId, @PathVariable Integer imageId) {
        try {
            carImageService.deleteImage(imageId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Đặt hình ảnh làm hình chính
     * PUT /api/cars/{carId}/images/{imageId}/primary
     */
    @PutMapping("/{imageId}/primary")
    public ResponseEntity<CarImageDTO> setPrimaryImage(@PathVariable Integer carId, @PathVariable Integer imageId) {
        try {
            CarImageDTO image = carImageService.setPrimaryImage(imageId);
            return ResponseEntity.ok(image);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

