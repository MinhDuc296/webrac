package com.example.webrac.controller;

import com.example.webrac.model.Contact;
import com.example.webrac.model.Material;
import com.example.webrac.repository.ContactRepository;
import com.example.webrac.repository.MaterialRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class HomeController {

    private final MaterialRepository materialRepository;
    private final ContactRepository contactRepository;

    public HomeController(MaterialRepository materialRepository, ContactRepository contactRepository) {
        this.materialRepository = materialRepository;
        this.contactRepository = contactRepository;
    }

    @GetMapping({"/", "/home"})
    public String index(Model model) {
        List<Material> allMaterials = materialRepository.findAll();
        // Lấy tối đa 6 sản phẩm hiện ra trang chủ
        int limit = Math.min(allMaterials.size(), 6);
        model.addAttribute("materials", allMaterials.subList(0, limit));
        return "user/index";
    }

    @PostMapping("/contact/submit")
    public String submitContact(@RequestParam String fullName,
                                @RequestParam String phone,
                                @RequestParam(required = false) String email,
                                @RequestParam(required = false) String content,
                                @RequestParam(required = false) List<String> topics) { // Nhận danh sách checkbox

        Contact contact = new Contact();
        contact.setFullName(fullName);
        contact.setPhone(phone);
        contact.setEmail(email);
        contact.setMessage(content);
        contact.setCreatedAt(Instant.now());
        contact.setStatus(Contact.Status.NEW);

        if (topics != null && !topics.isEmpty()) {
            contact.setTopics(new HashSet<>(topics));
        } else {
            Set<String> defaultTopic = new HashSet<>();
            defaultTopic.add("Yêu cầu chung");
            contact.setTopics(defaultTopic);
        }

        contactRepository.save(contact);
        return "redirect:/?sent=true#contact";
    }
}