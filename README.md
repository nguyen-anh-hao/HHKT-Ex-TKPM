# Dự án Quản lý sinh viên

- Developer Guide: https://hhkt-tkpm.github.io/
- Document: https://deepwiki.com/nguyen-anh-hao/HHKT-Ex-TKPM/1-student-management-system-overview

## Cấu trúc source code
```
.
├── client/
│   ├── messages/
│   │   ├── en.json
│   │   └── vi.json
│   ├── src/
│   │   ├── app/
│   │   │   ├── class-management/
│   │   │   │   ├── actions/
│   │   │   │   │   └── ClassActions.ts
│   │   │   │   ├── components/
│   │   │   │   │   ├── ClassModal.tsx
│   │   │   │   │   ├── ClassTable.tsx
│   │   │   │   │   └── Home.tsx
│   │   │   │   └── page.tsx
│   │   │   ├── course-management/
│   │   │   │   ├── actions/
│   │   │   │   │   └── CourseActions.ts
│   │   │   │   ├── components/
│   │   │   │   │   ├── CourseModal.tsx
│   │   │   │   │   ├── CourseTable.tsx
│   │   │   │   │   └── Home.tsx
│   │   │   │   └── page.tsx
│   │   │   ├── reference-management/
│   │   │   │   ├── components/
│   │   │   │   │   └── ReferenceList.tsx
│   │   │   │   └── page.tsx
│   │   │   ├── register-class/
│   │   │   │   ├── actions/
│   │   │   │   │   └── RegisterActions.ts
│   │   │   │   ├── components/
│   │   │   │   │   ├── Home.tsx
│   │   │   │   │   ├── RegisterModal.tsx
│   │   │   │   │   └── RegisterTable.tsx
│   │   │   │   └── page.tsx
│   │   │   ├── semester-management/
│   │   │   │   ├── components/
│   │   │   │   │   ├── Home.tsx
│   │   │   │   │   └── SemesterModal.tsx
│   │   │   │   └── page.tsx
│   │   │   ├── status-rules-configuration/
│   │   │   │   └── page.tsx
│   │   │   ├── student-management/
│   │   │   │   ├── actions/
│   │   │   │   │   └── StudentActions.ts
│   │   │   │   ├── components/
│   │   │   │   │   ├── ExportModal.tsx
│   │   │   │   │   ├── Home.tsx
│   │   │   │   │   ├── ImportModal.tsx
│   │   │   │   │   ├── StudentModal.tsx
│   │   │   │   │   └── StudentTable.tsx
│   │   │   │   └── page.tsx
│   │   │   ├── transcript/
│   │   │   │   ├── [studentId]/
│   │   │   │   │   └── page.tsx
│   │   │   │   └── page.tsx
│   │   │   ├── favicon.ico
│   │   │   ├── layout.tsx
│   │   │   └── page.tsx
│   │   ├── components/
│   │   │   ├── layout/
│   │   │   │   └── Layout.tsx
│   │   │   └── ui/
│   │   │       ├── LanguageSelector.tsx
│   │   │       └── MessageProvider.tsx
│   │   ├── i18n/
│   │   │   └── request.ts
│   │   ├── interfaces/
│   │   │   ├── class/
│   │   │   │   ├── Class.ts
│   │   │   │   ├── ClassResponse.ts
│   │   │   │   ├── CreateClassRequest.ts
│   │   │   │   └── UpdateClassRequest.ts
│   │   │   ├── common/
│   │   │   │   └── PaginatedResponse.ts
│   │   │   ├── course/
│   │   │   │   ├── Course.ts
│   │   │   │   ├── CourseResponse.ts
│   │   │   │   ├── CreateCourseRequest.ts
│   │   │   │   └── UpdateCourseRequest.ts
│   │   │   ├── register/
│   │   │   │   ├── CreateRegisterRequest.ts
│   │   │   │   └── RegisterResponse.ts
│   │   │   ├── semester/
│   │   │   │   ├── CreateSemesterRequest.ts
│   │   │   │   ├── Semester.ts
│   │   │   │   ├── SemesterResponse.ts
│   │   │   │   └── UpdateSemesterRequest.ts
│   │   │   ├── student/
│   │   │   │   ├── CreateStudentRequest.ts
│   │   │   │   ├── Student.ts
│   │   │   │   ├── StudentResponse.ts
│   │   │   │   └── UpdateStudentRequest.ts
│   │   │   ├── Address.ts
│   │   │   ├── ApiResponse.ts
│   │   │   ├── Identify.ts
│   │   │   └── Transcript.ts
│   │   └── libs/
│   │       ├── api/
│   │       │   ├── api.ts
│   │       │   ├── classApi.ts
│   │       │   ├── courseApi.ts
│   │       │   ├── referenceApi.ts
│   │       │   ├── registerApi.ts
│   │       │   ├── semesterApi.ts
│   │       │   ├── statusRuleApi.ts
│   │       │   ├── studentApi.ts
│   │       │   ├── studentsFileApi.ts
│   │       │   ├── transcriptFileApi.ts
│   │       │   └── translateApi.ts
│   │       ├── hooks/
│   │       │   ├── class/
│   │       │   │   ├── useClassMutation.ts
│   │       │   │   └── useClassQuery.ts
│   │       │   ├── course/
│   │       │   │   ├── useCourseMutation.ts
│   │       │   │   └── useCourseQuery.ts
│   │       │   ├── reference/
│   │       │   │   ├── useReferenceMutation.ts
│   │       │   │   └── useReferences.ts
│   │       │   ├── register/
│   │       │   │   ├── useRegisterMutation.ts
│   │       │   │   └── useRegisterQuery.ts
│   │       │   ├── semester/
│   │       │   │   ├── useSemesterMutation.ts
│   │       │   │   └── useSemesters.ts
│   │       │   ├── statusRule/
│   │       │   │   ├── useStatusRuleMutation.ts
│   │       │   │   └── useStatusRules.ts
│   │       │   ├── student/
│   │       │   │   ├── useStudentMutation.ts
│   │       │   │   └── useStudents.ts
│   │       │   └── transcript/
│   │       │       └── useTransripts.ts
│   │       ├── services/
│   │       │   ├── classService.ts
│   │       │   ├── courseService.ts
│   │       │   ├── referenceService.ts
│   │       │   ├── registerService.ts
│   │       │   ├── semesterService.ts
│   │       │   ├── statusRuleService.ts
│   │       │   ├── studentService.ts
│   │       │   ├── studentsFileService.ts
│   │       │   ├── transcriptFileService.ts
│   │       │   └── transcriptService.ts
│   │       ├── stores/
│   │       │   └── referenceStore.ts
│   │       ├── utils/
│   │       │   ├── transform/
│   │       │   │   ├── courseTransform.ts
│   │       │   │   └── studentTransform.ts
│   │       │   ├── cleanData.ts
│   │       │   ├── errorUtils.ts
│   │       │   ├── messageUtils.ts
│   │       │   └── translate-helper.ts
│   │       └── validators/
│   │           └── studentSchema.ts
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
│   ├── docs/
│   │   ├── Part01.md
│   │   ├── Part02.md
│   │   ├── Part03.md
│   │   ├── Part04.md
│   │   └── Parts.md
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── org/
│   │   │   │       └── example/
│   │   │   │           └── backend/
│   │   │   │               ├── common/
│   │   │   │               │   ├── Auditable.java
│   │   │   │               │   ├── CachingRequestWrapper.java
│   │   │   │               │   ├── CachingResponseWrapper.java
│   │   │   │               │   ├── PaginationInfo.java
│   │   │   │               │   ├── RegistrationStatus.java
│   │   │   │               │   └── TranslationFilter.java
│   │   │   │               ├── config/
│   │   │   │               │   ├── AuditorAwareConfig.java
│   │   │   │               │   ├── CorsConfig.java
│   │   │   │               │   ├── JacksonConfig.java
│   │   │   │               │   ├── OpenAPIConfig.java
│   │   │   │               │   ├── SecurityConfig.java
│   │   │   │               │   ├── StudentStatusRulesConfig.java
│   │   │   │               │   └── WebConfig.java
│   │   │   │               ├── controller/
│   │   │   │               │   ├── ClassController.java
│   │   │   │               │   ├── ClassRegistrationController.java
│   │   │   │               │   ├── ClassRegistrationHistoryController.java
│   │   │   │               │   ├── CourseController.java
│   │   │   │               │   ├── EmailDomainController.java
│   │   │   │               │   ├── FacultyController.java
│   │   │   │               │   ├── FileTransferController.java
│   │   │   │               │   ├── LecturerController.java
│   │   │   │               │   ├── ProgramController.java
│   │   │   │               │   ├── SemesterController.java
│   │   │   │               │   ├── StudentController.java
│   │   │   │               │   ├── StudentStatusController.java
│   │   │   │               │   └── StudentStatusRuleController.java
│   │   │   │               ├── domain/
│   │   │   │               │   ├── Address.java
│   │   │   │               │   ├── Class.java
│   │   │   │               │   ├── ClassRegistration.java
│   │   │   │               │   ├── ClassRegistrationHistory.java
│   │   │   │               │   ├── Course.java
│   │   │   │               │   ├── Document.java
│   │   │   │               │   ├── EmailDomain.java
│   │   │   │               │   ├── Faculty.java
│   │   │   │               │   ├── Lecturer.java
│   │   │   │               │   ├── PhonePattern.java
│   │   │   │               │   ├── Program.java
│   │   │   │               │   ├── Semester.java
│   │   │   │               │   ├── Student.java
│   │   │   │               │   ├── StudentStatus.java
│   │   │   │               │   └── StudentStatusRule.java
│   │   │   │               ├── dto/
│   │   │   │               │   ├── data/
│   │   │   │               │   │   └── TranscriptData.java
│   │   │   │               │   ├── request/
│   │   │   │               │   │   ├── AddressRequest.java
│   │   │   │               │   │   ├── ClassRegistrationHistoryRequest.java
│   │   │   │               │   │   ├── ClassRegistrationRequest.java
│   │   │   │               │   │   ├── ClassRegistrationUpdateRequest.java
│   │   │   │               │   │   ├── ClassRequest.java
│   │   │   │               │   │   ├── CourseRequest.java
│   │   │   │               │   │   ├── CourseUpdateRequest.java
│   │   │   │               │   │   ├── DocumentRequest.java
│   │   │   │               │   │   ├── EmailDomainRequest.java
│   │   │   │               │   │   ├── FacultyRequest.java
│   │   │   │               │   │   ├── LecturerRequest.java
│   │   │   │               │   │   ├── ProgramRequest.java
│   │   │   │               │   │   ├── SemesterRequest.java
│   │   │   │               │   │   ├── StudentRequest.java
│   │   │   │               │   │   ├── StudentStatusRequest.java
│   │   │   │               │   │   ├── StudentStatusRuleRequest.java
│   │   │   │               │   │   ├── StudentUpdateRequest.java
│   │   │   │               │   │   └── TranslateRequest.java
│   │   │   │               │   └── response/
│   │   │   │               │       ├── APIResponse.java
│   │   │   │               │       ├── AddressResponse.java
│   │   │   │               │       ├── ClassRegistrationHistoryResponse.java
│   │   │   │               │       ├── ClassRegistrationResponse.java
│   │   │   │               │       ├── ClassResponse.java
│   │   │   │               │       ├── ClassSummaryResponse.java
│   │   │   │               │       ├── CourseResponse.java
│   │   │   │               │       ├── DocumentResponse.java
│   │   │   │               │       ├── EmailDomainResponse.java
│   │   │   │               │       ├── FacultyResponse.java
│   │   │   │               │       ├── LecturerResponse.java
│   │   │   │               │       ├── ProgramResponse.java
│   │   │   │               │       ├── SemesterResponse.java
│   │   │   │               │       ├── StudentResponse.java
│   │   │   │               │       ├── StudentStatusResponse.java
│   │   │   │               │       ├── StudentStatusRuleResponse.java
│   │   │   │               │       └── TranslateResponse.java
│   │   │   │               ├── mapper/
│   │   │   │               │   ├── AddressMapper.java
│   │   │   │               │   ├── ClassMapper.java
│   │   │   │               │   ├── ClassRegistrationHistoryMapper.java
│   │   │   │               │   ├── ClassRegistrationMapper.java
│   │   │   │               │   ├── CourseMapper.java
│   │   │   │               │   ├── DocumentMapper.java
│   │   │   │               │   ├── EmailDomainMapper.java
│   │   │   │               │   ├── FacultyMapper.java
│   │   │   │               │   ├── LecturerMapper.java
│   │   │   │               │   ├── ProgramMapper.java
│   │   │   │               │   ├── SemesterMapper.java
│   │   │   │               │   ├── StudentMapper.java
│   │   │   │               │   ├── StudentStatusMapper.java
│   │   │   │               │   └── StudentStatusRuleMapper.java
│   │   │   │               ├── repository/
│   │   │   │               │   ├── IAddressRepository.java
│   │   │   │               │   ├── IClassRegistrationHistoryRepository.java
│   │   │   │               │   ├── IClassRegistrationRepository.java
│   │   │   │               │   ├── IClassRepository.java
│   │   │   │               │   ├── ICourseRepository.java
│   │   │   │               │   ├── IDocumentRepository.java
│   │   │   │               │   ├── IEmailDomainRepository.java
│   │   │   │               │   ├── IFacultyRepository.java
│   │   │   │               │   ├── ILecturerRepository.java
│   │   │   │               │   ├── IPhonePatternRepository.java
│   │   │   │               │   ├── IProgramRepository.java
│   │   │   │               │   ├── ISemesterRepository.java
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
│   │   │   │               │   │   ├── TranscriptPdfExportService.java
│   │   │   │               │   │   └── XMLExportService.java
│   │   │   │               │   ├── impl/
│   │   │   │               │   │   ├── ClassRegistrationHistoryServiceImpl.java
│   │   │   │               │   │   ├── ClassRegistrationServiceImpl.java
│   │   │   │               │   │   ├── ClassServiceImpl.java
│   │   │   │               │   │   ├── CourseServiceImpl.java
│   │   │   │               │   │   ├── EmailDomainServiceImpl.java
│   │   │   │               │   │   ├── FacultyServiceImpl.java
│   │   │   │               │   │   ├── LecturerServiceImpl.java
│   │   │   │               │   │   ├── ProgramServiceImpl.java
│   │   │   │               │   │   ├── SemesterServiceImpl.java
│   │   │   │               │   │   ├── StudentServiceImpl.java
│   │   │   │               │   │   ├── StudentStatusRuleServiceImpl.java
│   │   │   │               │   │   ├── StudentStatusServiceImpl.java
│   │   │   │               │   │   └── TranslationServiceImpl.java
│   │   │   │               │   ├── IClassRegistrationHistoryService.java
│   │   │   │               │   ├── IClassRegistrationService.java
│   │   │   │               │   ├── IClassService.java
│   │   │   │               │   ├── ICourseService.java
│   │   │   │               │   ├── IEmailDomainService.java
│   │   │   │               │   ├── IFacultyService.java
│   │   │   │               │   ├── ILecturerService.java
│   │   │   │               │   ├── IProgramService.java
│   │   │   │               │   ├── ISemesterService.java
│   │   │   │               │   ├── IStudentService.java
│   │   │   │               │   ├── IStudentStatusRuleService.java
│   │   │   │               │   ├── IStudentStatusService.java
│   │   │   │               │   └── TranslationService.java
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
│   │   │       │   ├── data_import.json
│   │   │       │   ├── data_import.xml
│   │   │       │   └── student-status-rules.json
│   │   │       ├── db/
│   │   │       │   ├── 1_create_table.sql
│   │   │       │   └── 2_insert_data.sql
│   │   │       ├── font/
│   │   │       │   └── Roboto-Regular.ttf
│   │   │       └── application.yml
│   │   └── test/
│   │       └── java/
│   │           └── org/
│   │               └── example/
│   │                   └── backend/
│   │                       ├── config/
│   │                       │   └── TestConfig.java
│   │                       ├── controller/
│   │                       │   ├── EmailDomainControllerTest.java
│   │                       │   ├── FacultyControllerTest.java
│   │                       │   ├── ProgramControllerTest.java
│   │                       │   ├── StudentControllerTest.java
│   │                       │   └── StudentStatusControllerTest.java
│   │                       ├── service/
│   │                       │   ├── Import/
│   │                       │   │   ├── JSONImportServiceTest.java
│   │                       │   │   └── XMLImportServiceTest.java
│   │                       │   ├── export/
│   │                       │   │   ├── ExcelExportServiceTest.java
│   │                       │   │   ├── TranscriptPdfExportServiceTest.java
│   │                       │   │   └── XMLExportServiceTest.java
│   │                       │   ├── ClassRegistrationHistoryServiceTest.java
│   │                       │   ├── ClassRegistrationServiceTest.java
│   │                       │   ├── ClassServiceTest.java
│   │                       │   ├── CourseServiceTest.java
│   │                       │   ├── EmailDomainServiceTest.java
│   │                       │   ├── FacultyServiceTest.java
│   │                       │   ├── LecturerServiceTest.java
│   │                       │   ├── ProgramServiceTest.java
│   │                       │   ├── SemesterServiceTest.java
│   │                       │   ├── StudentServiceTest.java
│   │                       │   ├── StudentStatusRuleServiceTest.java
│   │                       │   ├── StudentStatusServiceTest.java
│   │                       │   └── TranslationServiceTest.java
│   │                       ├── validator/
│   │                       │   ├── EmailDomainValidatorTest.java
│   │                       │   ├── PhoneNumberValidatorTest.java
│   │                       │   └── StudentStatusTransitionValidatorTest.java
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

**Quản lý sinh viên**

Xem danh sách sinh viên
![image](https://github.com/user-attachments/assets/7c49aea7-720e-4ab6-af53-9a659c9c479b)

Thêm sinh viên
![image](https://github.com/user-attachments/assets/ed90c1e6-12ef-4dfa-ac2b-87a45b70ce29)

Sửa sinh viên
![image](https://github.com/user-attachments/assets/d5bb1e51-0349-413b-80de-ce3f1c72055b)

Xóa sinh viên
![image](https://github.com/user-attachments/assets/e86d72ae-094b-41db-bb7f-a1d892b3b318)

**Quản lý danh mục**

Xem danh mục
![image](https://github.com/user-attachments/assets/c99510ea-0bc7-41e9-9b0d-1d57f602d101)

Thêm danh mục
![image](https://github.com/user-attachments/assets/093b9ecc-b48b-4692-8f01-29e04972b7e8)

Sửa danh mục
![image](https://github.com/user-attachments/assets/e6878692-92eb-4137-a29c-325517982d31)

Xóa danh mục
![image](https://github.com/user-attachments/assets/1e83a0f8-3ad1-4c1e-b473-ec3d5d7cb882)

**Cấu hình trạng thái sinh viên**

![image](https://github.com/user-attachments/assets/ecb1d2de-b217-4b4d-acb6-af4b1d9d7549)

**Quản lý khóa học**

Xem danh sách khóa học
![image](https://github.com/user-attachments/assets/32c2c675-7d9c-4e49-880e-966460b874ed)

Thêm khóa học
![image](https://github.com/user-attachments/assets/8deeb14e-15cf-460a-b1ab-234ca38f3e37)

Sửa khóa học
![image](https://github.com/user-attachments/assets/ed15db50-cb5e-43c0-8755-8f694ac289a5)

Xóa khóa học
![image](https://github.com/user-attachments/assets/48f8724f-e7d6-4999-97ec-699ca8566bf5)

**Quản lý học kỳ**

![image](https://github.com/user-attachments/assets/f29d8089-0f5e-4a3f-877c-d1d76692b248)

**Quản lý lớp học**

![image](https://github.com/user-attachments/assets/a8709d44-06b6-45d6-bdd5-21b234c09d53)

**Đăng ký học phần**

![image](https://github.com/user-attachments/assets/11ac6aea-9672-4c29-9865-3b8c1f3b03fe)

**Bảng điểm sinh viên**

![image](https://github.com/user-attachments/assets/2ebf10f7-994c-4d9c-a3b8-efb3220dcd2b)

**Đa ngôn ngữ**

![image](https://github.com/user-attachments/assets/63d92e68-cc29-4a07-8f74-990f41eacc7d)

## Database

![image](https://github.com/user-attachments/assets/35e25919-bb7a-4a10-a0d9-3ab5cb20ad10)
