package com.showcar.showcar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDepositDTO {
    private Integer orderDepositId;
    private Integer customerId;
    private String customerName;
    private Integer carId;
    private String carName;
    private Integer employeeId;
    private String employeeName;
    private LocalDateTime orderDate;
    private BigDecimal depositAmount;
    private BigDecimal totalPrice;
    private String status; // "Pending", "Deposited", "Delivered", "Canceled"
}
