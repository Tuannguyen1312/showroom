package com.showcar.showcar.dto;

import com.showcar.showcar.model.OrderDeposit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDepositDTO {
    private String id;
    private String customerId;
    private String customerName;
    private String carId;
    private String carName;
    private String employeeId;
    private String employeeName;
    private LocalDateTime orderDate;
    private BigDecimal depositAmount;
    private BigDecimal totalPrice;
    private OrderDeposit.OrderStatus status;
}
