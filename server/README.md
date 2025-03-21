# Student Management System

## 📌 Overview
This project is a **Student Management System** built using **Spring Boot** and **PostgreSQL**. It provides functionalities for managing students, departments, programs, and student statuses.

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
│   ├── pom.xml
├── README.md
```


## 🚀 Features

### **Week 1**
- **Student Management**
   - Add a new student.
   - Delete a student by MSSV.
   - Update student information by MSSV.
   - Search for students by name or MSSV.

### **Week 2**
- **Department, Program & Student Status Management**
   - Rename and create new: departments, student statuses, and academic programs.
   - Search functionality: search by department, or department + name.
   - Data import/export: support at least two formats (CSV, JSON, XML, Excel).
   - Add logging mechanism for troubleshooting production issues & audit purposes.

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
   - The server will start on `http://localhost:9000`.

## 📡 API Endpoints

### **SinhVienController (Student Management)**
| Method | Endpoint | Description |  
|--------|----------|-------------|  
| `POST` | `/api/sinh-vien` | Add a new student |  
| `GET` | `/api/sinh-vien/{mssv}` | Get student details by MSSV |  
| `GET` | `/api/sinh-vien` | Get a paginated list of all students |  
| `PUT` | `/api/sinh-vien/{mssv}` | Update student details by MSSV |  
| `DELETE` | `/api/sinh-vien/{mssv}` | Delete a student by MSSV |  

### **KhoaController (Department Management)**
| Method | Endpoint | Description |  
|--------|----------|-------------|  
| `POST` | `/api/khoa` | Add a new department |  
| `PUT` | `/api/khoa/{id}` | Rename an existing department |  
| `GET` | `/api/khoa` | Get a list of all departments |  
| `GET` | `/api/khoa/search` | Search department by name |  

### **ChuongTrinhController (Program Management)**
| Method | Endpoint | Description |  
|--------|----------|-------------|  
| `POST` | `/api/chuong-trinh` | Add a new academic program |  
| `PUT` | `/api/chuong-trinh/{id}` | Rename an existing academic program |  
| `GET` | `/api/chuong-trinh` | Get a list of all academic programs |  

### **TinhTrangSinhVienController (Student Status Management)**
| Method | Endpoint | Description |  
|--------|----------|-------------|  
| `POST` | `/api/tinh-trang` | Add a new student status |  
| `PUT` | `/api/tinh-trang/{id}` | Rename an existing student status |  
| `GET` | `/api/tinh-trang` | Get a list of all student statuses |  

### **FileTransferController (Data Import/Export)**
| Method | Endpoint | Description |  
|--------|----------|-------------|  
| `GET` | `/api/file-transfer/export` | Export student data to a file (CSV, JSON, XML, Excel) |  
| `POST` | `/api/file-transfer/import` | Import student data from a file (CSV, JSON, XML, Excel) |  