# 🚀 Hướng dẫn Test và Chạy Ứng dụng ShowCar

## 📋 Yêu cầu trước khi chạy

1. **Java 21** - Cài đặt Java 21
2. **MySQL 8.0+** - MySQL server đang chạy
3. **Gradle** - Đã có sẵn trong project (gradlew)

## 🔧 Bước 1: Setup Database

### Tạo database MySQL:

```sql
CREATE DATABASE Carshow1;
```

Hoặc có thể để Hibernate tự động tạo database khi `spring.jpa.hibernate.ddl-auto=update`

### Cấu hình database trong `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/Carshow1
spring.datasource.username=root
spring.datasource.password=123456
```

**Lưu ý**: Thay đổi username và password theo MySQL của bạn!

## 🚀 Bước 2: Chạy ứng dụng

### Cách 1: Dùng Gradle (Khuyến nghị)

```bash
# Build project
./gradlew build

# Chạy ứng dụng
./gradlew bootRun
```

### Cách 2: Dùng IDE (IntelliJ IDEA / Eclipse)

1. Mở project trong IDE
2. Chạy class `ShowcarApplication.java`
3. Hoặc click Run button

### Cách 3: Chạy JAR file

```bash
# Build JAR
./gradlew bootJar

# Chạy JAR
java -jar build/libs/showcar-0.0.1-SNAPSHOT.jar
```

## ✅ Kiểm tra ứng dụng đã chạy

Khi ứng dụng chạy thành công, bạn sẽ thấy:

```
Tomcat started on port 8080
Started ShowcarApplication in X seconds
```

Truy cập: http://localhost:8080

## 🧪 Bước 3: Test API

### Công cụ test API:

- **Postman** (Khuyến nghị) - https://www.postman.com/
- **Insomnia** - https://insomnia.rest/
- **cURL** (Command line)
- **Thunder Client** (VS Code extension)

## 📝 Test các API Endpoints

### 1. Test Authentication APIs

#### 1.1. Đăng ký tài khoản (Public)

**POST** `/api/auth/register`

```json
{
  "fullName": "Nguyễn Văn A",
  "email": "test@example.com",
  "phone": "0123456789",
  "address": "123 Đường ABC",
  "username": "testuser",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "username": "testuser",
  "role": "customer",
  "customerId": 1
}
```

#### 1.2. Đăng nhập (Public)

**POST** `/api/auth/login`

```json
{
  "username": "testuser",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "username": "testuser",
  "role": "customer",
  "customerId": 1
}
```

**⚠️ Lưu ý**: Copy `token` từ response để dùng cho các API cần authentication!

### 2. Test Car APIs (Public - không cần token)

#### 2.1. Lấy tất cả xe

**GET** `/api/cars`

#### 2.2. Lấy xe theo ID

**GET** `/api/cars/{id}`

Ví dụ: **GET** `/api/cars/1`

#### 2.3. Lấy xe còn hàng

**GET** `/api/cars/available`

#### 2.4. Tìm kiếm xe

**GET** `/api/cars/search?keyword=toyota`

### 3. Test Brand APIs (Public)

**GET** `/api/brands` - Lấy tất cả thương hiệu

**GET** `/api/brands/{id}` - Lấy thương hiệu theo ID

### 4. Test Protected APIs (Cần JWT Token)

#### 4.1. Cấu hình JWT Token trong Postman:

1. Mở Postman
2. Tạo Request mới
3. Vào tab **Headers**
4. Thêm header:
   - **Key**: `Authorization`
   - **Value**: `Bearer YOUR_TOKEN_HERE`
   
   (Thay `YOUR_TOKEN_HERE` bằng token bạn nhận được từ login/register)

#### 4.2. Test Favorite Car APIs

**GET** `/api/favorites/customer/{customerId}`

**Headers**: `Authorization: Bearer YOUR_TOKEN`

**POST** `/api/favorites?customerId=1&carId=1`

**Headers**: `Authorization: Bearer YOUR_TOKEN`

**DELETE** `/api/favorites?customerId=1&carId=1`

**Headers**: `Authorization: Bearer YOUR_TOKEN`

#### 4.3. Test Test Drive Request APIs

**POST** `/api/test-drives`

**Headers**: `Authorization: Bearer YOUR_TOKEN`

```json
{
  "customerId": 1,
  "carId": 1,
  "preferredDate": "2024-12-25",
  "note": "Muốn lái thử vào buổi sáng"
}
```

#### 4.4. Test Order Deposit APIs

**POST** `/api/orders`

**Headers**: `Authorization: Bearer YOUR_TOKEN`

```json
{
  "customerId": 1,
  "carId": 1,
  "depositAmount": 100000000,
  "totalPrice": 1000000000,
  "status": "Pending"
}
```

### 5. Test File Upload APIs

#### 5.1. Upload hình ảnh xe

**POST** `/api/cars/{carId}/images`

**Headers**: 
- `Authorization: Bearer YOUR_TOKEN`
- Content-Type: `multipart/form-data`

**Body** (form-data):
- `file`: [Chọn file image]
- `isPrimary`: `true` hoặc `false`

#### 5.2. Lấy danh sách hình ảnh

**GET** `/api/cars/{carId}/images`

### 6. Test Statistics APIs (Cần role ADMIN/SALES/MARKETING)

**GET** `/api/statistics/dashboard`

**Headers**: `Authorization: Bearer ADMIN_TOKEN`

### 7. Test Admin Email APIs

**POST** `/api/admin/email/marketing`

**Headers**: `Authorization: Bearer ADMIN_TOKEN`

**Body** (form-data hoặc JSON):
```
customerEmail: test@example.com
customerName: Nguyễn Văn A
carName: Toyota RAV4
carPrice: 1000000000
promotion: Giảm 10% cho khách hàng VIP
```

## 🧪 Test với cURL (Command Line)

### Đăng ký:
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Nguyễn Văn A",
    "email": "test@example.com",
    "phone": "0123456789",
    "username": "testuser",
    "password": "password123"
  }'
```

### Đăng nhập:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

### Lấy danh sách xe (với token):
```bash
curl -X GET http://localhost:8080/api/cars \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

## 🐛 Troubleshooting

### Lỗi kết nối database:
- Kiểm tra MySQL đang chạy: `mysql -u root -p`
- Kiểm tra database đã được tạo chưa
- Kiểm tra username/password trong `application.properties`

### Lỗi port 8080 đã được sử dụng:
- Thay đổi port trong `application.properties`: `server.port=8081`
- Hoặc kill process đang dùng port 8080

### Lỗi JWT token:
- Đảm bảo token đúng format: `Bearer TOKEN`
- Kiểm tra token chưa hết hạn (default 24 giờ)
- Login lại để lấy token mới

### Lỗi file upload:
- Kiểm tra thư mục `uploads/images` đã được tạo
- Kiểm tra quyền ghi file
- File size không quá 10MB (configurable)

## 📚 Tài liệu tham khảo

- Spring Boot: https://spring.io/projects/spring-boot
- Spring Security: https://spring.io/projects/spring-security
- JWT: https://jwt.io/
- Argon2: https://github.com/phxql/argon2-jvm

## 📞 Hỗ trợ

Nếu gặp vấn đề, kiểm tra:
1. Logs trong console khi chạy ứng dụng
2. HTTP status code và error message
3. Database connection
4. JWT token validity

