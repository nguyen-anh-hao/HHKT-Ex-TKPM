"use client"

import { useEffect, useState } from "react";
import { Button } from "antd";
import { PlusOutlined } from "@ant-design/icons";
import StudentTable from "./_components/StudentTable";
import StudentModal from "./_components/StudentModal";
import { addStudent, updateStudent, deleteStudent } from "./_components/StudentActions";
import { Student } from "./interface/Student";

import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';

const fetchData = async () => {
    try {
        const { data } = await axios.get('http://localhost:8080/api/sinh-vien');
        return data;
    } catch (error) {
        console.error(error);
        throw error;
    }
};

const createData = async (values: Student) => {
    try {
        const { data } = await axios.post('http://localhost:8080/api/sinh-vien', values);
        return data; // Return the response data
    } catch (error) {
        console.error("Error creating student:", error);
        throw error; // Re-throw the error for useMutation to handle
    }
};

const updateData = async (values: Student) => {
    try {
        await axios.put(`http://localhost:8080/api/sinh-vien/${values.studentId}`, values);
    } catch (error) {
        console.error(error);
        throw error;
    }
}

const deleteData = async (studentId: string) => {
    try {
        await axios.delete(`http://localhost:8080/api/sinh-vien/${studentId}`);
    } catch (error) {
        console.error(error);
        throw error;
    }
}

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
    
    const queryClient = useQueryClient();
    
    const { data: _student } = useQuery({ queryKey: ['sinh-vien'], queryFn: fetchData });
    const { mutate: _createStudent } = useMutation({ mutationFn: createData, onSuccess: () => queryClient.invalidateQueries({ queryKey: ['sinh-vien'] }) });
    const { mutate: _updateStudent } = useMutation({ mutationFn: updateData, onSuccess: () => queryClient.invalidateQueries({ queryKey: ['sinh-vien'] }) });
    const { mutate: _deleteStudent } = useMutation({ mutationFn: deleteData, onSuccess: () => queryClient.invalidateQueries({ queryKey: ['sinh-vien'] }) });

    const [isModalVisible, setIsModalVisible] = useState(false);
    const [selectedStudent, setSelectedStudent] = useState<Student | null>(null);

    const handleAddOrUpdateStudent = (values: Student) => {
        if (selectedStudent) {
            setStudents(updateStudent(students, values));
            _createStudent(values);
        }
        else {
            setStudents(addStudent(students, values));
            _updateStudent(values);
        }
    };

    const handleDeleteStudent = (studentId: string) => {
        setStudents(deleteStudent(students, studentId));
        _deleteStudent(studentId);
    }

    useEffect(() => {
        if (_student) {
            console.log(_student);
        }
    }, [_student]);

    return (
        <div>
            <h1>Quản lý sinh viên</h1>
            <Button style={{ marginBottom: 16 }} type="primary" icon={<PlusOutlined />} onClick={() => { setSelectedStudent(null); setIsModalVisible(true); }}>Thêm sinh viên</Button>
            <StudentTable
                students={students}
                openModal={setIsModalVisible}
                onEdit={setSelectedStudent}
                onDelete={handleDeleteStudent}
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