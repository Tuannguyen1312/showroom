package com.showcar.showcar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "brand")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Integer brandId;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "origin", length = 100)
    private String origin;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<Car> cars;
}
