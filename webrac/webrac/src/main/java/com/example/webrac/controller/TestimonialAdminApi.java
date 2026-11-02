package com.example.webrac.controller;

import com.example.webrac.model.Testimonial;
import com.example.webrac.service.TestimonialService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/testimonials")
@PreAuthorize("hasRole('STAFF')")
public class TestimonialAdminApi {

    private final TestimonialService service;

    public TestimonialAdminApi(TestimonialService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Testimonial t) {
        Testimonial saved = service.save(t);
        return ResponseEntity.ok(Map.of("success", true, "id", saved.getId()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Testimonial payload) {
        Testimonial t = service.save(merge(id, payload));
        return ResponseEntity.ok(Map.of("success", true, "id", t.getId()));
    }

    @PatchMapping("/{id}/publish")
    public ResponseEntity<?> publish(@PathVariable Long id, @RequestParam boolean value) {
        Testimonial t = service.togglePublish(id, value);
        return ResponseEntity.ok(Map.of("success", true, "published", t.isPublished()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(Map.of("success", true));
    }

    private Testimonial merge(Long id, Testimonial incoming) {
        Testimonial t = service.findAll().stream().filter(x -> x.getId().equals(id)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not found"));
        if (incoming.getAuthorName() != null) t.setAuthorName(incoming.getAuthorName());
        if (incoming.getRating() != null) t.setRating(incoming.getRating());
        if (incoming.getContent() != null) t.setContent(incoming.getContent());
        return t;
    }
}
