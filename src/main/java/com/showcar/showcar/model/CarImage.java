package com.showcar.showcar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "car_image")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarImage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_image_id")
    private Integer carImageId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
    
    @Column(name = "image_url", nullable = false, length = 255)
    private String imageUrl;
    
    @Column(name = "is_primary")
    private Boolean isPrimary = false;
}
