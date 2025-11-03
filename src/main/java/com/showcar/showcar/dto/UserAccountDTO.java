package com.showcar.showcar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDTO {
    private Integer userAccountId;
    private Integer customerId;
    private String username;
    private String role; // "customer", "admin", "sales", "marketing"
    // Note: Không bao gồm passwordHash vì lý do bảo mật
}
