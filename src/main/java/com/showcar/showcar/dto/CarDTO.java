package com.showcar.showcar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    private Integer carId;
    private Integer brandId;
    private String brandName;
    private Integer categoryId;
    private String categoryName;
    private String name;
    private Integer modelYear;
    private BigDecimal price;
    private String color;
    private String conditions; // "New" or "Used"
    private String status; // "Available", "Sold", "Discontinued"
    private String specifications;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CarImageDTO> images;
}
