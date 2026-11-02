package com.example.webrac.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "contacts")
public class Contact {

    public enum Status {
        NEW, CONTACTED, CLOSED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String phone;
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "contact_topics", joinColumns = @JoinColumn(name = "contact_id"))
    @Column(name = "topic")
    private Set<String> topics = new HashSet<>();

    @Lob
    private String message;
    private String internalNote; // Ghi chú của Admin

    private Instant createdAt = Instant.now();

    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;

    // --- Constructor ---
    public Contact() {}

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Set<String> getTopics() { return topics; }
    public void setTopics(Set<String> topics) { this.topics = topics; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getInternalNote() { return internalNote; }
    public void setInternalNote(String internalNote) { this.internalNote = internalNote; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}