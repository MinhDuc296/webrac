package com.example.webrac.repository;

import com.example.webrac.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    // Sửa lỗi "cannot find symbol" trong StatsService
    long countByStatus(Contact.Status status);

    default long countAll() {
        return count();
    }

    // Lấy danh sách liên hệ mới nhất hiện lên bảng Admin
    List<Contact> findAllByOrderByCreatedAtDesc();
}