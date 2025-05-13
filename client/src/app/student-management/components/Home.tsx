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
import { createStudentSchema } from '@/libs/validators/studentSchema';
import { useTranslations } from 'next-intl';

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
    
    const t = useTranslations('student-management');
    
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
                        message.error(`Cập nhật sinh viên thất bại: ${error.response.data.errors
                            ? error.response.data.errors.map((error: any) => error.defaultMessage).join(' ')
                            : error.response.data.message}`);
                    }
                }
            );
        } else {
            const validation = createStudentSchema(students).safeParse(value);
            if (!validation.success) {
                const errorMessages = validation.error.errors.map((error) => error.message).join(', ');
                message.error(`Thêm sinh viên thất bại: ${errorMessages}`);
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
            <h1>{t('student-management')}</h1>
            <div style={{ display: 'flex', gap: '10px', marginBottom: '20px' }}>
                <Button type='primary' icon={<PlusOutlined />} onClick={() => { setSelectedStudent(null); setIsModalVisible(true); }}>
                    {t('add-student')}
                </Button>
                <Button onClick={() => setIsImportModalVisible(true)} icon={<UploadOutlined />}>{t('input-data')}</Button>
                <Button onClick={() => setIsExportModalVisible(true)} icon={<DownloadOutlined />}>{t('output-data')}</Button>
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