package com.example.webrac.controller;

import com.example.webrac.model.Contact;
import com.example.webrac.service.ContactService;
import com.example.webrac.service.MailService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ContactService contactService;
    private final MailService mailService;

    public ApiController(ContactService contactService, MailService mailService) {
        this.contactService = contactService;
        this.mailService = mailService;
    }

    /**
     * Public: nhận form liên hệ từ người dùng.
     * Không cần đăng nhập.
     */
    @PostMapping("/contacts")
    public ResponseEntity<?> createContact(@RequestBody Contact payload) {
        try {
            Contact saved = contactService.create(payload);

            // Gửi thông báo email: 1) tới admin, 2) xác nhận cho khách (nếu có email)
            try {
                mailService.sendNewContactNotifications(saved);
            } catch (Exception mailEx) {
                // Ghi log nếu cần, không chặn response thành công cho client
                // log.warn("Send mail failed", mailEx);
            }

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "id", saved.getId()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "error", "Đã xảy ra lỗi khi lưu liên hệ"
            ));
        }
    }

    /**
     * STAFF/ADMIN: xem danh sách toàn bộ liên hệ.
     * @PreAuthorize đảm bảo chỉ người có role thích hợp được truy cập.
     */
    @PreAuthorize("hasRole('STAFF')")
    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> listContacts() {
        List<Contact> contacts = contactService.findAll();
        return ResponseEntity.ok(contacts);
    }
}
