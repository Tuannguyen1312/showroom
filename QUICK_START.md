# ⚡ Quick Start Guide - ShowCar

## 🚀 Chạy ứng dụng nhanh (3 bước)

### Bước 1: Setup Database

```bash
# Login vào MySQL
mysql -u root -p

# Tạo database
CREATE DATABASE Carshow1;
```

### Bước 2: Cấu hình (nếu cần)

Chỉnh sửa `src/main/resources/application.properties`:
```properties
spring.datasource.username=root        # Thay bằng MySQL username của bạn
spring.datasource.password=123456     # Thay bằng MySQL password của bạn
```

### Bước 3: Chạy ứng dụng

```bash
# Cách 1: Dùng Gradle (Khuyến nghị)
./gradlew bootRun

# Cách 2: Dùng IDE
# Mở ShowcarApplication.java và click Run
```

## ✅ Kiểm tra ứng dụng đã chạy

Khi thấy log này là OK:
```
Tomcat started on port 8080
Started ShowcarApplication
```

Truy cập: http://localhost:8080

## 🧪 Test nhanh với cURL

### 1. Đăng ký user mới:
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Test User",
    "email": "test@example.com",
    "phone": "0123456789",
    "username": "testuser",
    "password": "password123"
  }'
```

**Lưu lại token từ response!**

### 2. Đăng nhập:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

### 3. Lấy danh sách xe (không cần token):
```bash
curl http://localhost:8080/api/cars
```

### 4. Lấy thương hiệu (không cần token):
```bash
curl http://localhost:8080/api/brands
```

## 📦 Test với Postman

1. **Import collection**: File `POSTMAN_COLLECTION.json`
2. **Set baseUrl**: `http://localhost:8080` (đã có sẵn)
3. **Đăng ký/Đăng nhập**: Token sẽ tự động được lưu vào variable `token`
4. **Test các API khác**: Token sẽ tự động được gửi trong header

## 🎯 Test các tính năng chính

### ✅ Authentication với Argon2
- Đăng ký user → Password được hash bằng Argon2
- Đăng nhập → Verify password với Argon2 hash

### ✅ JWT Token
- Login/Register → Nhận được JWT token
- Dùng token trong header: `Authorization: Bearer TOKEN`

### ✅ File Upload
- Upload hình ảnh xe tại `/api/cars/{carId}/images`
- Xem hình ảnh tại `/uploads/images/{filename}`

### ✅ Statistics
- Xem dashboard tại `/api/statistics/dashboard` (cần role ADMIN/SALES/MARKETING)

### ✅ Email Notifications
- Tạo order → Tự động gửi email xác nhận
- Tạo test drive request → Tự động gửi email xác nhận

## 🐛 Lỗi thường gặp

### Database connection failed
```
Kiểm tra:
- MySQL đang chạy: mysql -u root -p
- Database Carshow1 đã được tạo
- Username/password trong application.properties đúng
```

### Port 8080 already in use
```
Thay đổi port trong application.properties:
server.port=8081
```

### JWT token expired
```
Login lại để lấy token mới
Token mặc định có thời hạn 24 giờ
```

## 📚 Tài liệu chi tiết

Xem file `TESTING_GUIDE.md` để biết chi tiết cách test từng API endpoint.

