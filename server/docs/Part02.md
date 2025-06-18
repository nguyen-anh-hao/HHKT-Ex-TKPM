# Student Management API Documentation - Part 2

## Overview
This is Part 2 of the comprehensive Student Management API documentation, covering Student and Lecturer management. These entities depend on the foundational data from Part 1 (Faculty, Program, Student Status, etc.).

## Base URL
```
http://localhost:9000/api
```

---

## 1. Student Management

### 1.1 Add Student
**POST** `/students`

Creates a new student in the system with complete profile information.

**Request Body:**
```json
{
  "studentId": "SV001",
  "fullName": "Nguyễn Văn A",
  "dob": "2002-05-10",
  "gender": "Nam",
  "intake": "K20",
  "email": "nguyenvana@student.university.edu.vn",
  "phoneCountry": "VN",
  "phone": "+84987654321",
  "nationality": "Việt Nam",
  "facultyId": 1,
  "programId": 1,
  "studentStatusId": 1,
  "addresses": [
    {
      "addressType": "Thường Trú",
      "houseNumberStreetName": "123 Lê Lợi",
      "wardCommune": "Phường 1",
      "district": "Quận 3",
      "cityProvince": "TP.HCM",
      "country": "Việt Nam"
    },
    {
      "addressType": "Tạm Trú",
      "houseNumberStreetName": "456 Trần Hưng Đạo",
      "wardCommune": "Phường 5",
      "district": "Quận 1",
      "cityProvince": "TP.HCM",
      "country": "Việt Nam"
    }
  ],
  "documents": [
    {
      "documentType": "CCCD",
      "documentNumber": "123456789012",
      "issuedDate": "2020-10-01",
      "expiredDate": "2030-10-01",
      "issuedBy": "TP.HCM",
      "issuedCountry": "Việt Nam",
      "note": null,
      "hasChip": true
    }
  ]
}
```

**Response:**
```json
{
  "status": 201,
  "message": "Success",
  "data": {
    "studentId": "SV001",
    "fullName": "Nguyễn Văn A",
    "dob": "2002-05-10",
    "gender": "Nam",
    "faculty": "Khoa Luật",
    "intake": "K20",
    "program": "Đại học chính quy",
    "email": "nguyenvana@student.university.edu.vn",
    "phoneCountry": "VN",
    "phone": "+84987654321",
    "studentStatus": "Đang học",
    "nationality": "Việt Nam",
    "addresses": [
      {
        "addressType": "Thường Trú",
        "houseNumberStreetName": "123 Lê Lợi",
        "wardCommune": "Phường 1",
        "district": "Quận 3",
        "cityProvince": "TP.HCM",
        "country": "Việt Nam"
      }
    ],
    "documents": [
      {
        "documentType": "CCCD",
        "documentNumber": "123456789012",
        "issuedDate": "2020-10-01",
        "expiredDate": "2030-10-01",
        "issuedBy": "TP.HCM",
        "issuedCountry": "Việt Nam",
        "note": null,
        "hasChip": true
      }
    ],
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T10:30:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

### 1.2 Get Student by ID
**GET** `/students/{studentId}`

Retrieves a specific student by their student ID.

**Path Parameters:**
- `studentId` (string, required): Student ID (e.g., "SV001")

**Response:**
```json
{
  "status": 200,
  "message": "Success",
  "data": {
    "studentId": "SV001",
    "fullName": "Nguyễn Văn A",
    "dob": "2002-05-10",
    "gender": "Nam",
    "faculty": "Khoa Luật",
    "intake": "K20",
    "program": "Đại học chính quy",
    "email": "nguyenvana@student.university.edu.vn",
    "phoneCountry": "VN",
    "phone": "+84987654321",
    "studentStatus": "Đang học",
    "nationality": "Việt Nam",
    "addresses": [
      {
        "addressType": "Thường Trú",
        "houseNumberStreetName": "123 Lê Lợi",
        "wardCommune": "Phường 1",
        "district": "Quận 3",
        "cityProvince": "TP.HCM",
        "country": "Việt Nam"
      }
    ],
    "documents": [
      {
        "documentType": "CCCD",
        "documentNumber": "123456789012",
        "issuedDate": "2020-10-01",
        "expiredDate": "2030-10-01",
        "issuedBy": "TP.HCM",
        "issuedCountry": "Việt Nam",
        "note": null,
        "hasChip": true
      }
    ],
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T10:30:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

### 1.3 Get All Students
**GET** `/students`

Retrieves all students with pagination support.

**Query Parameters:**
- `page` (integer, optional, default: 0): Page number
- `size` (integer, optional, default: 3): Page size

**Response:**
```json
{
  "status": 200,
  "message": "Success",
  "data": [
    {
      "studentId": "SV001",
      "fullName": "Nguyễn Văn A",
      "dob": "2002-05-10",
      "gender": "Nam",
      "faculty": "Khoa Luật",
      "intake": "K20",
      "program": "Đại học chính quy",
      "email": "nguyenvana@student.university.edu.vn",
      "phoneCountry": "VN",
      "phone": "+84987654321",
      "studentStatus": "Đang học",
      "nationality": "Việt Nam",
      "addresses": [],
      "documents": [],
      "createdAt": "2025-06-05T10:30:00",
      "updatedAt": "2025-06-05T10:30:00",
      "createdBy": "admin",
      "updatedBy": "admin"
    }
  ],
  "paginationInfo": {
    "page": 0,
    "size": 3,
    "totalElements": 20,
    "totalPages": 7,
    "hasNext": true,
    "hasPrevious": false
  }
}
```

### 1.4 Update Student
**PATCH** `/students/{studentId}`

Updates an existing student's information. Only provided fields will be updated.

**Path Parameters:**
- `studentId` (string, required): Student ID

**Request Body:**
```json
{
  "studentId": "SV001",
  "fullName": "Nguyễn Văn A Updated",
  "email": "newemail@student.university.edu.vn",
  "phoneCountry": "VN",
  "phone": "+84987654322",
  "studentStatusId": 2,
  "addresses": [
    {
      "addressType": "Thường Trú",
      "houseNumberStreetName": "789 Nguyễn Huệ",
      "wardCommune": "Phường 2",
      "district": "Quận 1",
      "cityProvince": "TP.HCM",
      "country": "Việt Nam"
    }
  ]
}
```

**Response:**
```json
{
  "status": 200,
  "message": "Success",
  "data": {
    "studentId": "SV001",
    "fullName": "Nguyễn Văn A Updated",
    "dob": "2002-05-10",
    "gender": "Nam",
    "faculty": "Khoa Luật",
    "intake": "K20",
    "program": "Đại học chính quy",
    "email": "newemail@student.university.edu.vn",
    "phoneCountry": "VN",
    "phone": "+84987654322",
    "studentStatus": "Tạm dừng học",
    "nationality": "Việt Nam",
    "addresses": [
      {
        "addressType": "Thường Trú",
        "houseNumberStreetName": "789 Nguyễn Huệ",
        "wardCommune": "Phường 2",
        "district": "Quận 1",
        "cityProvince": "TP.HCM",
        "country": "Việt Nam"
      }
    ],
    "documents": [],
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T11:45:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

### 1.5 Delete Student
**DELETE** `/students/{studentId}`

Deletes a student from the system.

**Path Parameters:**
- `studentId` (string, required): Student ID

**Response:**
```json
{
  "status": 200,
  "message": "Successfully deleted student with ID: SV001"
}
```

### 1.6 Search Students
**GET** `/students/search`

Searches for students by keyword in name or student ID.

**Query Parameters:**
- `keyword` (string, required): Search keyword
- `page` (integer, optional, default: 0): Page number
- `size` (integer, optional, default: 3): Page size

**Example Request:**
```
GET /students/search?keyword=Nguyễn&page=0&size=5
```

**Response:**
```json
{
  "status": 200,
  "message": "Success",
  "data": [
    {
      "studentId": "SV001",
      "fullName": "Nguyễn Văn A",
      "dob": "2002-05-10",
      "gender": "Nam",
      "faculty": "Khoa Luật",
      "intake": "K20",
      "program": "Đại học chính quy",
      "email": "nguyenvana@student.university.edu.vn",
      "phoneCountry": "VN",
      "phone": "+84987654321",
      "studentStatus": "Đang học",
      "nationality": "Việt Nam",
      "addresses": [],
      "documents": [],
      "createdAt": "2025-06-05T10:30:00",
      "updatedAt": "2025-06-05T10:30:00",
      "createdBy": "admin",
      "updatedBy": "admin"
    }
  ],
  "paginationInfo": {
    "page": 0,
    "size": 5,
    "totalElements": 3,
    "totalPages": 1,
    "hasNext": false,
    "hasPrevious": false
  }
}
```

### 1.7 Get Student Transcript
**GET** `/students/{studentId}/transcript`

Generates and returns a PDF transcript for the specified student.

**Path Parameters:**
- `studentId` (string, required): Student ID

**Response:**
- **Content-Type:** `application/pdf`
- **Content-Disposition:** `inline; filename="transcript_SV001.pdf"`

**Success Response:**
Returns PDF file as binary data with appropriate headers.

**Error Response:**
```json
{
  "status": 404,
  "message": "Student not found"
}
```

---

## 2. Lecturer Management

### 2.1 Add Lecturer
**POST** `/lecturers`

Creates a new lecturer in the system.

**Request Body:**
```json
{
  "fullName": "TS. Nguyễn Văn A",
  "email": "nguyenvana@university.edu",
  "phone": "0912345678",
  "facultyId": 1
}
```

**Response:**
```json
{
  "status": 201,
  "message": "Success",
  "data": {
    "id": 1,
    "fullName": "TS. Nguyễn Văn A",
    "email": "nguyenvana@university.edu",
    "phone": "0912345678",
    "facultyId": 1,
    "facultyName": "Khoa Luật",
    "classes": [],
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T10:30:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

### 2.2 Get All Lecturers
**GET** `/lecturers`

Retrieves all lecturers with pagination support.

**Query Parameters:**
- `page` (integer, optional, default: 0): Page number
- `size` (integer, optional, default: 3): Page size

**Response:**
```json
{
  "status": 200,
  "message": "Success",
  "data": [
    {
      "id": 1,
      "fullName": "TS. Nguyễn Văn A",
      "email": "nguyenvana@university.edu",
      "phone": "0912345678",
      "facultyId": 1,
      "facultyName": "Khoa Luật",
      "classes": [
        {
          "classCode": "CS101-01",
          "maxStudents": 50,
          "schedule": "Thứ Hai - Thứ Tư 10:00-12:00",
          "room": "A101"
        }
      ],
      "createdAt": "2025-06-05T10:30:00",
      "updatedAt": "2025-06-05T10:30:00",
      "createdBy": "admin",
      "updatedBy": "admin"
    }
  ],
  "paginationInfo": {
    "page": 0,
    "size": 3,
    "totalElements": 10,
    "totalPages": 4,
    "hasNext": true,
    "hasPrevious": false
  }
}
```

### 2.3 Get Lecturer by ID
**GET** `/lecturers/{lecturerId}`

Retrieves a specific lecturer by their ID.

**Path Parameters:**
- `lecturerId` (integer, required): Lecturer ID

**Response:**
```json
{
  "status": 200,
  "message": "Success",
  "data": {
    "id": 1,
    "fullName": "TS. Nguyễn Văn A",
    "email": "nguyenvana@university.edu",
    "phone": "0912345678",
    "facultyId": 1,
    "facultyName": "Khoa Luật",
    "classes": [
      {
        "classCode": "CS101-01",
        "maxStudents": 50,
        "schedule": "Thứ Hai - Thứ Tư 10:00-12:00",
        "room": "A101"
      },
      {
        "classCode": "CS102-01",
        "maxStudents": 40,
        "schedule": "Thứ Ba - Thứ Năm 14:00-16:00",
        "room": "B201"
      }
    ],
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T10:30:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

---

## 3. File Transfer Operations

### 3.1 Export Students Data
**GET** `/file-transfer/export`

Exports student data to various file formats (Excel, CSV, PDF).

**Query Parameters:**
- `type` (string, required): Export format ("xlsx", "csv", "pdf")
- `fileName` (string, optional): Custom filename (default: "data_export")
- `page` (integer, optional, default: 0): Page number for data to export
- `size` (integer, optional, default: 20): Number of records to export

**Example Request:**
```
GET /file-transfer/export?type=xlsx&fileName=students_export&size=50
```

**Response:**
- **Content-Type:** Varies by type (application/vnd.openxmlformats-officedocument.spreadsheetml.sheet for xlsx)
- **Content-Disposition:** `attachment; filename="students_export.xlsx"`

Returns the file as binary data.

**Error Response:**
```json
{
  "status": 400,
  "message": "Invalid file type"
}
```

### 3.2 Import Students Data
**POST** `/file-transfer/import`

Imports student data from uploaded file (Excel, CSV).

**Query Parameters:**
- `type` (string, required): File format ("xlsx", "csv")

**Request Body:**
- **Content-Type:** `multipart/form-data`
- **file:** File to upload

**Example Request:**
```
POST /file-transfer/import?type=xlsx
Content-Type: multipart/form-data

file: [binary file data]
```

**Response:**
```json
{
  "status": 200,
  "message": "Success",
  "data": [
    {
      "studentId": "SV021",
      "fullName": "Imported Student 1",
      "dob": "2002-01-01",
      "gender": "Nam",
      "faculty": "Khoa Luật",
      "intake": "K20",
      "program": "Đại học chính quy",
      "email": "imported1@student.university.edu.vn",
      "phoneCountry": "VN",
      "phone": "+84987654321",
      "studentStatus": "Đang học",
      "nationality": "Việt Nam",
      "addresses": [],
      "documents": [],
      "createdAt": "2025-06-05T12:00:00",
      "updatedAt": "2025-06-05T12:00:00",
      "createdBy": "admin",
      "updatedBy": "admin"
    }
  ]
}
```

**Error Response:**
```json
{
  "status": 400,
  "message": "Invalid file type"
}
```

---

## 4. Translation Test (Utility)

### 4.1 Test Text Translation
**POST** `/test-translate/text`

Tests text translation functionality (Vietnamese ↔ English).

**Request Body:**
```json
{
  "text": "Xin chào thế giới",
  "from": "vi",
  "to": "en"
}
```

**Response:**
```json
"Hello world"
```

### 4.2 Test Object Translation
**POST** `/test-translate/object`

Tests object translation functionality.

**Request Body:**
```json
{
  "name": "Nguyễn Văn A",
  "description": "Sinh viên xuất sắc",
  "faculty": "Khoa Công nghệ Thông tin"
}
```

**Response:**
```json
{
  "name": "Nguyen Van A",
  "description": "Excellent student",
  "faculty": "Faculty of Information Technology"
}
```

---

## Validation Rules

### Student Validation
- **studentId**: Required, unique
- **fullName**: Required, not blank
- **dob**: Required, valid date
- **gender**: Required, not blank
- **email**: Required, valid email format, must match allowed domain
- **phone**: Must match country-specific phone pattern
- **phoneCountry**: Required when phone is provided
- **facultyId**: Required, must exist
- **programId**: Required, must exist
- **studentStatusId**: Required, must exist
- **studentStatusTransition**: Must follow allowed transition rules

### Lecturer Validation
- **fullName**: Required, not blank
- **email**: Required, valid email format
- **phone**: Required, not blank
- **facultyId**: Required, must exist

### Phone Number Validation
The system validates phone numbers based on country-specific patterns:
- **VN**: `+84xxxxxxxxx` (10 digits after country code)
- **US/CA**: `+1xxxxxxxxxx` (10 digits after country code)
- **UK**: `+44xxxxxxxxxx` (10 digits after country code)
- And other international formats...

---

## Error Handling

### Student Not Found (404)
```json
{
  "status": 404,
  "message": "Student not found"
}
```

### Validation Error (400)
```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": [
    {
      "field": "email",
      "message": "Email domain is not allowed"
    },
    {
      "field": "phone",
      "message": "Phone number format is invalid for country VN"
    }
  ]
}
```

### Status Transition Error (400)
```json
{
  "status": 400,
  "message": "Invalid status transition from 'Đã tốt nghiệp' to 'Đang học'"
}
```

---

## Notes
- Student IDs are strings (e.g., "SV001", "SV002")
- Lecturer IDs are auto-generated integers
- Phone numbers must include country code and match country-specific patterns
- Email domains must be pre-registered in the allowed domains list
- Student status transitions must follow predefined rules
- File import/export supports Excel (.xlsx) and CSV formats
- PDF transcripts are generated on-demand
- All dates are in ISO 8601 format (YYYY-MM-DD)
- This is Part 2 covering Student and Lecturer management plus File Transfer operations
- Part 3 will cover Course and Semester management
- Part 4 will cover Class, Registration, and Class Registration History operations