package com.example.webrac.controller;

import com.example.webrac.model.Contact;
import com.example.webrac.model.Material;
import com.example.webrac.repository.ContactRepository;
import com.example.webrac.repository.MaterialRepository;
import com.example.webrac.repository.OrderRepository;
import com.example.webrac.service.StatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('STAFF')")
public class AdminController {

    private final ContactRepository contactRepository;
    private final MaterialRepository materialRepository;
    private final OrderRepository orderRepository;
    private final StatsService statsService;

    public AdminController(ContactRepository contactRepository,
                           MaterialRepository materialRepository,
                           OrderRepository orderRepository,
                           StatsService statsService) {
        this.contactRepository = contactRepository;
        this.materialRepository = materialRepository;
        this.orderRepository = orderRepository;
        this.statsService = statsService;
    }

    @GetMapping({"", "/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("stats", statsService.overview());
        model.addAttribute("contacts", contactRepository.findAllByOrderByCreatedAtDesc());
        model.addAttribute("ordersCount", orderRepository.count());
        model.addAttribute("materialsCount", materialRepository.count());
        return "admin/dashboard";
    }

    @GetMapping("/contacts")
    public String contacts(Model model) {
        model.addAttribute("contacts", contactRepository.findAllByOrderByCreatedAtDesc());
        return "admin/contacts";
    }

    // Quản lý kho vật liệu
    @GetMapping("/materials")
    public String materials(Model model) {
        model.addAttribute("materials", materialRepository.findAll());
        model.addAttribute("newMaterial", new Material());
        return "admin/materials";
    }

    @PostMapping("/materials/save")
    public String saveMaterial(@ModelAttribute Material material) {
        materialRepository.save(material);
        return "redirect:/admin/materials";
    }

    @GetMapping("/materials/edit/{id}")
    public String editMaterial(@PathVariable Long id, Model model) {
        Material m = materialRepository.findById(id).orElse(null);
        if (m == null) return "redirect:/admin/materials";
        model.addAttribute("material", m);
        return "admin/material_edit";
    }

    @GetMapping("/materials/delete/{id}")
    public String deleteMaterial(@PathVariable Long id) {
        materialRepository.deleteById(id);
        return "redirect:/admin/materials";
    }

    // Quản lý đơn hàng
    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("orders", orderRepository.findAllByOrderByOrderDateDesc());
        return "admin/orders";
    }

    @PostMapping("/orders/{id}/status")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        orderRepository.findById(id).ifPresent(o -> {
            o.setStatus(status);
            orderRepository.save(o);
        });
        return "redirect:/admin/orders";
    }

    // API phục vụ cho việc đổi trạng thái ngay tại trang quản lý
    @PatchMapping("/api/contacts/{id}/status")
    @ResponseBody
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Contact c = contactRepository.findById(id).orElse(null);
        if (c == null) return ResponseEntity.notFound().build();
        c.setStatus(Contact.Status.valueOf(body.get("status")));
        contactRepository.save(c);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/testimonials")
    public String testimonials(Model model) {
        return "admin/testimonials";
    }
}