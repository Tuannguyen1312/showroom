package com.showcar.showcar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "test_drive_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDriveRequest {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", length = 36)
    private String id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
    
    @Column(name = "preferred_date")
    private LocalDate preferredDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TestDriveStatus status = TestDriveStatus.Pending;
    
    @Column(name = "note", columnDefinition = "TEXT")
    private String note;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    public enum TestDriveStatus {
        Pending, Confirmed, Completed, Canceled
    }
}
