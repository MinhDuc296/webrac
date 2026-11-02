package com.example.webrac.service;

import com.example.webrac.model.Testimonial;
import com.example.webrac.repository.TestimonialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestimonialService {

    private final TestimonialRepository repo;
    private final MailService mailService;

    public TestimonialService(TestimonialRepository repo, MailService mailService) {
        this.repo = repo;
        this.mailService = mailService;
    }

    public List<Testimonial> findAll() {
        return repo.findAll();
    }

    public List<Testimonial> findPublished() {
        return repo.findByPublishedTrueOrderByCreatedAtDesc();
    }

    /**
     * Lưu phản hồi.
     * - Nếu là BẢN GHI MỚI (id == null) và có email -> gửi email cảm ơn.
     */
    public Testimonial save(Testimonial t) {
        boolean isNew = (t.getId() == null);
        Testimonial saved = repo.save(t);

        if (isNew && hasEmail(saved)) {
            // Không để lỗi mail ảnh hưởng luồng chính
            try {
                mailService.sendTestimonialReply(saved);
            } catch (Exception ignore) {}
        }
        return saved;
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Testimonial togglePublish(Long id, boolean published) {
        Testimonial t = repo.findById(id).orElseThrow();
        t.setPublished(published);
        return repo.save(t);
    }

    /* ---------- Helpers ---------- */

    private boolean hasEmail(Testimonial t) {
        try {
            String email = t.getAuthorEmail();  // yêu cầu Testimonial có field authorEmail + getter
            return email != null && !email.isBlank();
        } catch (NoSuchMethodError | NullPointerException e) {
            // Nếu model chưa có authorEmail, hãy thêm:
            // private String authorEmail; + getter/setter tương ứng.
            return false;
        }
    }
}
