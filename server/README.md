# Student Management System

## 📌 Overview
This project is a **Student Management System** built using **Spring Boot** and **PostgreSQL**. It provides APIs for managing students, departments, programs, and student statuses.

## 📂 Project Structure

```
StudentManagement/
├── server/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   ├── org/
│   │   │   │   │   ├── example/
│   │   │   │   │   │   ├── backend/
│   │   │   │   │   │   │   ├── controller/
│   │   │   │   │   │   │   ├── dto/
│   │   │   │   │   │   │   ├── service/
│   │   │   │   │   │   │   ├── common/
│   │   │   │   │   │   │   ├── domain/
│   │   │   │   │   │   │   ├── repository/
│   │   │   │   │   │   │   ├── config/
│   │   │   │   │   │   │   ├── mapper/
│   │   │   │   │   │   │   ├── BackendApplication.java
│   │   │   ├── resources/
│   │   │   │   ├── db/
│   │   │   │   │   ├── V1_20250319081659_create_table.sql
│   │   │   │   │   ├── V1_20250319082559_insert_data.sql
│   │   │   │   ├── application.properties
```

## 🚀 Features

### Week 1
- Add new student: Enter information of a student and save to the database.
- Delete student: Delete student information based on Student ID (MSSV).
- Update student information: Update information of a student based on Student ID.
- Search for students: Search for students by name or Student ID.

### Week 2
- Allow renaming & creating new: faculty, student status, program
- Add search functionality: search by faculty, faculty + name
- Support data import/export: CSV, JSON, XML, Excel (at least 2)
- Add logging mechanism for troubleshooting production issues & audit purposes

## ⚙️ Setup Instructions

### **📌 Prerequisites**
- **Java 17** or higher
- **Maven 3.6.0** or higher
- **PostgreSQL** (or any other preferred SQL database)

### **🔧 Steps to Setup**
1. **Clone the repository**
   ```sh
   git clone https://github.com/your-username/StudentManagement.git
   cd StudentManagement

2. **Configure the database**
   - Create a PostgreSQL database named `student_management`.
   - Update the database configuration in `application.properties` located in `server/src/main/resources/`.
   - Change the `spring.datasource.username` and `spring.datasource.password` properties as per your PostgreSQL installation.
3. **Build the project**
    ```sh
    cd server
    mvn clean install
    ```
4. **Run the application**
    ```sh
   mvn spring-boot:run
    ```
5. **Access the application**
   - The server will start at <http://localhost:9000>.

## API Documentation 🔥

### Student Management APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/sinh-vien` | Add a new student to the system |
| GET | `/api/sinh-vien/{mssv}` | Get student details by ID |
| GET | `/api/sinh-vien` | Get paginated list of all students |
| PUT | `/api/sinh-vien/{mssv}` | Update student information by ID |
| DELETE | `/api/sinh-vien/{mssv}` | Delete a student from the system |
| GET | `/api/sinh-vien/search` | Search students by keyword |

<details>
<summary>Add Student - Example</summary>

![Add Student](https://res.cloudinary.com/dvzhmi7a9/image/upload/v1742570806/StudentManagement/1-AddStudent.png)

</details>

<details>
<summary>Get Student Details - Example</summary>

![Get Student Details](https://res.cloudinary.com/dvzhmi7a9/image/upload/v1742570933/StudentManagement/2-GetStudent.png)

</details>

<details>
<summary>List Students - Example</summary>

![List Students](https://res.cloudinary.com/dvzhmi7a9/image/upload/v1742571000/StudentManagement/3-ListStudents.png)

</details>

<details>
<summary>Update Student - Example</summary>

![Update Student](https://res.cloudinary.com/dvzhmi7a9/image/upload/v1742571066/StudentManagement/4-UpdateStudent.png)

</details>

<details>
<summary>Delete Student - Example</summary>

![Delete Student](https://res.cloudinary.com/dvzhmi7a9/image/upload/v1742571131/StudentManagement/5-DeleteStudent.png)

</details>

<details>
<summary>Search Students - Example</summary>

![Search Students](https://res.cloudinary.com/dvzhmi7a9/image/upload/v1742571196/StudentManagement/6-SearchStudents.png)

</details>

### Program Management APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/chuong-trinh` | Add a new academic program |

<details>
<summary>Add Program - Example</summary>

![Add Program](https://res.cloudinary.com/dvzhmi7a9/image/upload/v1742571261/StudentManagement/7-AddProgram.png)

</details>

### Faculty Management APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/khoa` | Add a new faculty |

<details>
<summary>Add Faculty - Example</summary>

![Add Faculty](https://res.cloudinary.com/dvzhmi7a9/image/upload/v1742570806/StudentManagement/8-AddFaculty.png)

</details>

### Student Status Management APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/tinh-trang` | Add a new student status |

<details>
<summary>Add Student Status - Example</summary>

![Add Student Status](https://res.cloudinary.com/dvzhmi7a9/image/upload/v1742570806/StudentManagement/9-AddStatus.png)

</details>

### File Transfer APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/file-transfer/export` | Export student data to file |
| POST | `/api/file-transfer/import` | Import student data from file |

**Note on Export API**: File download operations cannot be effectively shown in Postman screenshots.

**Note on Import API**: File upload results are processed server-side and cannot be adequately captured in static images.