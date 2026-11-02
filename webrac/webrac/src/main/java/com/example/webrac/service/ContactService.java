package com.example.webrac.service;

import com.example.webrac.model.Contact;
import com.example.webrac.repository.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepo;

    public ContactService(ContactRepository contactRepo) {
        this.contactRepo = contactRepo;
    }
    public Contact updateStatus(Long id, Contact.Status status) {
        Contact c = contactRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Contact not found"));
        c.setStatus(status);
        return contactRepo.save(c);
    }

    public Contact updateNote(Long id, String note) {
        Contact c = contactRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Contact not found"));
        c.setInternalNote(note);
        return contactRepo.save(c);
    }

    public Contact create(Contact contact) {
        // validate tối thiểu
        if (!StringUtils.hasText(contact.getFullName())) {
            throw new IllegalArgumentException("Full name is required");
        }
        if (!StringUtils.hasText(contact.getPhone())) {
            throw new IllegalArgumentException("Phone is required");
        }
        return contactRepo.save(contact);
    }

    public List<Contact> findAll() {
        return contactRepo.findAll();
    }
}
