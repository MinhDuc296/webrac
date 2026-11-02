package com.example.webrac.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String phone;
    private String address;
    private Double totalAmount;

    @Column(length = 2000) // Cho phép lưu nội dung dài
    private String itemsList; // Lưu danh sách hàng: "Sắt (2kg), Nhựa (1kg)..."

    private LocalDateTime orderDate;
    private String status; // Ví dụ: "Mới đặt", "Đã giao"

    public Order() {
        this.orderDate = LocalDateTime.now();
        this.status = "Mới đặt";
    }

    // Constructor nhanh
    public Order(String customerName, String phone, String address, Double totalAmount, String itemsList) {
        this.customerName = customerName;
        this.phone = phone;
        this.address = address;
        this.totalAmount = totalAmount;
        this.itemsList = itemsList;
        this.orderDate = LocalDateTime.now();
        this.status = "Mới đặt";
    }

    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    public String getItemsList() { return itemsList; }
    public void setItemsList(String itemsList) { this.itemsList = itemsList; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}