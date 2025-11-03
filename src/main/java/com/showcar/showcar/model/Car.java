package com.showcar.showcar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "car")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Integer carId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CarCategory category;
    
    @Column(name = "name", nullable = false, length = 150)
    private String name;
    
    @Column(name = "model_year")
    private Integer modelYear;
    
    @Column(name = "price", nullable = false, precision = 15, scale = 2)
    private BigDecimal price;
    
    @Column(name = "color", length = 50)
    private String color;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "conditions")
    private CarCondition conditions = CarCondition.New;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CarStatus status = CarStatus.Available;
    
    @Column(name = "specifications", columnDefinition = "TEXT")
    private String specifications;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarImage> images;
    
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<TestDriveRequest> testDriveRequests;
    
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<OrderDeposit> orderDeposits;
    
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<FavoriteCar> favoriteCars;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum CarCondition {
        New, Used
    }
    
    public enum CarStatus {
        Available, Sold, Discontinued
    }
}
