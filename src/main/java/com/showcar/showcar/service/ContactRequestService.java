package com.showcar.showcar.service;

import com.showcar.showcar.dto.ContactRequestDTO;
import com.showcar.showcar.model.ContactRequest;
import com.showcar.showcar.repository.ContactRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ContactRequestService {
    
    private final ContactRequestRepository contactRequestRepository;
    
    // Lấy tất cả contact requests
    public List<ContactRequestDTO> getAllContactRequests() {
        return contactRequestRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Lấy contact request theo ID
    public ContactRequestDTO getContactRequestById(Integer id) {
        ContactRequest request = contactRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact request not found with id: " + id));
        return convertToDTO(request);
    }
    
    // Tạo contact request mới
    public ContactRequestDTO createContactRequest(ContactRequestDTO requestDTO) {
        ContactRequest request = convertToEntity(requestDTO);
        ContactRequest savedRequest = contactRequestRepository.save(request);
        return convertToDTO(savedRequest);
    }
    
    // Xóa contact request
    public void deleteContactRequest(Integer id) {
        if (!contactRequestRepository.existsById(id)) {
            throw new RuntimeException("Contact request not found with id: " + id);
        }
        contactRequestRepository.deleteById(id);
    }
    
    // Tìm contact requests theo email
    public List<ContactRequestDTO> findByEmail(String email) {
        return contactRequestRepository.findByEmail(email).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Convert Entity to DTO
    private ContactRequestDTO convertToDTO(ContactRequest request) {
        ContactRequestDTO dto = new ContactRequestDTO();
        dto.setContactRequestId(request.getContactRequestId());
        dto.setCustomerName(request.getCustomerName());
        dto.setEmail(request.getEmail());
        dto.setPhone(request.getPhone());
        dto.setMessage(request.getMessage());
        dto.setCreatedAt(request.getCreatedAt());
        return dto;
    }
    
    // Convert DTO to Entity
    private ContactRequest convertToEntity(ContactRequestDTO dto) {
        ContactRequest request = new ContactRequest();
        request.setContactRequestId(dto.getContactRequestId());
        request.setCustomerName(dto.getCustomerName());
        request.setEmail(dto.getEmail());
        request.setPhone(dto.getPhone());
        request.setMessage(dto.getMessage());
        return request;
    }
}
