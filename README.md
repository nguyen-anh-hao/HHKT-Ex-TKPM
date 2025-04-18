# Dự án Quản lý sinh viên

## Cấu trúc source code
```
.
├── client/
│   ├── src/
│   │   ├── app/
│   │   │   ├── class-management/
│   │   │   ├── course-management/
│   │   │   ├── enroll-class/
│   │   │   ├── reference-management/
│   │   │   ├── status-rules-configuration/
│   │   │   ├── student-management/
│   │   │   ├── transcript/
│   │   │   ├── favicon.ico
│   │   │   ├── layout.tsx
│   │   │   └── page.tsx
│   │   ├── components/
│   │   ├── interfaces/
│   │   └── libs/
│   │       ├── api/
│   │       ├── hooks/
│   │       ├── services/
│   │       ├── stores/
│   │       ├── utils/
│   │       └── validators/
│   ├── .gitignore
│   ├── README.md
│   ├── eslint.config.mjs
│   ├── next-env.d.ts
│   ├── next.config.ts
│   ├── package-lock.json
│   ├── package.json
│   └── tsconfig.json
├── docs/
│   ├── The Broken Window Theory & The Boy Scout Rule in Software Development.pdf
│   └── Unit Test Coverage và Best Practices.pdf
├── server/
│   ├── .mvn/
│   │   └── wrapper/
│   │       └── maven-wrapper.properties
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── org/
│   │   │   │       └── example/
│   │   │   │           └── backend/
│   │   │   │               ├── common/
│   │   │   │               ├── config/
│   │   │   │               ├── controller/
│   │   │   │               ├── domain/
│   │   │   │               ├── dto/
│   │   │   │               │   ├── data/
│   │   │   │               │   ├── request/
│   │   │   │               │   └── response/
│   │   │   │               ├── mapper/
│   │   │   │               ├── repository/
│   │   │   │               ├── service/
│   │   │   │               │   ├── Import/
│   │   │   │               │   ├── export/
│   │   │   │               │   ├── impl/
│   │   │   │               ├── validator/
│   │   │   │               └── BackendApplication.java
│   │   │   └── resources/
│   │   │       ├── config/
│   │   │       ├── db/
│   │   │       ├── font/
│   │   │       └── application.yml
│   │   └── test/
│   │       └── java/
│   │           └── org/
│   │               └── example/
│   │                   └── backend/
│   │                       ├── controller/
│   │                       ├── service/
│   │                       ├── validator/
│   │                       └── BackendApplicationTests.java
│   ├── .gitattributes
│   ├── .gitignore
│   ├── README.md
│   ├── docker-compose.yml
│   ├── mvnw
│   ├── mvnw.cmd
│   └── pom.xml
└── README.md
```

## Hướng dẫn cài đặt & Chạy chương trình
- Server
  - Sử dụng framework Java Spring Boot nên máy tính cần cài dặt các môi trường cũng như công cụ sau:
    - Docker
    - JDK
    - Maven
    - IDE IntelliJ IDEA
  - Chạy chương trình, vào thư mục Server
    - Bước 1: Chạy Docker và seed database bằng Terminal
      ```
      docker-compose up -d
      ```
      Sau khi docker run thành công, seed database bằng các file .SQL trong thư mục src/mainresources/db.
    - Bước 2: Mở file BackendApplication.java, bấm nút run trên IDE.
- Client
  - Sử dụng framework NextJS trên NodeJS nên máy tính cần cài đặt các môi trường cũng như công cụ sau:
    - NodeJS
    - Visual Studio Code
  - Chạy chương trình, vào thư mục Client
    - Bước 1: Cài dependencies bằng Terminal
      ```
      npm install
      ```
    - Bước 2: Chạy chương trình bằng Terminal
      ```
      npm run dev
      ```

## Chạy chương trình
- Truy cập locahost:3000 để xem website frontend
- Truy cập locahost:9000 để gọi api backend (có thể dùng Postman để test)

## Sử dụng chương trình

Backend API (Swagger)

![image](https://github.com/user-attachments/assets/49df6aaf-dd29-42c7-9bf0-e7f0d8b2e263)

![image](https://github.com/user-attachments/assets/13c6e019-9399-4186-8651-320fcc8e87ec)

Để xem danh sách sinh viên, vào mục "Quản lý sinh viên", ở đây cũng có thể xóa, sửa sinh viên

![image](https://github.com/user-attachments/assets/303fc915-145b-4f12-9303-98316ec24c12)


Để thêm sinh viên, ấn nút bấm "Thêm sinh viên"

![image](https://github.com/user-attachments/assets/f71057c1-922a-4055-89bf-bcb3077d0a9b)

Để thêm, xóa, sửa các nhãn mới cho Khoa, Chương trình, Trạng thái, Domain Email vào mục "Quản lý danh muc"

![image](https://github.com/user-attachments/assets/b41e3421-af03-42a3-b03f-adcf99678cb4)

Cấu hình quy tắc chuyển trạng thái sinh viên

![image](https://github.com/user-attachments/assets/5de8f980-5c2e-4daf-a2ad-0b5e88860226)

Quản lý môn học

![image](https://github.com/user-attachments/assets/489c371b-9b44-4b73-90b9-e2a0784eaefd)

Quản lý lớp học

![image](https://github.com/user-attachments/assets/783cdf1a-0219-4782-93d7-46a97fe1fb60)

Quản lý đăng ký lớp học

![image](https://github.com/user-attachments/assets/abd295fc-8581-4a3f-957e-c27caa8b0f00)

Bảng điểm sinh viên

![image](https://github.com/user-attachments/assets/4933647a-d89e-4da1-9363-be3dd2b33e01)

![image](https://github.com/user-attachments/assets/0a7666e6-989b-4172-bdbe-45a6497c72c4)


## Database

![image](https://github.com/user-attachments/assets/35e25919-bb7a-4a10-a0d9-3ab5cb20ad10)
