package com.example.webrac.model;

import jakarta.persistence.*;

@Entity
@Table(name = "materials")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // Tên vật liệu (Ví dụ: Sắt vụn)
    private Double price;       // Giá bán
    private String unit;        // Đơn vị tính (kg, tấn, bao...)
    private Double quantity;    // Số lượng trong kho
    private String image;       // Đường dẫn ảnh (nếu có)

    @Column(length = 1000)
    private String description; // Mô tả chi tiết

    // --- Constructor mặc định (Bắt buộc cho JPA) ---
    public Material() {
    }

    // --- Constructor đầy đủ ---
    public Material(String name, Double price, String unit, Double quantity, String image, String description) {
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.quantity = quantity;
        this.image = image;
        this.description = description;
    }

    // --- Getter và Setter (Vì không dùng Lombok) ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}