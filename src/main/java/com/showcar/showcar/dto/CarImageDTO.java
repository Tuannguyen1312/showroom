package com.showcar.showcar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarImageDTO {
    private Integer carImageId;
    private Integer carId;
    private String imageUrl;
    private Boolean isPrimary;
}
