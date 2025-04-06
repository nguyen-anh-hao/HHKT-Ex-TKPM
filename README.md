# Dự án Quản lý sinh viên

## Cấu trúc source code
```
.
├── client/
│   ├── src/
│   │   ├── app/
│   │   │   ├── configuration/
│   │   │   │   └── page.tsx
│   │   │   ├── inputoutput-data-management/
│   │   │   │   └── page.tsx
│   │   │   ├── reference-management/
│   │   │   │   └── page.tsx
│   │   │   ├── student-management/
│   │   │   │   ├── actions/
│   │   │   │   │   └── StudentActions.ts
│   │   │   │   ├── components/
│   │   │   │   │   ├── Home.tsx
│   │   │   │   │   ├── StudentModal.tsx
│   │   │   │   │   └── StudentTable.tsx
│   │   │   │   └── page.tsx
│   │   │   ├── favicon.ico
│   │   │   ├── layout.tsx
│   │   │   └── page.tsx
│   │   ├── components/
│   │   │   └── layout/
│   │   │       └── Layout.tsx
│   │   ├── interfaces/
│   │   │   ├── Address.ts
│   │   │   ├── CreateStudentRequest.ts
│   │   │   ├── Identify.ts
│   │   │   ├── Student.ts
│   │   │   ├── StudentResponse.ts
│   │   │   └── UpdateStudentRequest.ts
│   │   └── libs/
│   │       ├── api/
│   │       │   ├── api.ts
│   │       │   ├── referenceApi.ts
│   │       │   └── studentApi.ts
│   │       ├── hooks/
│   │       │   ├── useReferences.ts
│   │       │   ├── useStudentMutation.ts
│   │       │   └── useStudents.ts
│   │       ├── services/
│   │       │   ├── referenceService.ts
│   │       │   └── studentService.ts
│   │       ├── stores/
│   │       │   └── referenceStore.ts
│   │       ├── utils/
│   │       │   ├── cleanData.ts
│   │       │   └── studentTransform.ts
│   │       └── validators/
│   │           ├── dataValidation.ts
│   │           └── studentValidator.ts
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
│   ├── node_modules/
│   │   └── .cache/
│   │       └── _logs/
│   │           ├── 2025-03-19T12_36_48_635Z-debug-0.log
│   │           ├── 2025-03-20T00_36_41_407Z-debug-0.log
│   │           ├── 2025-03-21T06_32_07_932Z-debug-0.log
│   │           ├── 2025-03-25T03_04_41_647Z-debug-0.log
│   │           ├── 2025-03-26T13_40_07_391Z-debug-0.log
│   │           ├── 2025-03-27T01_39_25_715Z-debug-0.log
│   │           ├── 2025-03-27T13_38_54_714Z-debug-0.log
│   │           └── 2025-03-28T07_10_26_098Z-debug-0.log
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── org/
│   │   │   │       └── example/
│   │   │   │           └── backend/
│   │   │   │               ├── common/
│   │   │   │               │   ├── Auditable.java
│   │   │   │               │   └── PaginationInfo.java
│   │   │   │               ├── config/
│   │   │   │               │   ├── AuditorAwareConfig.java
│   │   │   │               │   ├── CorsConfig.java
│   │   │   │               │   ├── JacksonConfig.java
│   │   │   │               │   ├── OpenAPIConfig.java
│   │   │   │               │   └── StudentStatusRulesConfig.java
│   │   │   │               ├── controller/
│   │   │   │               │   ├── EmailDomainController.java
│   │   │   │               │   ├── FacultyController.java
│   │   │   │               │   ├── FileTransferController.java
│   │   │   │               │   ├── ProgramController.java
│   │   │   │               │   ├── StudentController.java
│   │   │   │               │   ├── StudentStatusController.java
│   │   │   │               │   └── StudentStatusRuleController.java
│   │   │   │               ├── domain/
│   │   │   │               │   ├── Address.java
│   │   │   │               │   ├── Document.java
│   │   │   │               │   ├── EmailDomain.java
│   │   │   │               │   ├── Faculty.java
│   │   │   │               │   ├── PhonePattern.java
│   │   │   │               │   ├── Program.java
│   │   │   │               │   ├── Student.java
│   │   │   │               │   ├── StudentStatus.java
│   │   │   │               │   └── StudentStatusRule.java
│   │   │   │               ├── dto/
│   │   │   │               │   ├── request/
│   │   │   │               │   │   ├── AddressRequest.java
│   │   │   │               │   │   ├── DocumentRequest.java
│   │   │   │               │   │   ├── EmailDomainRequest.java
│   │   │   │               │   │   ├── FacultyRequest.java
│   │   │   │               │   │   ├── ProgramRequest.java
│   │   │   │               │   │   ├── StudentRequest.java
│   │   │   │               │   │   ├── StudentStatusRequest.java
│   │   │   │               │   │   ├── StudentStatusRuleRequest.java
│   │   │   │               │   │   └── StudentUpdateRequest.java
│   │   │   │               │   └── response/
│   │   │   │               │       ├── APIResponse.java
│   │   │   │               │       ├── AddressResponse.java
│   │   │   │               │       ├── DocumentResponse.java
│   │   │   │               │       ├── EmailDomainResponse.java
│   │   │   │               │       ├── FacultyResponse.java
│   │   │   │               │       ├── ProgramResponse.java
│   │   │   │               │       ├── StudentResponse.java
│   │   │   │               │       ├── StudentStatusResponse.java
│   │   │   │               │       └── StudentStatusRuleResponse.java
│   │   │   │               ├── mapper/
│   │   │   │               │   ├── AddressMapper.java
│   │   │   │               │   ├── DocumentMapper.java
│   │   │   │               │   ├── EmailDomainMapper.java
│   │   │   │               │   ├── FacultyMapper.java
│   │   │   │               │   ├── ProgramMapper.java
│   │   │   │               │   ├── StudentMapper.java
│   │   │   │               │   ├── StudentStatusMapper.java
│   │   │   │               │   └── StudentStatusRuleMapper.java
│   │   │   │               ├── repository/
│   │   │   │               │   ├── IAddressRepository.java
│   │   │   │               │   ├── IDocumentRepository.java
│   │   │   │               │   ├── IEmailDomainRepository.java
│   │   │   │               │   ├── IFacultyRepository.java
│   │   │   │               │   ├── IPhonePatternRepository.java
│   │   │   │               │   ├── IProgramRepository.java
│   │   │   │               │   ├── IStudentRepository.java
│   │   │   │               │   ├── IStudentStatusRepository.java
│   │   │   │               │   └── IStudentStatusRuleRepository.java
│   │   │   │               ├── service/
│   │   │   │               │   ├── Import/
│   │   │   │               │   │   ├── CSVImportService.java
│   │   │   │               │   │   ├── ExcelImportService.java
│   │   │   │               │   │   ├── ImportService.java
│   │   │   │               │   │   ├── ImportServiceFactory.java
│   │   │   │               │   │   ├── JSONImportService.java
│   │   │   │               │   │   └── XMLImportService.java
│   │   │   │               │   ├── export/
│   │   │   │               │   │   ├── CSVExportService.java
│   │   │   │               │   │   ├── ExcelExportService.java
│   │   │   │               │   │   ├── ExportService.java
│   │   │   │               │   │   ├── ExportServiceFactory.java
│   │   │   │               │   │   ├── JSONExportService.java
│   │   │   │               │   │   └── XMLExportService.java
│   │   │   │               │   ├── impl/
│   │   │   │               │   │   ├── EmailDomainServiceImpl.java
│   │   │   │               │   │   ├── FacultyServiceImpl.java
│   │   │   │               │   │   ├── ProgramServiceImpl.java
│   │   │   │               │   │   ├── StudentServiceImpl.java
│   │   │   │               │   │   ├── StudentStatusRuleServiceImpl.java
│   │   │   │               │   │   └── StudentStatusServiceImpl.java
│   │   │   │               │   ├── IEmailDomainService.java
│   │   │   │               │   ├── IFacultyService.java
│   │   │   │               │   ├── IProgramService.java
│   │   │   │               │   ├── IStudentService.java
│   │   │   │               │   ├── IStudentStatusRuleService.java
│   │   │   │               │   └── IStudentStatusService.java
│   │   │   │               ├── validator/
│   │   │   │               │   ├── EmailDomain.java
│   │   │   │               │   ├── EmailDomainValidator.java
│   │   │   │               │   ├── PhoneNumber.java
│   │   │   │               │   ├── PhoneNumberValidator.java
│   │   │   │               │   ├── StudentStatusTransitionValidator.java
│   │   │   │               │   └── ValidStudentStatusTransition.java
│   │   │   │               └── BackendApplication.java
│   │   │   └── resources/
│   │   │       ├── config/
│   │   │       │   └── student-status-rules.json
│   │   │       ├── db/
│   │   │       │   ├── 1_create_table.sql
│   │   │       │   └── 2_insert_data.sql
│   │   │       └── application.yml
│   │   └── test/
│   │       └── java/
│   │           └── org/
│   │               └── example/
│   │                   └── backend/
│   │                       ├── controller/
│   │                       │   ├── EmailDomainControllerTest.java
│   │                       │   ├── FacultyControllerTest.java
│   │                       │   ├── ProgramControllerTest.java
│   │                       │   └── StudentStatusControllerTest.java
│   │                       ├── service/
│   │                       │   ├── EmailDomainServiceTest.java
│   │                       │   ├── FacultyServiceTest.java
│   │                       │   ├── ProgramServiceTest.java
│   │                       │   └── StudentStatusServiceTest.java
│   │                       ├── validator/
│   │                       │   ├── EmailDomainValidatorTest.java
│   │                       │   └── PhoneNumberValidatorTest.java
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

![image](https://github.com/user-attachments/assets/c67179a9-3ea7-4b8f-84ed-30abaffc9ab0)

Để thêm sinh viên, ấn nút bấm "Thêm sinh viên"

![image](https://github.com/user-attachments/assets/f71057c1-922a-4055-89bf-bcb3077d0a9b)

Để thêm, xóa, sửa các nhãn mới cho Khoa, Chương trình, Trạng thái, vào mục "Quản lý danh muc"

![image](https://github.com/user-attachments/assets/3e906e30-5837-45fd-929a-11931e88275f)

Để nhập xuất dữ liệu, vào mục "Nhập xuất dữ liệu"

![image](https://github.com/user-attachments/assets/4234a42c-4838-4a5b-95e8-2c29eb700916)

Cấu hình Business Rules

![image](https://github.com/user-attachments/assets/9474a02b-5edd-441e-8406-f19527e4ecd2)

## Database

![image](https://github.com/user-attachments/assets/35e25919-bb7a-4a10-a0d9-3ab5cb20ad10)
