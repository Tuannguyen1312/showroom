package com.showcar.showcar.controller;

import com.showcar.showcar.dto.ContactRequestDTO;
import com.showcar.showcar.service.ContactRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API Controller cho Contact Request (Liên hệ/Hỗ trợ khách hàng)
 * Base URL: /api/contacts
 * 
 * Chức năng:
 * - Form liên hệ
 * - Yêu cầu tư vấn
 * - Quản lý liên hệ khách hàng (ADMIN)
 */
@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ContactRequestController {
    
    private final ContactRequestService contactRequestService;
    
    /**
     * Lấy tất cả yêu cầu liên hệ (ADMIN)
     * GET /api/contacts
     */
    @GetMapping
    public ResponseEntity<List<ContactRequestDTO>> getAllContactRequests() {
        List<ContactRequestDTO> requests = contactRequestService.getAllContactRequests();
        return ResponseEntity.ok(requests);
    }
    
    /**
     * Lấy chi tiết yêu cầu liên hệ theo ID (ADMIN)
     * GET /api/contacts/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContactRequestDTO> getContactRequestById(@PathVariable Integer id) {
        try {
            ContactRequestDTO request = contactRequestService.getContactRequestById(id);
            return ResponseEntity.ok(request);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Tìm yêu cầu liên hệ theo email (ADMIN)
     * GET /api/contacts/email/{email}
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<List<ContactRequestDTO>> findByEmail(@PathVariable String email) {
        List<ContactRequestDTO> requests = contactRequestService.findByEmail(email);
        return ResponseEntity.ok(requests);
    }
    
    /**
     * Gửi yêu cầu liên hệ/tư vấn
     * POST /api/contacts
     */
    @PostMapping
    public ResponseEntity<ContactRequestDTO> createContactRequest(@RequestBody ContactRequestDTO requestDTO) {
        try {
            ContactRequestDTO created = contactRequestService.createContactRequest(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Xóa yêu cầu liên hệ (ADMIN)
     * DELETE /api/contacts/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContactRequest(@PathVariable Integer id) {
        try {
            contactRequestService.deleteContactRequest(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
