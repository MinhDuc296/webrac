package com.example.webrac.repository;

import com.example.webrac.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    // Hiện tại chưa cần hàm tìm kiếm phức tạp, để trống là đủ dùng cơ bản
}