package com.showcar.showcar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarImageDTO {
    private String id;
    private String carId;
    private String imageUrl;
    private Boolean isPrimary;
}
