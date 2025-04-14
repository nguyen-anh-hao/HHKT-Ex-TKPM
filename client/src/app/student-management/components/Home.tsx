'use client';

import { useEffect, useState } from 'react';
import { Button, message, Popconfirm } from 'antd';
import { PlusOutlined, UploadOutlined, DownloadOutlined } from '@ant-design/icons';
import StudentTable from './StudentTable';
import StudentModal from './StudentModal';
import { Student } from '@/interfaces/Student';
import { updateStudent as updateStudentState, addStudent as addStudentState, deleteStudent as deleteStudentState } from '../actions/StudentActions';
import { useCreateStudent, useDeleteStudent, useUpdateStudent } from '@/libs/hooks/useStudentMutation';
import useReferenceStore from '@/libs/stores/referenceStore';
import ImportModal from './ImportModal'
import ExportModal from './ExportModal';

export default function Home({ initialStudents }: { initialStudents: Student[] }) {
    const [students, setStudents] = useState<Student[]>(initialStudents);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [selectedStudent, setSelectedStudent] = useState<Student | null>(null);
    const [isImportModalVisible, setIsImportModalVisible] = useState(false);
    const [isExportModalVisible, setIsExportModalVisible] = useState(false);
    const [isResetModal, setIsResetModal] = useState(false);

    const { mutate: createStudent } = useCreateStudent();
    const { mutate: updateStudent } = useUpdateStudent();
    const { mutate: deleteStudent } = useDeleteStudent();

    const fetchReference = useReferenceStore((state) => state.fetchReference);
    
    useEffect(() => {
        fetchReference();
    }, [fetchReference]);

    const handleAddOrUpdateStudent = (value: Student) => {
        if (selectedStudent) {
            updateStudent(
                value, 
                {
                    onSuccess: () => {
                        message.success('Cập nhật sinh viên thành công');
                        setStudents(updateStudentState(students, value));
                        setIsResetModal(true);
                    },
                    onError: (error : any) => {
                        message.error(`Cập nhật sinh viên thất bại: ${error.response.data.errors.map((error: any) => error.defaultMessage).join(' ') || error.response.data.message}`);
                    }
                }
            );
        } else {
            if (value.studentId && students.some((student) => student.studentId === value.studentId)) {
                message.error('Mã số sinh viên đã tồn tại');
                return;
            }
            if (students.some((student) => student.email === value.email)) {
                message.error('Email đã tồn tại');
                return;
            }
            if (students.some((student) => student.phone === value.phone)) {
                message.error('Số điện thoại đã tồn tại');
                return;
            }
            createStudent(
                value,
                {
                    onSuccess: () => {
                        message.success('Thêm sinh viên thành công');
                        setStudents(addStudentState(students, value));
                    },
                    onError: (error: any) => {
                        message.error(`Thêm sinh viên thất bại: ${error.response.data.errors.map((error: any) => error.defaultMessage).join(' ') || error.response.data.message}`);
                    }
                }
            );
        }
    };

    const handleDeleteStudent = (studentId: string) => {
        deleteStudent(
            studentId,
            {
                onSuccess: () => {
                    message.success('Xóa sinh viên thành công');
                    setStudents(deleteStudentState(students, studentId));
                },
                onError: (error: any) => {
                    message.error(`Xóa sinh viên thất bại: ${error.response.data.errors.map((error: any) => error.defaultMessage).join(' ') || error.response.data.message}`);
                }
            }
        );
    };

    return (
        <div>
            <h1>Quản lý sinh viên</h1>
            <div style={{ display: 'flex', gap: '10px', marginBottom: '20px' }}>
                <Button type='primary' icon={<PlusOutlined />} onClick={() => { setSelectedStudent(null); setIsModalVisible(true); }}>
                    Thêm sinh viên
                </Button>
                <Button onClick={() => setIsImportModalVisible(true)} icon={<UploadOutlined />} >Nhập dữ liệu</Button>
                <Button onClick={() => setIsExportModalVisible(true)} icon={<DownloadOutlined />}>Xuất dữ liệu</Button>
            </div>
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
                isResetModal={isResetModal}
                setIsResetModal={setIsResetModal}
            />
            <ImportModal
                visible={isImportModalVisible}
                onCancel={() => setIsImportModalVisible(false)}
            />
            <ExportModal
                visible={isExportModalVisible}
                onCancel={() => setIsExportModalVisible(false)}
            />
        </div>
    );
}