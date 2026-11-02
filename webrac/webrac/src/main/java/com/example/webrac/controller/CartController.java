package com.example.webrac.controller;

import com.example.webrac.model.CartItem;
import com.example.webrac.model.Material;
import com.example.webrac.model.Order;
import com.example.webrac.repository.MaterialRepository;
import com.example.webrac.repository.OrderRepository; // <--- MỚI
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; // <--- MỚI
import org.springframework.web.bind.annotation.RequestParam; // <--- MỚI

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    private final MaterialRepository materialRepository;
    private final OrderRepository orderRepository; // <--- MỚI

    // Cập nhật constructor
    public CartController(MaterialRepository materialRepository, OrderRepository orderRepository) {
        this.materialRepository = materialRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        double grandTotal = 0;
        for (CartItem item : cart) {
            grandTotal += item.getTotalPrice();
        }

        model.addAttribute("cart", cart);
        model.addAttribute("grandTotal", grandTotal);
        return "user/cart";
    }

    @GetMapping("/cart/add/{id}")
    public String addToCart(@PathVariable Long id, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        boolean found = false;
        for (CartItem item : cart) {
            if (item.getId().equals(id)) {
                item.setQuantity(item.getQuantity() + 1);
                found = true;
                break;
            }
        }

        if (!found) {
            Material material = materialRepository.findById(id).orElse(null);
            if (material != null) {
                cart.add(new CartItem(material.getId(), material.getName(), material.getPrice(), 1, material.getImage()));
            }
        }
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    @GetMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable Long id, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(item -> item.getId().equals(id));
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }

    @GetMapping("/cart/clear")
    public String clearCart(HttpSession session) {
        session.removeAttribute("cart");
        return "redirect:/cart";
    }

    // --- HÀM MỚI: XỬ LÝ ĐẶT HÀNG ---
    @PostMapping("/cart/checkout")
    public String checkout(@RequestParam String customerName,
                           @RequestParam String phone,
                           @RequestParam String address,
                           HttpSession session,
                           Model model) {

        // 1. Lấy giỏ hàng
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        // 2. Tính tổng tiền, Tạo đơn hàng VÀ TRỪ KHO
        double total = 0;
        StringBuilder itemsStr = new StringBuilder();

        for (CartItem item : cart) {
            total += item.getTotalPrice();
            itemsStr.append(item.getName()).append(" (").append(item.getQuantity()).append("), ");

            // --- ĐOẠN CODE MỚI: TRỪ KHO ---
            Material material = materialRepository.findById(item.getId()).orElse(null);
            if (material != null) {
                // Lấy số lượng cũ trừ đi số lượng khách mua
                double newQuantity = material.getQuantity() - item.getQuantity();

                // Nếu < 0 thì cho về 0 (tránh lỗi âm kho)
                if (newQuantity < 0) newQuantity = 0;

                material.setQuantity(newQuantity);
                materialRepository.save(material); // Lưu lại số lượng mới vào Database
            }
            // -----------------------------
        }

        // 3. Lưu đơn hàng
        Order newOrder = new Order(customerName, phone, address, total, itemsStr.toString());
        orderRepository.save(newOrder);

        // 4. Xóa sạch giỏ hàng
        session.removeAttribute("cart");

        return "redirect:/market?success=true";
    }
}