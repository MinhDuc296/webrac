package com.example.webrac.controller;

import com.example.webrac.service.StatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StatsApi {

    private final StatsService statsService;

    public StatsApi(StatsService statsService) {
        this.statsService = statsService;
    }

    /**
     * STAFF/ADMIN: Lấy thống kê hệ thống.
     * Yêu cầu role STAFF trở lên (ADMIN kế thừa).
     */
    @PreAuthorize("hasRole('STAFF')")
    @GetMapping("/api/stats")
    public ResponseEntity<?> stats() {
        try {
            Map<String, Object> overview = statsService.overview();
            return ResponseEntity.ok(overview);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    Map.of("success", false, "error", "Không lấy được thống kê")
            );
        }
    }
}
