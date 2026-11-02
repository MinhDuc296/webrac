package com.example.webrac.service;

import com.example.webrac.dto.RegisterRequest;
import com.example.webrac.model.Role;
import com.example.webrac.model.User;
import com.example.webrac.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(RegisterRequest req) {
        if (userRepo.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }
        if (req.getPassword() == null || req.getPassword().length() < 6) {
            throw new IllegalArgumentException("Mật khẩu tối thiểu 6 ký tự");
        }

        User u = new User();
        u.setFullName(req.getFullName());
        u.setEmail(req.getEmail().toLowerCase().trim());
        u.setPhone(req.getPhone());
        u.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        u.setEnabled(true);
        u.setRoles(EnumSet.of(Role.USER)); // mặc định ROLE_USER

        userRepo.save(u);
    }
}
