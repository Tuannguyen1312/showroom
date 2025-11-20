package com.showcar.showcar.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "order_deposit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDeposit {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_deposit_id")
    private Integer orderDepositId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;


    @Column(name = "order_date")
    private LocalDateTime orderDate;


    @Column(name = "deposit_amount", precision = 15, scale = 2)
    private BigDecimal depositAmount;


    @Column(name = "total_price", precision = 15, scale = 2)
    private BigDecimal totalPrice;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status = OrderStatus.Pending;


    @PrePersist
    protected void onCreate() {
        if (orderDate == null) {
            orderDate = LocalDateTime.now();
        }
    }


    public enum OrderStatus {
        Pending, Deposited, Delivered, Canceled
    }
}

