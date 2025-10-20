package com.showcar.showcar.dto;

import com.showcar.showcar.model.Car;
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
    private String id;
    private String brandId;
    private String brandName;
    private String categoryId;
    private String categoryName;
    private String name;
    private Integer modelYear;
    private BigDecimal price;
    private String color;
    private Car.CarCondition condition;
    private Car.CarStatus status;
    private String specifications;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CarImageDTO> images;
    private String primaryImageUrl;
}
