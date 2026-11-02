package com.example.webrac.config;

import com.example.webrac.model.Contact;
import com.example.webrac.model.Material;
import com.example.webrac.model.Order;
import com.example.webrac.model.Role;
import com.example.webrac.model.User;
import com.example.webrac.repository.ContactRepository;
import com.example.webrac.repository.MaterialRepository;
import com.example.webrac.repository.OrderRepository;
import com.example.webrac.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final MaterialRepository materialRepository;
    private final ContactRepository contactRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           MaterialRepository materialRepository,
                           ContactRepository contactRepository,
                           OrderRepository orderRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.materialRepository = materialRepository;
        this.contactRepository = contactRepository;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // 1. Khởi tạo tài khoản quản trị Admin
        if (!userRepository.existsByEmail("admin@wasteheroes.vn")) {
            User admin = new User();
            admin.setFullName("Nguyễn Văn Quản Trị");
            admin.setEmail("admin@wasteheroes.vn");
            admin.setPhone("0987654321");
            admin.setPasswordHash(passwordEncoder.encode("admin123"));
            admin.setEnabled(true);
            admin.setRoles(EnumSet.of(Role.ADMIN, Role.STAFF, Role.USER));
            userRepository.save(admin);
        }

        // 2. Khởi tạo tài khoản Người dùng mẫu
        if (!userRepository.existsByEmail("user@wasteheroes.vn")) {
            User user = new User();
            user.setFullName("Trần Thị Khách Hàng");
            user.setEmail("user@wasteheroes.vn");
            user.setPhone("0912345678");
            user.setPasswordHash(passwordEncoder.encode("user123"));
            user.setEnabled(true);
            user.setRoles(EnumSet.of(Role.USER));
            userRepository.save(user);
        }

        // 3. Khởi tạo & Cập nhật ảnh chất lượng cao cho danh mục Vật Liệu Tái Chế
        List<Material> existing = materialRepository.findAll();
        if (existing.isEmpty() || existing.stream().anyMatch(m -> m.getImage() == null || m.getImage().contains("/assets/images/"))) {
            materialRepository.deleteAll();

            materialRepository.save(new Material(
                    "Chai nhựa PET tái chế",
                    12000.0,
                    "kg",
                    850.0,
                    "https://images.unsplash.com/photo-1530587191325-3db32d826c18?auto=format&fit=crop&w=700&q=80",
                    "Nhựa PET trong suốt, đã rửa sạch, đóng kiện tiêu chuẩn xuất khẩu, độ tinh khiết >98%."
            ));

            materialRepository.save(new Material(
                    "Thùng Carton phế liệu loại 1",
                    4500.0,
                    "kg",
                    2400.0,
                    "https://images.unsplash.com/photo-1589939705384-5185137a7f0f?auto=format&fit=crop&w=700&q=80",
                    "Bìa carton 3-5 lớp khô ráo, ép khối kiện vuông 300kg/kiện, thích hợp cho nhà máy bao bì."
            ));

            materialRepository.save(new Material(
                    "Vỏ lon nhôm sạch ép kiện",
                    38000.0,
                    "kg",
                    650.0,
                    "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?auto=format&fit=crop&w=700&q=80",
                    "Vỏ lon bia, nước giải khát đã rửa và nén khối nguyên chất, hàm lượng nhôm >99%."
            ));

            materialRepository.save(new Material(
                    "Đồng phế liệu cáp điện loại 1",
                    215000.0,
                    "kg",
                    320.0,
                    "https://images.unsplash.com/photo-1605559424843-9e4c228bf1c2?auto=format&fit=crop&w=700&q=80",
                    "Đồng đỏ dây điện bóc vỏ không tạp chất, sợi sáng bóng, dẫn điện dẫn nhiệt tối đa."
            ));

            materialRepository.save(new Material(
                    "Thủy tinh vụn phân loại theo màu",
                    3500.0,
                    "kg",
                    1800.0,
                    "https://images.unsplash.com/photo-1516762689617-e1cffcef479d?auto=format&fit=crop&w=700&q=80",
                    "Chai lọ thủy tinh thu gom từ nhà hàng, khách sạn, đã loại bỏ nhãn mác và nghiền hạt."
            ));

            materialRepository.save(new Material(
                    "Sắt thép xây dựng & máy móc cũ",
                    11000.0,
                    "kg",
                    5200.0,
                    "https://images.unsplash.com/photo-1504917599217-d4dc5ebe6122?auto=format&fit=crop&w=700&q=80",
                    "Sắt đặc công trình, dầm chữ I, xà gồ cắt khúc 50-80cm, tỷ lệ gỉ sét thấp."
            ));
        }

        // 4. Khởi tạo dữ liệu mẫu yêu cầu thu gom (Contacts) cho Dashboard
        if (contactRepository.count() == 0) {
            Contact c1 = new Contact();
            c1.setFullName("Công ty Cổ phần May Nhà Bè");
            c1.setPhone("0908123456");
            c1.setEmail("contact@nhabe.vn");
            c1.setMessage("Cần ký hợp đồng thu gom định kỳ 3 tấn vải vụn và bìa carton mỗi tuần tại xưởng quận 7.");
            c1.setTopics(Set.of("Rác tái chế"));
            c1.setStatus(Contact.Status.NEW);
            c1.setCreatedAt(Instant.now().minusSeconds(3600 * 2));
            contactRepository.save(c1);

            Contact c2 = new Contact();
            c2.setFullName("Khách Sạn InterContinental Saigon");
            c2.setPhone("0918987654");
            c2.setEmail("green@intercont.vn");
            c2.setMessage("Yêu cầu lắp đặt thùng rác phân loại và định kỳ thu gom rác hữu cơ, vỏ chai thủy tinh hàng ngày.");
            c2.setTopics(Set.of("Rác hữu cơ", "Rác tái chế"));
            c2.setStatus(Contact.Status.CONTACTED);
            c2.setCreatedAt(Instant.now().minusSeconds(3600 * 12));
            contactRepository.save(c2);

            Contact c3 = new Contact();
            c3.setFullName("Trường Đại học Bách Khoa");
            c3.setPhone("0934567890");
            c3.setEmail("moitruong@hcmut.edu.vn");
            c3.setMessage("Chương trình ngày hội đổi rác lấy quà và thu gom rác thải điện tử máy tính hỏng.");
            c3.setTopics(Set.of("Rác điện tử"));
            c3.setStatus(Contact.Status.CLOSED);
            c3.setCreatedAt(Instant.now().minusSeconds(3600 * 48));
            contactRepository.save(c3);
        }

        // 5. Khởi tạo dữ liệu mẫu đơn hàng (Orders) cho Admin Quản Lý Đơn
        if (orderRepository.count() == 0) {
            orderRepository.save(new Order(
                    "Công ty TNHH Nhựa Tái Sinh Xanh",
                    "0938112233",
                    "KCN Tân Bình, P. Tây Thạnh, Q. Tân Phú, TP. HCM",
                    12000000.0,
                    "Chai nhựa PET tái chế (1000 kg)"
            ));

            orderRepository.save(new Order(
                    "Xưởng Đúc Nhôm Đồng Hải Phát",
                    "0977889900",
                    "Đường số 3, KCN Sóng Thần 1, Dĩ An, Bình Dương",
                    22900000.0,
                    "Vỏ lon nhôm sạch ép kiện (500 kg), Đồng phế liệu cáp điện loại 1 (20 kg)"
            ));
        }
    }
}
