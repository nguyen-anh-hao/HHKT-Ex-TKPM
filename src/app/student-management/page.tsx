"use client"

import { useState } from "react";
import { Button } from "antd";
import { PlusOutlined } from "@ant-design/icons";
import StudentTable from "./_components/StudentTable";
import StudentModal from "./_components/StudentModal";
import { addStudent, updateStudent, deleteStudent } from "./_components/StudentActions";
import { Student } from "./interface/Student";
import { message } from "antd";

const Home = () => {
    const [students, setStudents] = useState<Student[]>([
        {
            studentId: "1",
            fullName: "Nguyễn Văn A",
            dob: "1999-01-01",
            gender: "Nam",
            faculty: "Khoa Tiếng Anh thương mại",
            intake: "2021",
            program: "Đại trà",
            status: "Đang học",
            permanentAddress: "Hà Nội",
            temporaryAddress: "Hà Nội",
            email: "",
            phone: "",
            documentType: "",
            documentNumber: "",
            issuedDate: "",
            expiredDate: "",
            issuedBy: "",
            issuedCountry: "",
            hasChip: false, 
            nationality: "",
        },
        {
            studentId: "2",
            fullName: "Nguyễn Thị B",
            dob: "1989-01-01",
            gender: "Nữ",
            faculty: "Khoa Tiếng Pháp",
            intake: "2019",
            program: "CLC",
            status: "Đã tốt nghiệp",
            permanentAddress: "Hà Tĩnh",
            temporaryAddress: "Hà Nam",
            email: "",
            phone: "",
            documentType: "",
            documentNumber: "",
            issuedDate: "",
            expiredDate: "",
            issuedBy: "",
            issuedCountry: "",
            hasChip: false, 
            nationality: "",
        },
    ]);

    const [isModalVisible, setIsModalVisible] = useState(false);
    const [selectedStudent, setSelectedStudent] = useState<Student | null>(null);

    const handleAddOrUpdateStudent = (values: Student) => {
        if (selectedStudent) {
            setStudents(updateStudent(students, values));
        }
        else {
            setStudents(addStudent(students, values));
        }
    };

    return (
        <div>
            <h1>Quản lý sinh viên</h1>
            <Button style={{ marginBottom: 16 }} type="primary" icon={<PlusOutlined />} onClick={() => { setSelectedStudent(null); setIsModalVisible(true); }}>Thêm sinh viên</Button>
            <StudentTable
                students={students}
                openModal={setIsModalVisible}
                onEdit={setSelectedStudent}
                onDelete={(studentId: string) => setStudents(deleteStudent(students, studentId))}
            />
            <StudentModal
                visible={isModalVisible}
                onCancel={() => {
                    setIsModalVisible(false);
                    setSelectedStudent(null);
                }}
                onSubmit={handleAddOrUpdateStudent}
                student={selectedStudent || undefined}
            />
        </div>
    );
};

export default Home;

// **Tìm kiếm sinh viên**: Tìm kiếm sinh viên theo họ tên hoặc MSSV.
// tanstack