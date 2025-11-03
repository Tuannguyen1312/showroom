package com.showcar.showcar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {
    private Integer brandId;
    private String name;
    private String origin;
    private String description;
}
