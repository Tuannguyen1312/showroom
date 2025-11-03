package com.showcar.showcar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteCarDTO {
    private Integer favoriteCarId;
    private Integer customerId;
    private Integer carId;
    private String carName;
    private String carBrand;
    private LocalDateTime addedAt;
}
