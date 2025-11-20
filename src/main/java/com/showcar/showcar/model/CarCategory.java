package com.showcar.showcar.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;


@Entity
@Table(name = "car_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarCategory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;


    @Column(name = "name", nullable = false, length = 100)
    private String name;


    @Column(name = "description", columnDefinition = "TEXT")
    private String description;


    @OneToMany(mappedBy = "category")
    private List<Car> cars;
}