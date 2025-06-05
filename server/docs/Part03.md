# Student Management API Documentation - Part 3

## Overview
This is Part 3 of the comprehensive Student Management API documentation, covering Course and Semester management. These entities form the academic structure that enables class creation and student registration management.

## Base URL
```
http://localhost:9000/api
```

---

## 1. Course Management

### 1.1 Add Course
**POST** `/courses`

Creates a new course in the system.

**Request Body:**
```json
{
  "courseCode": "CS101",
  "courseName": "Nhập môn Lập trình",
  "credits": 3,
  "description": "Các khái niệm lập trình cơ bản",
  "isActive": true,
  "facultyId": 1,
  "prerequisiteCourseId": null
}
```

**Validation Rules:**
- `courseCode`: Required, not blank
- `courseName`: Required, not blank
- `credits`: Required, minimum value 2
- `facultyId`: Required, must exist
- `prerequisiteCourseId`: Optional, must exist if provided
- `isActive`: Optional, defaults to true

**Response:**
```json
{
  "status": 201,
  "message": "Success",
  "data": {
    "courseId": 1,
    "courseCode": "CS101",
    "courseName": "Nhập môn Lập trình",
    "credits": 3,
    "description": "Các khái niệm lập trình cơ bản",
    "isActive": true,
    "facultyId": 1,
    "facultyName": "Khoa Luật",
    "prerequisiteCourseId": null,
    "prerequisiteCourseName": null,
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T10:30:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

### 1.2 Get All Courses
**GET** `/courses`

Retrieves all courses with pagination support.

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
      "courseId": 1,
      "courseCode": "CS101",
      "courseName": "Nhập môn Lập trình",
      "credits": 3,
      "description": "Các khái niệm lập trình cơ bản",
      "isActive": true,
      "facultyId": 1,
      "facultyName": "Khoa Luật",
      "prerequisiteCourseId": null,
      "prerequisiteCourseName": null,
      "createdAt": "2025-06-05T10:30:00",
      "updatedAt": "2025-06-05T10:30:00",
      "createdBy": "admin",
      "updatedBy": "admin"
    },
    {
      "courseId": 2,
      "courseCode": "CS102",
      "courseName": "Cấu trúc Dữ liệu",
      "credits": 3,
      "description": "Nghiên cứu về cấu trúc dữ liệu",
      "isActive": true,
      "facultyId": 1,
      "facultyName": "Khoa Luật",
      "prerequisiteCourseId": 1,
      "prerequisiteCourseName": "Nhập môn Lập trình",
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

### 1.3 Get Course by ID
**GET** `/courses/{courseId}`

Retrieves a specific course by its ID.

**Path Parameters:**
- `courseId` (integer, required): Course ID

**Response:**
```json
{
  "status": 200,
  "message": "Success",
  "data": {
    "courseId": 1,
    "courseCode": "CS101",
    "courseName": "Nhập môn Lập trình",
    "credits": 3,
    "description": "Các khái niệm lập trình cơ bản",
    "isActive": true,
    "facultyId": 1,
    "facultyName": "Khoa Luật",
    "prerequisiteCourseId": null,
    "prerequisiteCourseName": null,
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T10:30:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

### 1.4 Update Course
**PATCH** `/courses/{courseId}`

Updates an existing course. Only provided fields will be updated.

**Path Parameters:**
- `courseId` (integer, required): Course ID

**Request Body:**
```json
{
  "courseName": "Nhập môn Lập trình Nâng cao",
  "credits": 4,
  "description": "Các khái niệm lập trình nâng cao và ứng dụng thực tế",
  "facultyId": 2
}
```

**Response:**
```json
{
  "status": 200,
  "message": "Success",
  "data": {
    "courseId": 1,
    "courseCode": "CS101",
    "courseName": "Nhập môn Lập trình Nâng cao",
    "credits": 4,
    "description": "Các khái niệm lập trình nâng cao và ứng dụng thực tế",
    "isActive": true,
    "facultyId": 2,
    "facultyName": "Khoa Tiếng Anh thương mại",
    "prerequisiteCourseId": null,
    "prerequisiteCourseName": null,
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T11:45:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

### 1.5 Delete Course
**DELETE** `/courses/{courseId}`

Deletes a course from the system. Returns the deleted course information.

**Path Parameters:**
- `courseId` (integer, required): Course ID

**Response:**
```json
{
  "status": 200,
  "message": "Successfully deleted course",
  "data": {
    "courseId": 1,
    "courseCode": "CS101",
    "courseName": "Nhập môn Lập trình",
    "credits": 3,
    "description": "Các khái niệm lập trình cơ bản",
    "isActive": true,
    "facultyId": 1,
    "facultyName": "Khoa Luật",
    "prerequisiteCourseId": null,
    "prerequisiteCourseName": null,
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T10:30:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

---

## 2. Semester Management

### 2.1 Add Semester
**POST** `/semesters`

Creates a new semester in the academic system.

**Request Body:**
```json
{
  "semester": 1,
  "startDate": "2025-09-03",
  "endDate": "2025-12-20",
  "academicYear": "2025-2026",
  "lastCancelDate": "2025-10-10"
}
```

**Validation Rules:**
- `semester`: Required, must be between 1 and 3
- `startDate`: Required, valid date
- `endDate`: Required, valid date
- `academicYear`: Required, must match pattern "YYYY-YYYY"
- `lastCancelDate`: Required, valid date
- **Business Rules:**
  - `startDate` must be before `lastCancelDate`
  - `lastCancelDate` must be before `endDate`
  - Start and end dates must fall within the specified academic year
  - Academic year format must be consecutive years (e.g., "2025-2026")

**Response:**
```json
{
  "status": 201,
  "message": "Semester added successfully",
  "data": {
    "id": 1,
    "semester": 1,
    "startDate": "2025-09-03",
    "endDate": "2025-12-20",
    "academicYear": "2025-2026",
    "lastCancelDate": "2025-10-10",
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T10:30:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

### 2.2 Get All Semesters
**GET** `/semesters`

Retrieves all semesters with pagination support.

**Query Parameters:**
- `page` (integer, optional, default: 0): Page number
- `size` (integer, optional, default: 10): Page size

**Response:**
```json
{
  "status": 200,
  "message": "Semesters fetched successfully",
  "data": [
    {
      "id": 1,
      "semester": 1,
      "startDate": "2025-09-03",
      "endDate": "2025-12-20",
      "academicYear": "2025-2026",
      "lastCancelDate": "2025-10-10",
      "createdAt": "2025-06-05T10:30:00",
      "updatedAt": "2025-06-05T10:30:00",
      "createdBy": "admin",
      "updatedBy": "admin"
    },
    {
      "id": 2,
      "semester": 2,
      "startDate": "2026-01-12",
      "endDate": "2026-04-25",
      "academicYear": "2025-2026",
      "lastCancelDate": "2026-02-22",
      "createdAt": "2025-06-05T10:30:00",
      "updatedAt": "2025-06-05T10:30:00",
      "createdBy": "admin",
      "updatedBy": "admin"
    },
    {
      "id": 3,
      "semester": 3,
      "startDate": "2026-06-07",
      "endDate": "2026-08-01",
      "academicYear": "2025-2026",
      "lastCancelDate": "2026-06-22",
      "createdAt": "2025-06-05T10:30:00",
      "updatedAt": "2025-06-05T10:30:00",
      "createdBy": "admin",
      "updatedBy": "admin"
    }
  ],
  "paginationInfo": {
    "page": 0,
    "size": 10,
    "totalElements": 10,
    "totalPages": 1,
    "hasNext": false,
    "hasPrevious": false
  }
}
```

### 2.3 Get Semester by ID
**GET** `/semesters/{semesterId}`

Retrieves a specific semester by its ID.

**Path Parameters:**
- `semesterId` (integer, required): Semester ID

**Response:**
```json
{
  "status": 200,
  "message": "Semester fetched successfully",
  "data": {
    "id": 1,
    "semester": 1,
    "startDate": "2025-09-03",
    "endDate": "2025-12-20",
    "academicYear": "2025-2026",
    "lastCancelDate": "2025-10-10",
    "createdAt": "2025-06-05T10:30:00",
    "updatedAt": "2025-06-05T10:30:00",
    "createdBy": "admin",
    "updatedBy": "admin"
  }
}
```

---

## 3. Academic Structure Examples

### 3.1 Typical Academic Year Structure
A complete academic year typically consists of 3 semesters:

```json
{
  "academicYear": "2025-2026",
  "semesters": [
    {
      "semester": 1,
      "name": "Fall Semester",
      "startDate": "2025-09-03",
      "endDate": "2025-12-20",
      "lastCancelDate": "2025-10-10"
    },
    {
      "semester": 2,
      "name": "Spring Semester", 
      "startDate": "2026-01-12",
      "endDate": "2026-04-25",
      "lastCancelDate": "2026-02-22"
    },
    {
      "semester": 3,
      "name": "Summer Semester",
      "startDate": "2026-06-07",
      "endDate": "2026-08-01",
      "lastCancelDate": "2026-06-22"
    }
  ]
}
```

### 3.2 Course Prerequisite Chain Example
Courses can have prerequisite relationships:

```json
{
  "prerequisiteChain": [
    {
      "courseCode": "CS101",
      "courseName": "Nhập môn Lập trình",
      "prerequisiteCourseId": null,
      "description": "Entry-level course"
    },
    {
      "courseCode": "CS102", 
      "courseName": "Cấu trúc Dữ liệu",
      "prerequisiteCourseId": 1,
      "prerequisiteCourseName": "Nhập môn Lập trình",
      "description": "Requires CS101"
    },
    {
      "courseCode": "CS103",
      "courseName": "Thuật toán", 
      "prerequisiteCourseId": 2,
      "prerequisiteCourseName": "Cấu trúc Dữ liệu",
      "description": "Requires CS102"
    }
  ]
}
```

### 3.3 Faculty Course Distribution
Courses are distributed across different faculties:

```json
{
  "facultyCourses": [
    {
      "facultyName": "Khoa Luật",
      "courses": [
        "CS101 - Nhập môn Lập trình",
        "CS102 - Cấu trúc Dữ liệu",
        "CS106 - Hệ điều hành"
      ]
    },
    {
      "facultyName": "Khoa Tiếng Anh thương mại", 
      "courses": [
        "CS104 - Hệ quản trị Cơ sở dữ liệu",
        "CS107 - Kỹ thuật Phần mềm"
      ]
    },
    {
      "facultyName": "Khoa Tiếng Nhật",
      "courses": [
        "CS105 - Mạng Máy tính",
        "CS108 - Trí tuệ Nhân tạo",
        "CS109 - An ninh Mạng"
      ]
    }
  ]
}
```

---

## 4. Business Rules and Constraints

### 4.1 Course Management Rules
- **Course Code**: Must be unique across the system
- **Credits**: Minimum 2 credits required for any course
- **Faculty Assignment**: Every course must belong to a faculty
- **Prerequisite Chain**: Cannot create circular dependencies
- **Active Status**: Inactive courses cannot be used for new class creation
- **Deletion**: Courses with existing classes cannot be deleted

### 4.2 Semester Management Rules
- **Date Validation**: Start date < Last cancel date < End date
- **Academic Year Alignment**: All dates must fall within the specified academic year
- **Semester Numbers**: Must be 1 (Fall), 2 (Spring), or 3 (Summer)
- **Academic Year Format**: Must follow "YYYY-YYYY" pattern with consecutive years
- **Overlap Prevention**: Semesters within the same academic year cannot overlap
- **Registration Deadlines**: Last cancel date determines registration deadline

### 4.3 Data Integrity Rules
- **Faculty Reference**: Course's facultyId must reference existing faculty
- **Prerequisite Reference**: prerequisiteCourseId must reference existing active course
- **Deletion Cascading**: Deleting faculty affects all related courses
- **Status Propagation**: Deactivating prerequisite courses affects dependent courses

---

## 5. Error Handling

### 5.1 Course Errors

**Course Not Found (404)**
```json
{
  "status": 404,
  "message": "Course not found"
}
```

**Invalid Prerequisites (400)**
```json
{
  "status": 400,
  "message": "Prerequisite course not found or inactive"
}
```

**Circular Dependency (400)**
```json
{
  "status": 400,
  "message": "Circular prerequisite dependency detected"
}
```

**Course Code Duplicate (409)**
```json
{
  "status": 409,
  "message": "Course code already exists"
}
```

### 5.2 Semester Errors

**Invalid Date Range (400)**
```json
{
  "status": 400,
  "message": "Start date must be before last cancel date and last cancel date must be before end date"
}
```

**Academic Year Mismatch (400)**
```json
{
  "status": 400,
  "message": "Start date and end date must be in the same academic year"
}
```

**Invalid Semester Number (400)**
```json
{
  "status": 400,
  "message": "Semester must be between 1 and 3"
}
```

**Academic Year Format Error (400)**
```json
{
  "status": 400,
  "message": "Academic year must be in format YYYY-YYYY"
}
```

**Semester Overlap (409)**
```json
{
  "status": 409,
  "message": "Semester dates overlap with existing semester in the same academic year"
}
```

### 5.3 Validation Errors

**General Validation Error (400)**
```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": [
    {
      "field": "credits",
      "message": "Credits must be greater than 2"
    },
    {
      "field": "courseName", 
      "message": "Course name is required"
    }
  ]
}
```

---

## 6. Usage Examples

### 6.1 Creating a Complete Course Sequence
```bash
# 1. Create foundational course
POST /api/courses
{
  "courseCode": "CS101",
  "courseName": "Nhập môn Lập trình", 
  "credits": 3,
  "facultyId": 1
}

# 2. Create intermediate course with prerequisite
POST /api/courses  
{
  "courseCode": "CS102",
  "courseName": "Cấu trúc Dữ liệu",
  "credits": 3,
  "facultyId": 1,
  "prerequisiteCourseId": 1
}

# 3. Create advanced course
POST /api/courses
{
  "courseCode": "CS103", 
  "courseName": "Thuật toán",
  "credits": 3,
  "facultyId": 1,
  "prerequisiteCourseId": 2
}
```

### 6.2 Setting up Academic Year
```bash
# 1. Create Fall semester
POST /api/semesters
{
  "semester": 1,
  "startDate": "2025-09-03", 
  "endDate": "2025-12-20",
  "academicYear": "2025-2026",
  "lastCancelDate": "2025-10-10"
}

# 2. Create Spring semester  
POST /api/semesters
{
  "semester": 2,
  "startDate": "2026-01-12",
  "endDate": "2026-04-25", 
  "academicYear": "2025-2026",
  "lastCancelDate": "2026-02-22"
}

# 3. Create Summer semester
POST /api/semesters
{
  "semester": 3,
  "startDate": "2026-06-07",
  "endDate": "2026-08-01",
  "academicYear": "2025-2026", 
  "lastCancelDate": "2026-06-22"
}
```

---

## Notes
- Course IDs are auto-generated integers
- Course codes must be unique system-wide
- Semester numbers follow standard academic conventions (1=Fall, 2=Spring, 3=Summer)
- Academic years must be consecutive (e.g., 2025-2026, not 2025-2027)
- Prerequisites create dependency chains that must be respected
- Date validations ensure logical semester progression
- This is Part 3 covering Course and Semester management
- Part 4 will cover Class, Registration, and Class Registration History operations