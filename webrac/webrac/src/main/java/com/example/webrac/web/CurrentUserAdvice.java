package com.example.webrac.web;

import com.example.webrac.model.User;
import com.example.webrac.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@Component
@ControllerAdvice
public class CurrentUserAdvice {

    private final UserRepository userRepo;

    public CurrentUserAdvice(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @ModelAttribute("currentUser")
    public User addCurrentUser(Authentication authentication) {
        if (authentication == null) return null;
        // Bạn login bằng email -> Authentication.getName() chính là email
        return userRepo.findByEmail(authentication.getName()).orElse(null);
    }
}
