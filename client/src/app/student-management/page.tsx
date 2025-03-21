"use client"

import { useEffect, useState } from "react";
import { Button } from "antd";
import { PlusOutlined } from "@ant-design/icons";
import StudentTable from "./_components/StudentTable";
import StudentModal from "./_components/StudentModal";
import { addStudent, updateStudent, deleteStudent } from "./_components/StudentActions";
import { defaultStudent, Student } from "./interface/Student";

import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';
import { set } from "zod";
import moment from "moment";

const fetchData = async () => {
    try {
        const { data } = await axios.get('http://localhost:9000/api/sinh-vien?page=0&size=50');
        return data;
    } catch (error) {
        console.error(error);
        throw error;
    }
};

const createData = async (values: Student) => {
    var _values = {
        mssv: values.studentId,
        hoTen: values.fullName,
        ngaySinh: moment(values.dob).format('YYYY-MM-DD'),
        gioiSinh: values.gender,
        khoaId: 4,
        khoaHoc: values.intake,
        chuongTrinhId: 2,
        email: values.email,
        soDienThoai: values.phone,
        tinhTrangId: 2,
        quocTich: values.nationality,
    }

    console.log(_values);

    try {
        const { data } = await axios.post('http://localhost:9000/api/sinh-vien', _values        );
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
        // {
        //     studentId: "1",
        //     fullName: "Nguyễn Văn A",
        //     dob: "1999-01-01",
        //     gender: "Nam",
        //     faculty: "Khoa Tiếng Anh thương mại",
        //     intake: "2021",
        //     program: "Đại trà",
        //     status: "Đang học",
        //     permanentAddress: "Hà Nội",
        //     temporaryAddress: "Hà Nội",
        //     email: "",
        //     phone: "",
        //     documentType: "",
        //     documentNumber: "",
        //     issuedDate: "",
        //     expiredDate: "",
        //     issuedBy: "",
        //     issuedCountry: "",
        //     hasChip: false, 
        //     nationality: "",
        // },
        // {
        //     studentId: "2",
        //     fullName: "Nguyễn Thị B",
        //     dob: "1989-01-01",
        //     gender: "Nữ",
        //     faculty: "Khoa Tiếng Pháp",
        //     intake: "2019",
        //     program: "CLC",
        //     status: "Đã tốt nghiệp",
        //     permanentAddress: "Hà Tĩnh",
        //     temporaryAddress: "Hà Nam",
        //     email: "",
        //     phone: "",
        //     documentType: "",
        //     documentNumber: "",
        //     issuedDate: "",
        //     expiredDate: "",
        //     issuedBy: "",
        //     issuedCountry: "",
        //     hasChip: false, 
        //     nationality: "",
        // },
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
            _updateStudent(values);
        }
        else {
            setStudents(addStudent(students, values));
            _createStudent(values);
        }
    };

    const handleDeleteStudent = (studentId: string) => {
        setStudents(deleteStudent(students, studentId));
        _deleteStudent(studentId);
    }

    useEffect(() => {
        if (_student) {
            console.log(_student.data);
            var __students: Student[] = [];

            for (let i = 0; i < _student.data.length; i++) {
                var _newStudent = { ...defaultStudent };

                _newStudent.studentId = _student.data[i].mssv;
                _newStudent.fullName = _student.data[i].hoTen;
                _newStudent.dob = _student.data[i].ngaySinh;
                _newStudent.gender = _student.data[i].gioiTinh;
                _newStudent.faculty = _student.data[i].khoa;
                _newStudent.intake = _student.data[i].khoaHoc;
                _newStudent.program = _student.data[i].chuongTrinh;
                _newStudent.email = _student.data[i].email;
                _newStudent.phone = _student.data[i].soDienThoai;
                _newStudent.status = _student.data[i].tinhTrang;
                _newStudent.nationality = _student.data[i].quocTich;
                // _newStudent.permanentAddress = _student.data[i].diaChis[0].soNhaTenDuong + ", " + _student.data[i].diaChis[0].phuongXa + ", " + _student.data[i].diaChis[0].quanHuyen + ", " + _student.data[i].diaChis[0].tinhThanhPho + ", " + _student.data[i].diaChis[0].quocGia;
                // _newStudent.temporaryAddress = _student.data[i].diaChis[1].soNhaTenDuong + ", " + _student.data[i].diaChis[1].phuongXa + ", " + _student.data[i].diaChis[1].quanHuyen + ", " + _student.data[i].diaChis[1].tinhThanhPho + ", " + _student.data[i].diaChis[1].quocGia;
                // _newStudent.documentType = _student.data[i].giayTos[0].loaiGiayTo; 
                // _newStudent.documentNumber = _student.data[i].giayTos[0].soGiayTo;
                // _newStudent.issuedDate = _student.data[i].giayTos[0].ngayCap;
                // _newStudent.expiredDate = _student.data[i].giayTos[0].ngayHetHan;
                // _newStudent.issuedBy = _student.data[i].giayTos[0].noiCap;
                // _newStudent.issuedCountry = _student.data[i].giayTos[0].quocGiaCap;
                // _newStudent.hasChip = _student.data[i].giayTos[0].coGanChip;
                __students = [...__students, _newStudent];
            }
            setStudents(__students);
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