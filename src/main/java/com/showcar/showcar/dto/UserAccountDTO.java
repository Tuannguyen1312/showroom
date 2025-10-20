package com.showcar.showcar.dto;

import com.showcar.showcar.model.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDTO {
    private String id;
    private String customerId;
    private String username;
    private UserAccount.UserRole role;
    private LocalDateTime createdAt;
}
