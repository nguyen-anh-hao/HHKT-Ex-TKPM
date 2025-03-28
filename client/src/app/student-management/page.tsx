"use client";

import { useState, useEffect } from "react";
import { Button } from "antd";
import { PlusOutlined } from "@ant-design/icons";
import StudentTable from "./components/StudentTable";
import StudentModal from "./components/StudentModal";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { fetchStudents, createStudent as apiCreateStudent, updateStudent as apiUpdateStudent, deleteStudent as apiDeleteStudent } from "@/lib/api/studentApi";
import { convertGetResponseToStudent } from "@/lib/utils/studentConverter";
import { Student } from "@/interfaces/student.interface";
import { updateStudent as updateStudentState, addStudent as addStudentState, deleteStudent as deleteStudentState } from "@/lib/actions/StudentActions";
import useReferenceDataStore from "@/lib/stores/referenceDataStore";

const Home = () => {
    const [students, setStudents] = useState<Student[]>([]);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [selectedStudent, setSelectedStudent] = useState<Student | null>(null);
    const queryClient = useQueryClient();

    const { data: studentData } = useQuery({ queryKey: ["students"], queryFn: fetchStudents });
    const { mutate: createStudentAPI } = useMutation({ mutationFn: apiCreateStudent, onSuccess: () => queryClient.invalidateQueries({ queryKey: ["students"]}) });
    const { mutate: updateStudentAPI } = useMutation({ mutationFn: apiUpdateStudent, onSuccess: () => queryClient.invalidateQueries({queryKey: ["students"]}) });
    const { mutate: deleteStudentAPI } = useMutation({ mutationFn: apiDeleteStudent, onSuccess: () => queryClient.invalidateQueries({queryKey: ["students"]}) });

    useEffect(() => {
        if (studentData) {
            const convertedStudents = studentData.data.map(convertGetResponseToStudent);
            setStudents(convertedStudents);
        }
    }, [studentData]);

    const handleAddOrUpdateStudent = (values: Student) => {
        if (selectedStudent) {
            setStudents(updateStudentState(students, values));
            updateStudentAPI(values);
        } else {
            setStudents(addStudentState(students, values));
            createStudentAPI(values);
        }
    };

    const handleDeleteStudent = (studentId: string) => {
        setStudents(deleteStudentState(students, studentId));
        deleteStudentAPI(studentId);
    };

    const {
        fetchReferenceData
    } = useReferenceDataStore();

    // Fetch reference data on component mount
    useEffect(() => {
        const fetchData = async () => {
            await fetchReferenceData();
        };
        fetchData();
    }, [fetchReferenceData]);

    return (
        <div>
            <h1>Quản lý sinh viên</h1>
            <Button type="primary" icon={<PlusOutlined />} onClick={() => { setSelectedStudent(null); setIsModalVisible(true); }}>
                Thêm sinh viên
            </Button>
            <StudentTable
                students={students}
                openModal={setIsModalVisible}
                onEdit={setSelectedStudent}
                onDelete={handleDeleteStudent}
            />
            <StudentModal
                visible={isModalVisible}
                onCancel={() => { setIsModalVisible(false); setSelectedStudent(null); }}
                onSubmit={handleAddOrUpdateStudent}
                student={selectedStudent || undefined}
            />
        </div>
    );
};

export default Home;

// Task:
// - Match labels "Quản lý danh mục" with "Quản lý sinh viên" -> Done
// - Call API "Quản lý danh mục" -> Xóa, Sửa => Done, Thêm
// - Export Import file

// - 4 cái bisiness rule -> CHữa cháy được SĐT, Email, MSSV -> Còn cái kiểu tốt nghiệp ko được back lại

// Bug:
// - Update student API

// Improvement:
// - Pagination for student table