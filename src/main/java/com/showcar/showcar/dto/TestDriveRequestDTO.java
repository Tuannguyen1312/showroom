package com.showcar.showcar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDriveRequestDTO {
    private Integer testDriveRequestId;
    private Integer customerId;
    private String customerName;
    private Integer carId;
    private String carName;
    private LocalDate preferredDate;
    private String status; // "Pending", "Confirmed", "Completed", "Canceled"
    private String note;
    private LocalDateTime createdAt;
}
