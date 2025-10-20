package com.showcar.showcar.dto;

import com.showcar.showcar.model.TestDriveRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDriveRequestDTO {
    private String id;
    private String customerId;
    private String customerName;
    private String carId;
    private String carName;
    private LocalDate preferredDate;
    private TestDriveRequest.TestDriveStatus status;
    private String note;
    private LocalDateTime createdAt;
}
