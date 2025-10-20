package com.showcar.showcar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteCarDTO {
    private String id;
    private String customerId;
    private String carId;
    private String carName;
    private String carImageUrl;
    private LocalDateTime addedAt;
}
