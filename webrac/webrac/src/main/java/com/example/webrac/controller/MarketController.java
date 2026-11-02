package com.example.webrac.controller;

import com.example.webrac.model.Material;
import com.example.webrac.repository.MaterialRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
// Xóa import ResponseBody đi

@Controller
public class MarketController {

    private final MaterialRepository materialRepository;

    public MarketController(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @GetMapping("/market")
    public String showMarket(Model model) {
        // Lấy tất cả
        List<Material> allMaterials = materialRepository.findAll();

        // Chỉ giữ lại những món có số lượng > 0
        List<Material> availableMaterials = new ArrayList<>();
        for (Material m : allMaterials) {
            if (m.getQuantity() > 0) {
                availableMaterials.add(m);
            }
        }

        model.addAttribute("materials", availableMaterials);
        return "user/market";
    }
}