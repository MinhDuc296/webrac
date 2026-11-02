package com.example.webrac.service;

import com.example.webrac.model.Contact;
import com.example.webrac.model.Testimonial;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.adminTo:}")
    private String adminTo;

    @Value("${spring.mail.username:}")
    private String from;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /* ---------- Email khi có liên hệ mới ---------- */
    public void sendNewContactNotifications(Contact c) {
        // 1) Mail tới admin
        if (adminTo != null && !adminTo.isBlank()) {
            SimpleMailMessage m = new SimpleMailMessage();
            m.setTo(adminTo);
            if (from != null && !from.isBlank()) m.setFrom(from);
            m.setSubject("[WasteHeroes] Liên hệ mới từ " + safe(c.getFullName()));
            m.setText("""
                    Có liên hệ mới:
                    - Họ tên: %s
                    - Phone: %s
                    - Email: %s
                    - Quan tâm: %s
                    - Nội dung: %s
                    """.formatted(safe(c.getFullName()), safe(c.getPhone()), safe(c.getEmail()),
                    String.join(", ", c.getTopics()), safe(c.getMessage())));
            mailSender.send(m);
        }

        // 2) Mail xác nhận cho khách (nếu có email)
        if (c.getEmail() != null && !c.getEmail().isBlank()) {
            SimpleMailMessage m = new SimpleMailMessage();
            m.setTo(c.getEmail());
            if (from != null && !from.isBlank()) m.setFrom(from);
            m.setSubject("WasteHeroes đã nhận yêu cầu của bạn");
            m.setText("""
                    Chào %s,
                    Chúng tôi đã nhận được yêu cầu và sẽ liên hệ sớm.
                    Trân trọng,
                    WasteHeroes
                    """.formatted(safe(c.getFullName())));
            mailSender.send(m);
        }
    }

    /* ---------- Email cảm ơn khi thêm Testimonial ---------- */
    public void sendTestimonialReply(Testimonial t) {
        if (t == null) return;
        String to = safe(t.getAuthorEmail());
        if (to.isBlank()) return; // không có email thì bỏ qua

        SimpleMailMessage m = new SimpleMailMessage();
        m.setTo(to);
        if (from != null && !from.isBlank()) m.setFrom(from);
        m.setSubject("Cảm ơn bạn đã phản hồi cho WasteHeroes");
        m.setText("""
                Xin chào %s,

                Cảm ơn bạn đã gửi phản hồi cho chúng tôi.

                Phản hồi của bạn:
                "%s"

                Chúng tôi rất trân trọng ý kiến đóng góp để cải thiện dịch vụ tốt hơn.

                Trân trọng,
                Đội ngũ WasteHeroes
                """.formatted(safe(t.getAuthorName()), safe(t.getContent())));
        mailSender.send(m);
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }
}
