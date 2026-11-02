# ♻️ WasteHeroes – Nền Tảng Tái Chế & Thu Gom Rác Thải Thông Minh

> **Dự án Showcase Portfolio & GitHub Profile**  
> Hệ thống sàn thương mại điện tử kết nối chuỗi cung ứng vật liệu tái sinh và điều phối dịch vụ thu gom rác thải theo mô hình **Kinh Tế Tuần Hoàn (Circular Economy)**.

---

## 🌟 Huy hiệu Công Nghệ (Tech Stack Badges)

![Java](https://img.shields.io/badge/Java-21%20LTS-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.4-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6.x-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-JPA-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-Template_Engine-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)
![Database](https://img.shields.io/badge/Database-H2%20%7C%20MySQL%208-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5.3-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white)

---

## 📖 Giới Thiệu Dự Án

**WasteHeroes** giải quyết bài toán nhức nhối trong ngành xử lý phế liệu truyền thống: thiếu tính chuẩn hóa, thông tin phân mảnh, biểu giá không minh bạch và chuỗi cung ứng bị đứt gãy.

Hệ thống cung cấp một nền tảng Web tập trung giúp:
1. **Người dân & Doanh nghiệp**: Dễ dàng đặt lịch thu gom các loại rác tái chế, rác hữu cơ, rác thải điện tử (E-waste) tận nơi với biểu giá niêm yết công khai.
2. **Nhà máy & Cơ sở sản xuất**: Mua nguồn nguyên liệu phế liệu tái sinh đã được làm sạch, ép kiện đạt chuẩn (nhựa PET, thùng carton, đồng cáp điện, vỏ lon nhôm...).
3. **Đội ngũ quản trị (WasteHeroes Admin)**: Quản lý toàn bộ kho hàng, phân loại yêu cầu, quản lý đơn đặt hàng và theo dõi chỉ số qua Dashboard trực quan.

---

## 🚀 Tính Năng Nổi Bật (Key Features)

### 1. 🛍️ Sàn Giao Dịch Vật Liệu Tái Sinh (Recycling Marketplace)
- Niêm yết sản phẩm theo danh mục và đơn vị đo chuẩn (`kg`, `tấn`).
- Bộ lọc và thanh **tìm kiếm trực tiếp theo thời gian thực (Live Search)**.
- **Giỏ hàng Session-based**: Khách hàng có thể thêm bớt hàng, điều chỉnh số lượng và tự động cập nhật tổng tiền.
- **Quy trình đặt hàng thông minh**: Kiểm tra tồn kho, tự động trừ số lượng hàng trong kho (`stock inventory deduction`) khi khách đặt mua.

### 2. 🚛 Cổng Đặt Lịch & Báo Giá Thu Gom (Smart Collection Request)
- Form đăng ký thông tin thu gom theo nhóm dịch vụ: Rác tái chế, Rác hữu cơ vi sinh, Rác điện tử nguy hại.
- Điều hướng yêu cầu tự động về bộ phận điều phối viên xử lý.

### 3. 🔐 Bảo Mật & Phân Quyền Vai Trò (Role-Based Access Control)
- Sử dụng **Spring Security 6** với phân cấp quyền hạn:
  $$\text{ROLE\_ADMIN} > \text{ROLE\_STAFF} > \text{ROLE\_USER}$$
- Mã hóa mật khẩu bảo mật cao bằng thuật toán **BCrypt**.
- Đăng nhập thông minh: Tự động điều hướng Quản trị viên vào `/admin/dashboard` và Người dùng thông thường về `/`.

### 4. 📊 Cổng Quản Trị Toàn Diện (Admin Portal & Dashboard)
- **Dashboard trực quan**: Tích hợp **Chart.js** biểu diễn cơ cấu yêu cầu thu gom và tỷ lệ giải quyết hợp đồng.
- **Quản lý kho hàng (CRUD Materials)**: Thêm mới, chỉnh sửa thông số, cập nhật giá và xóa mặt hàng trong kho.
- **Quản lý đơn hàng (Orders Management)**: Xem chi tiết giỏ hàng của khách, cập nhật trạng thái đơn (*Mới đặt &rarr; Đang giao &rarr; Hoàn thành*).
- **Quản lý yêu cầu (Contacts Management)**: Phân loại trạng thái (*NEW &rarr; CONTACTED &rarr; CLOSED*).

### 5. ⚡ Khởi Chạy 1-Click (Zero-Config Setup)
- Tích hợp sẵn cơ chế **H2 Database Engine** tự động lưu trữ dữ liệu vào thư mục `./data`, giúp người xem demo (Recruiter / Interviewer) có thể chạy web ngay lập tức mà **không cần cài đặt hay thiết lập tài khoản MySQL Server**.
- Sẵn sàng chuyển đổi sang **MySQL Server thật** chỉ bằng việc thay đổi 1 dòng trong file cấu hình `application.properties`.

---

## 🛠️ Cấu Trúc Thư Mục (Project Structure)

```text
webrac/
├── src/main/java/com/example/webrac/
│   ├── config/             # Cấu hình khởi tạo dữ liệu mẫu (DataInitializer)
│   ├── controller/         # Web & API Controllers (Admin, Market, Cart, Home, Auth)
│   ├── dto/                # Data Transfer Objects (Login, Register)
│   ├── model/              # JPA Entities (Material, Order, Contact, User, Role)
│   ├── repository/         # Spring Data JPA Repositories
│   ├── security/           # Spring Security 6 Configuration & RoleHierarchy
│   └── service/            # Business Logic & Statistical Services
├── src/main/resources/
│   ├── static/assets/      # CSS Design System, JavaScript & Images
│   ├── templates/          # Thymeleaf Templates (user/ & admin/)
│   └── application.properties # Cấu hình Database & Mail Server
├── pom.xml                 # Maven Dependencies & Build Configuration
└── mvnw.cmd                # Maven Wrapper script (không cần cài sẵn Maven)
```

---

## ⚡ Hướng Dẫn Cài Đặt & Chạy Ứng Dụng (Quickstart)

### Yêu cầu hệ thống:
- **Java**: JDK 17 hoặc JDK 21 trở lên.
- **Hệ điều hành**: Windows, macOS hoặc Linux.

### Các bước khởi chạy:

1. **Clone repository về máy**:
   ```bash
   git clone https://github.com/<your-username>/wasteheroes-webrac.git
   cd wasteheroes-webrac
   ```

2. **Chạy ứng dụng với Maven Wrapper**:
   - Trên **Windows**:
     ```powershell
     .\mvnw.cmd spring-boot:run
     ```
   - Trên **macOS / Linux**:
     ```bash
     ./mvnw spring-boot:run
     ```

3. **Mở trình duyệt và trải nghiệm**:
   - **Trang chủ**: [http://localhost:8080/](http://localhost:8080/)
   - **Sàn giao dịch phế liệu**: [http://localhost:8080/market](http://localhost:8080/market)
   - **Trang quản trị Admin**: [http://localhost:8080/admin/dashboard](http://localhost:8080/admin/dashboard)
   - **H2 Database Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)  
     *(JDBC URL: `jdbc:h2:file:./data/webrac`, User: `sa`, Password: để trống)*

---

## 🔑 Tài Khoản Demo Test Sẵn (Demo Accounts)

| Vai trò (Role) | Email đăng nhập | Mật khẩu | Chức năng kiểm thử |
| :--- | :--- | :--- | :--- |
| **Quản trị viên (Admin)** | `admin@wasteheroes.vn` | `admin123` | Quản lý kho, xem biểu đồ Dashboard, duyệt đơn hàng |
| **Người dùng mẫu (User)** | `user@wasteheroes.vn` | `user123` | Mua hàng, thêm giỏ hàng, gửi yêu cầu báo giá |

*(Trang đăng nhập `/login` cũng đã có sẵn nút bấm tiện lợi để tự động điền tài khoản mẫu với 1 cú nhấp chuột!)*

---

## 👨‍💻 Tác Giả & Liên Hệ (Author)

- **Họ và tên**: [Tên Của Bạn]
- **Email**: your-email@gmail.com
- **GitHub**: [github.com/your-username](https://github.com/)
- **LinkedIn**: [linkedin.com/in/your-profile](https://linkedin.com/)

---
*⭐ Nếu bạn thấy dự án này thú vị và hữu ích, hãy để lại 1 Star trên GitHub nhé!*
