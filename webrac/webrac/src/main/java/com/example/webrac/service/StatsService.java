package com.example.webrac.service;

import com.example.webrac.model.Contact;
import com.example.webrac.repository.ContactRepository;
import com.example.webrac.repository.ContributionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatsService {

    private final ContactRepository contactRepo;
    private final ContributionRepository contributionRepo;

    public StatsService(ContactRepository contactRepo, ContributionRepository contributionRepo) {
        this.contactRepo = contactRepo;
        this.contributionRepo = contributionRepo;
    }

    public Map<String, Object> overview() {
        Map<String, Object> result = new HashMap<>();
        long totalContacts = contactRepo  .countAll();
        long newContacts = contactRepo.countByStatus(Contact.Status.NEW);
        long contacted = contactRepo.countByStatus(Contact.Status.CONTACTED);
        long closed = contactRepo.countByStatus(Contact.Status.CLOSED);

        // breakdown theo topic
        Map<String, Long> byTopic = new HashMap<>();
        List<Contact> all = contactRepo.findAll();
        for (Contact c : all) {
            for (String t : c.getTopics()) {
                byTopic.merge(t, 1L, Long::sum);
            }
        }

        BigDecimal totalContribAmount = contributionRepo.sumAmount();
        long contribCount = contributionRepo.count();

        result.put("contactsTotal", totalContacts);
        result.put("contactsByStatus", Map.of(
                "NEW", newContacts,
                "CONTACTED", contacted,
                "CLOSED", closed
        ));
        result.put("contactsByTopic", byTopic);
        result.put("contributionsCount", contribCount);
        result.put("contributionsTotalAmount", totalContribAmount);
        return result;
    }
}
