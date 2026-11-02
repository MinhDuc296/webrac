package com.example.webrac.model;

public class CartItem {
    private Long id;        // ID của vật liệu
    private String name;    // Tên
    private Double price;   // Giá
    private int quantity;   // Số lượng khách mua
    private String image;   // Ảnh

    public CartItem() {
    }

    public CartItem(Long id, String name, Double price, int quantity, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }

    // --- Tính tổng tiền của món này (Giá x Số lượng) ---
    public Double getTotalPrice() {
        return price * quantity;
    }

    // --- Getter và Setter ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}