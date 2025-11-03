package com.showcar.showcar.controller;

import com.showcar.showcar.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/email")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmailController {

    private final EmailService emailService;

    /**
     * Gửi email marketing
     * POST /api/admin/email/marketing
     * Required: ADMIN, MARKETING roles
     */
    @PostMapping("/marketing")
    @PreAuthorize("hasAnyRole('ADMIN', 'MARKETING')")
    public ResponseEntity<Void> sendMarketingEmail(
            @RequestParam String customerEmail,
            @RequestParam String customerName,
            @RequestParam String carName,
            @RequestParam String carPrice,
            @RequestParam String promotion) {
        try {
            emailService.sendMarketingEmail(customerEmail, customerName, carName, carPrice, promotion);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

