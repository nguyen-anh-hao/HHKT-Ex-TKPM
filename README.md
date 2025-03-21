# Dự án Quản lý sinh viên

## Cấu trúc source code
```
📦 HHKT-Ex-TKPM
├─ client # Chứa source code frontend, sử dụng framework NextJS
│  ├─ .gitignore
│  ├─ README.md
│  ├─ eslint.config.mjs
│  ├─ next-env.d.ts
│  ├─ next.config.ts
│  ├─ package-lock.json
│  ├─ package.json
│  ├─ src
│  │  └─ app
│  │     ├─ _components
│  │     │  └─ Layout.tsx
│  │     ├─ dataValidation.ts
│  │     ├─ favicon.ico
│  │     ├─ inputoutput-data-management
│  │     │  └─ page.tsx
│  │     ├─ layout.tsx
│  │     ├─ page.tsx
│  │     ├─ reference-data-management
│  │     │  └─ page.tsx
│  │     ├─ store
│  │     │  └─ referenceDataStore.ts
│  │     └─ student-management
│  │        ├─ _components
│  │        │  ├─ StudentActions.ts
│  │        │  ├─ StudentModal.tsx
│  │        │  ├─ StudentSchema.ts
│  │        │  └─ StudentTable.tsx
│  │        ├─ interface
│  │        │  └─ Student.ts
│  │        └─ page.tsx
│  └─ tsconfig.json
└─ server # Chứa source code backend, sử dụng framework Java Spring Boot
   ├─ .gitattributes
   ├─ .gitignore
   ├─ .mvn
   │  └─ wrapper
   │     └─ maven-wrapper.properties
   ├─ docker-compose.yml
   ├─ mvnw
   ├─ mvnw.cmd
   ├─ node_modules
   │  └─ .cache
   │     └─ _logs
   │        ├─ 2025-03-19T12_36_48_635Z-debug-0.log
   │        ├─ 2025-03-20T00_36_41_407Z-debug-0.log
   │        └─ 2025-03-21T06_32_07_932Z-debug-0.log
   ├─ pom.xml
   └─ src
      ├─ main
      │  ├─ java
      │  │  └─ org
      │  │     └─ example
      │  │        └─ backend
      │  │           ├─ BackendApplication.java
      │  │           ├─ common
      │  │           │  ├─ Auditable.java
      │  │           │  └─ PaginationInfo.java
      │  │           ├─ config
      │  │           │  ├─ AuditorAwareConfig.java
      │  │           │  └─ CorsConfig.java
      │  │           ├─ controller
      │  │           │  ├─ ChuongTrinhController.java
      │  │           │  ├─ FileTransferController.java
      │  │           │  ├─ KhoaController.java
      │  │           │  ├─ SinhVienController.java
      │  │           │  └─ TinhTrangSinhVienController.java
      │  │           ├─ domain
      │  │           │  ├─ ChuongTrinh.java
      │  │           │  ├─ DiaChi.java
      │  │           │  ├─ GiayTo.java
      │  │           │  ├─ Khoa.java
      │  │           │  ├─ SinhVien.java
      │  │           │  └─ TinhTrangSinhVien.java
      │  │           ├─ dto
      │  │           │  ├─ request
      │  │           │  │  ├─ ChuongTrinhRequest.java
      │  │           │  │  ├─ DiaChiRequest.java
      │  │           │  │  ├─ GiayToRequest.java
      │  │           │  │  ├─ KhoaRequest.java
      │  │           │  │  ├─ SinhVienRequest.java
      │  │           │  │  └─ TinhTrangSinhVienRequest.java
      │  │           │  └─ response
      │  │           │     ├─ ApiResponse.java
      │  │           │     ├─ ChuongTrinhResponse.java
      │  │           │     ├─ DiaChiResponse.java
      │  │           │     ├─ GiayToResponse.java
      │  │           │     ├─ KhoaResponse.java
      │  │           │     ├─ SinhVienResponse.java
      │  │           │     └─ TinhTrangSinhVienResponse.java
      │  │           ├─ mapper
      │  │           │  ├─ ChuongTrinhMapper.java
      │  │           │  ├─ DiaChiMapper.java
      │  │           │  ├─ GiayToMapper.java
      │  │           │  ├─ KhoaMapper.java
      │  │           │  ├─ SinhVienMapper.java
      │  │           │  └─ TinhTrangSinhVienMapper.java
      │  │           ├─ repository
      │  │           │  ├─ IChuongTrinhRepository.java
      │  │           │  ├─ IDiaChiRepository.java
      │  │           │  ├─ IGiayToRepository.java
      │  │           │  ├─ IKhoaRepository.java
      │  │           │  ├─ ISinhVienRepository.java
      │  │           │  └─ ITinhTrangRepository.java
      │  │           └─ service
      │  │              ├─ IChuongTrinhService.java
      │  │              ├─ IKhoaService.java
      │  │              ├─ ISinhVienService.java
      │  │              ├─ ITinhTrangSinhVienService.java
      │  │              ├─ Import
      │  │              │  ├─ CSVImportService.java
      │  │              │  ├─ ExcelImportService.java
      │  │              │  ├─ ImportService.java
      │  │              │  ├─ ImportServiceFactory.java
      │  │              │  ├─ JSONImportService.java
      │  │              │  └─ XMLImportService.java
      │  │              ├─ export
      │  │              │  ├─ CSVExportService.java
      │  │              │  ├─ ExcelExportService.java
      │  │              │  ├─ ExportService.java
      │  │              │  ├─ ExportServiceFactory.java
      │  │              │  ├─ JSONExportService.java
      │  │              │  └─ XMLExportService.java
      │  │              └─ impl
      │  │                 ├─ ChuongTrinhServiceImpl.java
      │  │                 ├─ KhoaServiceImpl.java
      │  │                 ├─ SinhVienServiceImpl.java
      │  │                 └─ TinhTrangSinhVienServiceImpl.java
      │  └─ resources
      │     ├─ application.yml
      │     └─ db
      │        ├─ V1_20250319081659_create_table.sql
      │        └─ V1_20250319082559_insert_data.sql
      └─ test
         └─ java
            └─ org
               └─ example
                  └─ backend
                     └─ BackendApplicationTests.java
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

Để xem danh sách sinh viên, vào mục "Quản lý sinh viên", ở đây cũng có thể xóa, sửa sinh viên

![image](https://github.com/user-attachments/assets/c67179a9-3ea7-4b8f-84ed-30abaffc9ab0)

Để thêm sinh viên, ấn nút bấm "Thêm sinh viên"

![image](https://github.com/user-attachments/assets/f71057c1-922a-4055-89bf-bcb3077d0a9b)



