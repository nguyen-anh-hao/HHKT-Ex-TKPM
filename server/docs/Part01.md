# Student Management API Documentation - Part 1

## Overview
This is Part 1 of the comprehensive Student Management API documentation, covering the foundational entities that other parts of the system depend on. This includes core lookup tables and basic administrative data.

## Base URL
```
http://localhost:9000/api
```

## Authentication
All endpoints require appropriate authentication headers (implementation details to be defined).

---

## 1. Faculty Management

### 1.1 Add Faculty
**POST** `/faculties`

Creates a new faculty in the system.

**Request Body:**
```json
{
  "facultyName": "string (required)"
}
```

**Response:**
```json
{
  "status": 201,
  "message": "Success",
  "data": {
    "id": 1,
    "facultyName": "Khoa Công nghệ Thông tin",
    "students": [],
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T10:30:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

### 1.2 Get All Faculties
**GET** `/faculties`

Retrieves all faculties in the system.

**Response:**
```json
{
  "status": 200,
  "message": "Success",
  "data": [
    {
      "id": 1,
      "facultyName": "Khoa Luật",
      "students": [],
      "createdAt": "2025-06-05T10:30:00",
      "updatedAt": "2025-06-05T10:30:00",
      "createdBy": "admin",
      "updatedBy": "admin"
    }
  ]
}
```

### 1.3 Get Faculty by ID
**GET** `/faculties/{id}`

Retrieves a specific faculty by its ID.

**Path Parameters:**
- `id` (integer, required): Faculty ID

**Response:**
```json
{
  "status": 200,
  "message": "Success",
  "data": {
    "id": 1,
    "facultyName": "Khoa Luật",
    "students": [],
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T10:30:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

### 1.4 Update Faculty
**PUT** `/faculties/{id}`

Updates an existing faculty.

**Path Parameters:**
- `id` (integer, required): Faculty ID

**Request Body:**
```json
{
  "facultyName": "string (required)"
}
```

**Response:**
```json
{
  "status": 200,
  "message": "Success",
  "data": {
    "id": 1,
    "facultyName": "Updated Faculty Name",
    "students": [],
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T11:45:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

### 1.5 Delete Faculty
**DELETE** `/faculties/{id}`

Deletes a faculty from the system.

**Path Parameters:**
- `id` (integer, required): Faculty ID

**Response:**
```json
{
  "status": 200,
  "message": "Success"
}
```

---

## 2. Program Management

### 2.1 Add Program
**POST** `/programs`

Creates a new academic program.

**Request Body:**
```json
{
  "programName": "string (required)"
}
```

**Response:**
```json
{
  "status": 201,
  "message": "Success",
  "data": {
    "id": 1,
    "programName": "Đại học chính quy",
    "students": [],
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T10:30:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

### 2.2 Get All Programs
**GET** `/programs`

Retrieves all academic programs.

**Response:**
```json
{
  "status": 200,
  "message": "Success",
  "data": [
    {
      "id": 1,
      "programName": "Đại học chính quy",
      "students": [],
      "createdAt": "2025-06-05T10:30:00",
      "updatedAt": "2025-06-05T10:30:00",
      "createdBy": "admin",
      "updatedBy": "admin"
    }
  ]
}
```

### 2.3 Get Program by ID
**GET** `/programs/{id}`

Retrieves a specific program by its ID.

**Path Parameters:**
- `id` (integer, required): Program ID

**Response:**
```json
{
  "status": 200,
  "message": "Success",
  "data": {
    "id": 1,
    "programName": "Đại học chính quy",
    "students": [],
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T10:30:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

### 2.4 Update Program
**PUT** `/programs/{id}`

Updates an existing program.

**Path Parameters:**
- `id` (integer, required): Program ID

**Request Body:**
```json
{
  "programName": "string (required)"
}
```

**Response:**
```json
{
  "status": 200,
  "message": "Success",
  "data": {
    "id": 1,
    "programName": "Updated Program Name",
    "students": [],
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T11:45:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

### 2.5 Delete Program
**DELETE** `/programs/{id}`

Deletes a program from the system.

**Path Parameters:**
- `id` (integer, required): Program ID

**Response:**
```json
{
  "status": 200,
  "message": "Success"
}
```

---

## 3. Student Status Management

### 3.1 Add Student Status
**POST** `/student-statuses`

Creates a new student status.

**Request Body:**
```json
{
  "studentStatusName": "string (required)"
}
```

**Response:**
```json
{
  "status": 201,
  "message": "Success",
  "data": {
    "id": 1,
    "studentStatusName": "Đang học",
    "students": [],
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T10:30:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

### 3.2 Get All Student Statuses
**GET** `/student-statuses`

Retrieves all student statuses.

**Response:**
```json
{
  "status": 200,
  "message": "Success",
  "data": [
    {
      "id": 1,
      "studentStatusName": "Đang học",
      "students": [],
      "createdAt": "2025-06-05T10:30:00",
      "updatedAt": "2025-06-05T10:30:00",
      "createdBy": "admin",
      "updatedBy": "admin"
    }
  ]
}
```

### 3.3 Get Student Status by ID
**GET** `/student-statuses/{id}`

Retrieves a specific student status by its ID.

**Path Parameters:**
- `id` (integer, required): Student Status ID

**Response:**
```json
{
  "status": 200,
  "message": "Success",
  "data": {
    "id": 1,
    "studentStatusName": "Đang học",
    "students": [],
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T10:30:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

### 3.4 Update Student Status
**PUT** `/student-statuses/{id}`

Updates an existing student status.

**Path Parameters:**
- `id` (integer, required): Student Status ID

**Request Body:**
```json
{
  "studentStatusName": "string (required)"
}
```

**Response:**
```json
{
  "status": 200,
  "message": "Success",
  "data": {
    "id": 1,
    "studentStatusName": "Updated Status Name",
    "students": [],
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T11:45:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

### 3.5 Delete Student Status
**DELETE** `/student-statuses/{id}`

Deletes a student status from the system.

**Path Parameters:**
- `id` (integer, required): Student Status ID

**Response:**
```json
{
  "status": 200,
  "message": "Success"
}
```

---

## 4. Email Domain Management

### 4.1 Add Email Domain
**POST** `/email-domains`

Creates a new allowed email domain.

**Request Body:**
```json
{
  "domain": "string (required, must match pattern: ^[a-zA-Z0-9][a-zA-Z0-9-]*(\\.[a-zA-Z0-9][a-zA-Z0-9-]*)+$)"
}
```

**Response:**
```json
{
  "status": 201,
  "message": "Success",
  "data": {
    "id": 1,
    "domain": "student.university.edu.vn",
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T10:30:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

### 4.2 Get All Email Domains
**GET** `/email-domains`

Retrieves all allowed email domains.

**Response:**
```json
{
  "status": 200,
  "message": "Success",
  "data": [
    {
      "id": 1,
      "domain": "student.university.edu.vn",
      "createdAt": "2025-06-05T10:30:00",
      "updatedAt": "2025-06-05T10:30:00",
      "createdBy": "admin",
      "updatedBy": "admin"
    }
  ]
}
```

### 4.3 Get Email Domain by ID
**GET** `/email-domains/{id}`

Retrieves a specific email domain by its ID.

**Path Parameters:**
- `id` (integer, required): Email Domain ID

**Response:**
```json
{
  "status": 200,
  "message": "Success",
  "data": {
    "id": 1,
    "domain": "student.university.edu.vn",
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T10:30:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

### 4.4 Update Email Domain
**PUT** `/email-domains/{id}`

Updates an existing email domain.

**Path Parameters:**
- `id` (integer, required): Email Domain ID

**Request Body:**
```json
{
  "domain": "string (required, must match domain pattern)"
}
```

**Response:**
```json
{
  "status": 200,
  "message": "Success",
  "data": {
    "id": 1,
    "domain": "updated.domain.com",
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T11:45:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

### 4.5 Delete Email Domain
**DELETE** `/email-domains/{id}`

Deletes an email domain from the allowed list.

**Path Parameters:**
- `id` (integer, required): Email Domain ID

**Response:**
```json
{
  "status": 200,
  "message": "Success"
}
```

---

## 5. Student Status Rules Management

### 5.1 Add Student Status Rule
**POST** `/student-status-rules`

Creates a new rule defining allowed student status transitions.

**Request Body:**
```json
{
  "currentStatusId": "integer (required)",
  "allowedTransitionId": "integer (required)"
}
```

**Response:**
```json
{
  "status": 201,
  "message": "Success",
  "data": {
    "id": 1,
    "currentStatusName": "Đang học",
    "allowedTransitionName": "Tạm dừng học",
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T10:30:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

### 5.2 Get All Student Status Rules
**GET** `/student-status-rules`

Retrieves all student status transition rules with pagination.

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
      "currentStatusName": "Đang học",
      "allowedTransitionName": "Tạm dừng học",
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

### 5.3 Get Student Status Rule by ID
**GET** `/student-status-rules/{id}`

Retrieves a specific student status rule by its ID.

**Path Parameters:**
- `id` (integer, required): Student Status Rule ID

**Response:**
```json
{
  "status": 200,
  "message": "Success",
  "data": {
    "id": 1,
    "currentStatusName": "Đang học",
    "allowedTransitionName": "Tạm dừng học",
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T10:30:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

### 5.4 Update Student Status Rule
**PUT** `/student-status-rules/{id}`

Updates an existing student status rule.

**Path Parameters:**
- `id` (integer, required): Student Status Rule ID

**Request Body:**
```json
{
  "currentStatusId": "integer (required)",
  "allowedTransitionId": "integer (required)"
}
```

**Response:**
```json
{
  "status": 200,
  "message": "Success",
  "data": {
    "id": 1,
    "currentStatusName": "Đang học",
    "allowedTransitionName": "Đã tốt nghiệp",
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T11:45:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

### 5.5 Delete Student Status Rule
**DELETE** `/student-status-rules/{id}`

Deletes a student status rule from the system.

**Path Parameters:**
- `id` (integer, required): Student Status Rule ID

**Response:**
```json
{
  "status": 200,
  "message": "Success"
}
```

**Error Response (Rule not found):**
```json
{
  "status": 404,
  "message": "Student status rule not found"
}
```

---

## Common Error Responses

### Validation Error (400)
```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": [
    {
      "field": "facultyName",
      "message": "Faculty name is required"
    }
  ]
}
```

### Not Found (404)
```json
{
  "status": 404,
  "message": "Resource not found"
}
```

### Internal Server Error (500)
```json
{
  "status": 500,
  "message": "Internal server error"
}
```

---

## Notes
- All timestamps are in ISO 8601 format
- IDs are auto-generated integers
- This is Part 1 covering foundational entities (Faculty, Program, Student Status, Email Domain, Student Status Rules)
- Part 2 will cover Student and Lecturer management
- Part 3 will cover Course and Semester management  
- Part 4 will cover Class, Registration, and File Transfer operations