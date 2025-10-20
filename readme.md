# Car Showroom Management System

## Tổng quan dự án
Hệ thống quản lý showroom xe hơi được xây dựng bằng Spring Boot 3.5.6 và Java 21.

## Công nghệ sử dụng
- **Backend Framework**: Spring Boot 3.5.6
- **Java Version**: Java 21
- **Build Tool**: Gradle 8.14.3
- **Database**: MySQL 8.0
- **ORM**: Spring Data JPA / Hibernate
- **Dependencies**:
  - Spring Boot Starter Web (REST API)
  - Spring Boot Starter Data JPA
  - MySQL Connector
  - Lombok
  - Spring Boot DevTools

## Cấu trúc Database

### Các bảng chính:
1. **brand** - Thương hiệu xe
2. **car_category** - Loại xe (Sedan, SUV, Pickup, etc.)
3. **car** - Thông tin xe
4. **car_image** - Hình ảnh xe
5. **customer** - Khách hàng
6. **user_account** - Tài khoản người dùng
7. **employee** - Nhân viên
8. **test_drive_request** - Yêu cầu lái thử
9. **order_deposit** - Đơn hàng/Đặt cọc
10. **favorite_car** - Xe yêu thích
11. **contact_request** - Liên hệ/Hỗ trợ

## Cấu trúc Project

```
src/main/java/com/showcar/showcar/
├── model/              # Entity classes (JPA)
│   ├── Brand.java
│   ├── Car.java
│   ├── CarCategory.java
│   ├── CarImage.java
│   ├── Customer.java
│   ├── UserAccount.java
│   ├── Employee.java
│   ├── TestDriveRequest.java
│   ├── OrderDeposit.java
│   ├── FavoriteCar.java
│   └── ContactRequest.java
│
├── repository/         # JPA Repositories
│   ├── BrandRepository.java
│   ├── CarRepository.java
│   ├── CarCategoryRepository.java
│   ├── CarImageRepository.java
│   ├── CustomerRepository.java
│   ├── UserAccountRepository.java
│   ├── EmployeeRepository.java
│   ├── TestDriveRequestRepository.java
│   ├── OrderDepositRepository.java
│   ├── FavoriteCarRepository.java
│   └── ContactRequestRepository.java
│
├── dto/                # Data Transfer Objects
│   ├── BrandDTO.java
│   ├── CarDTO.java
│   ├── CarCategoryDTO.java
│   ├── CarImageDTO.java
│   ├── CustomerDTO.java
│   ├── UserAccountDTO.java
│   ├── EmployeeDTO.java
│   ├── TestDriveRequestDTO.java
│   ├── OrderDepositDTO.java
│   ├── FavoriteCarDTO.java
│   └── ContactRequestDTO.java
│
├── service/            # Business Logic Layer
│   ├── BrandService.java
│   ├── CarService.java
│   ├── CarCategoryService.java
│   ├── CustomerService.java
│   └── OrderDepositService.java
│
└── controller/         # REST API Controllers
    ├── BrandController.java
    ├── CarController.java
    ├── CarCategoryController.java
    ├── CustomerController.java
    └── OrderDepositController.java
```

## Cấu hình Database

File: `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/showroom_db
spring.datasource.username=root
spring.datasource.password=12345
spring.jpa.hibernate.ddl-auto=update
```

## Cài đặt và Chạy

### 1. Yêu cầu hệ thống
- Java 21 trở lên
- MySQL 8.0
- Gradle 8.14.3 (hoặc sử dụng Gradle Wrapper)

### 2. Tạo Database
Chạy file SQL để tạo database và các bảng:
```sql
CREATE DATABASE IF NOT EXISTS car_showroom_db;
USE car_showroom_db;
-- Chạy các câu lệnh CREATE TABLE...
```

### 3. Cấu hình Database
Cập nhật thông tin kết nối trong `application.properties`:
- URL: `jdbc:mysql://localhost:3306/car_showroom_db`
- Username: `root`
- Password: `12345`

### 4. Build và Run
```bash
# Sử dụng Gradle Wrapper
./gradlew clean build
./gradlew bootRun

# Hoặc
gradle clean build
gradle bootRun
```

Server sẽ chạy tại: `http://localhost:8080`

## API Endpoints

### Brand APIs
```
GET    /api/brands              - Lấy danh sách tất cả thương hiệu
GET    /api/brands/{id}         - Lấy thông tin thương hiệu theo ID
GET    /api/brands/name/{name}  - Lấy thông tin thương hiệu theo tên
POST   /api/brands              - Tạo thương hiệu mới
PUT    /api/brands/{id}         - Cập nhật thương hiệu
DELETE /api/brands/{id}         - Xóa thương hiệu
```

### Car Category APIs
```
GET    /api/categories              - Lấy danh sách loại xe
GET    /api/categories/{id}         - Lấy loại xe theo ID
GET    /api/categories/name/{name}  - Lấy loại xe theo tên
POST   /api/categories              - Tạo loại xe mới
PUT    /api/categories/{id}         - Cập nhật loại xe
DELETE /api/categories/{id}         - Xóa loại xe
```

### Car APIs
```
GET    /api/cars                          - Lấy danh sách tất cả xe
GET    /api/cars/{id}                     - Lấy thông tin xe theo ID
GET    /api/cars/brand/{brandId}          - Lấy xe theo thương hiệu
GET    /api/cars/category/{categoryId}    - Lấy xe theo loại
GET    /api/cars/status/{status}          - Lấy xe theo trạng thái
GET    /api/cars/search?keyword=...       - Tìm kiếm xe
GET    /api/cars/price-range?min=...&max=... - Lọc xe theo giá
POST   /api/cars                          - Tạo xe mới
PUT    /api/cars/{id}                     - Cập nhật xe
DELETE /api/cars/{id}                     - Xóa xe
```

### Customer APIs
```
GET    /api/customers                 - Lấy danh sách khách hàng
GET    /api/customers/{id}            - Lấy khách hàng theo ID
GET    /api/customers/email/{email}   - Lấy khách hàng theo email
GET    /api/customers/phone/{phone}   - Lấy khách hàng theo SĐT
POST   /api/customers                 - Tạo khách hàng mới
PUT    /api/customers/{id}            - Cập nhật khách hàng
DELETE /api/customers/{id}            - Xóa khách hàng
```

### Order Deposit APIs
```
GET    /api/orders                        - Lấy tất cả đơn hàng
GET    /api/orders/{id}                   - Lấy đơn hàng theo ID
GET    /api/orders/customer/{customerId}  - Lấy đơn hàng theo khách hàng
GET    /api/orders/status/{status}        - Lấy đơn hàng theo trạng thái
POST   /api/orders                        - Tạo đơn hàng mới
PATCH  /api/orders/{id}/status            - Cập nhật trạng thái đơn
DELETE /api/orders/{id}                   - Xóa đơn hàng
```

## Các Enum trong hệ thống

### Car.CarCondition
- `New` - Xe mới
- `Used` - Xe đã qua sử dụng

### Car.CarStatus
- `Available` - Có sẵn
- `Sold` - Đã bán
- `Discontinued` - Ngừng kinh doanh

### OrderDeposit.OrderStatus
- `Pending` - Đang chờ
- `Deposited` - Đã đặt cọc
- `Delivered` - Đã giao xe
- `Canceled` - Đã hủy

### TestDriveRequest.TestDriveStatus
- `Pending` - Đang chờ
- `Confirmed` - Đã xác nhận
- `Completed` - Hoàn thành
- `Canceled` - Đã hủy

### UserAccount.UserRole
- `customer` - Khách hàng
- `admin` - Quản trị viên
- `sales` - Nhân viên bán hàng
- `marketing` - Nhân viên marketing

### Employee.EmployeePosition
- `Administrator` - Quản trị viên
- `Sales_Consultant` - Tư vấn bán hàng
- `Marketing_Staff` - Nhân viên marketing

## Features đã implement

✅ Entity Models với đầy đủ relationships
✅ JPA Repositories với custom queries
✅ DTOs cho data transfer
✅ Service layer với business logic
✅ REST API Controllers
✅ Transaction management
✅ CRUD operations cho tất cả entities
✅ Search và Filter functionality
✅ UUID primary keys
✅ Timestamp tracking (created_at, updated_at)

## Những gì cần phát triển thêm

- 🔲 Authentication & Authorization (Spring Security)
- 🔲 File upload cho car images
- 🔲 Pagination và Sorting
- 🔲 Exception handling toàn cục
- 🔲 Validation cho input data
- 🔲 Unit tests & Integration tests
- 🔲 API documentation (Swagger/OpenAPI)
- 🔲 Logging
- 🔲 Caching
- 🔲 Email notifications

## License
MIT License
