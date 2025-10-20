package com.showcar.showcar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequestDTO {
    private String id;
    private String customerName;
    private String email;
    private String phone;
    private String message;
    private LocalDateTime createdAt;
}
