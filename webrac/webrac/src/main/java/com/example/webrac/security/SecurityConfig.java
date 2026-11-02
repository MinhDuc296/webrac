package com.example.webrac.security;

import com.example.webrac.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Đang tắt CSRF cho nhanh khi phát triển
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                .authorizeHttpRequests(auth -> auth
                        // 1. Các trang Public (Ai cũng vào được)
                        .requestMatchers("/", "/home", "/index", "/index.html",
                                "/assets/**", "/static/**", "/css/**", "/js/**", "/h2-console/**").permitAll()

                        // 2. Trang Login/Register
                        .requestMatchers("/login", "/register").permitAll()

                        // 3. [QUAN TRỌNG] Mở quyền cho trang Sàn Mua Bán & Ảnh
                        .requestMatchers("/market").permitAll()       // Khách xem hàng
                        .requestMatchers("/uploads/**").permitAll()   // Khách xem ảnh hàng

                        .requestMatchers("/cart/**").permitAll()  // <--- Cho phép khách xem và sửa giỏ hàng

                        // 4. API Public
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/contacts").permitAll()

                        // 5. Khu vực quản trị (Chỉ STAFF/ADMIN)
                        .requestMatchers("/api/**").hasRole("STAFF")
                        .requestMatchers("/admin/**").hasRole("STAFF")

                        // 6. Các yêu cầu khác
                        .anyRequest().permitAll()
                )

                // ---- FORM LOGIN ĐÃ NÂNG CẤP ----
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        // THAY ĐỔI Ở ĐÂY: Dùng successHandler thay vì defaultSuccessUrl
                        .successHandler((request, response, authentication) -> {
                            // Lấy danh sách quyền của user vừa đăng nhập
                            var roles = authentication.getAuthorities();

                            // Kiểm tra xem có quyền quản trị không (Admin hoặc Staff)
                            boolean isAdmin = roles.stream()
                                    .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")
                                            || r.getAuthority().equals("ADMIN")
                                            || r.getAuthority().equals("ROLE_STAFF")
                                            || r.getAuthority().equals("STAFF"));

                            // Điều hướng thông minh
                            if (isAdmin) {
                                response.sendRedirect("/admin/dashboard");
                            } else {
                                response.sendRedirect("/");
                            }
                        })
                        .failureUrl("/login?error")
                        .permitAll()
                )

                // ---- LOGOUT ----
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .permitAll()
                )

                .httpBasic(Customizer.withDefaults())
                .authenticationProvider(daoAuthenticationProvider());

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    // Phân cấp vai trò: ADMIN > STAFF > USER
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl rh = new RoleHierarchyImpl();
        rh.setHierarchy("""
            ROLE_ADMIN > ROLE_STAFF
            ROLE_STAFF > ROLE_USER
        """);
        return rh;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}