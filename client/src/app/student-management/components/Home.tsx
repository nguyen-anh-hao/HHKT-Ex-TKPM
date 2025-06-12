'use client';

import { useEffect, useState } from 'react';
import { Button, message, Popconfirm } from 'antd';
import { PlusOutlined, UploadOutlined, DownloadOutlined } from '@ant-design/icons';
import StudentTable from './StudentTable';
import StudentModal from './StudentModal';
import { Student } from '@/interfaces/student/Student';
import { updateStudent as updateStudentState, addStudent as addStudentState, deleteStudent as deleteStudentState } from '../actions/StudentActions';
import { useCreateStudent, useDeleteStudent, useUpdateStudent } from '@/libs/hooks/student/useStudentMutation';
import { useStudents } from '@/libs/hooks/student/useStudents';
import useReferenceStore from '@/libs/stores/referenceStore';
import ImportModal from './ImportModal'
import ExportModal from './ExportModal';
import { createStudentSchema } from '@/libs/validators/studentSchema';
import { useTranslations } from 'next-intl';

export default function Home() {
    const { data: initialStudents = [], isLoading: isLoadingStudents } = useStudents();
    const [students, setStudents] = useState<Student[]>([]);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [selectedStudent, setSelectedStudent] = useState<Student | null>(null);
    const [isImportModalVisible, setIsImportModalVisible] = useState(false);
    const [isExportModalVisible, setIsExportModalVisible] = useState(false);
    const [isResetModal, setIsResetModal] = useState(false);

    // Update local state when data from API changes
    useEffect(() => {
        if (initialStudents) {
            setStudents(initialStudents);
        }
    }, [initialStudents]);

    const { mutate: createStudent, isPending: isCreating } = useCreateStudent();
    const { mutate: updateStudent, isPending: isUpdating } = useUpdateStudent();
    const { mutate: deleteStudent, isPending: isDeleting } = useDeleteStudent();

    const fetchReference = useReferenceStore((state) => state.fetchReference);
    
    const t = useTranslations('student-management');
    const tCommon = useTranslations('common');
    const tMessages = useTranslations('messages');
    
    useEffect(() => {
        fetchReference();
    }, [fetchReference]);

    const handleAddOrUpdateStudent = (value: Student) => {
        if (selectedStudent) {
            updateStudent(
                value, 
                {
                    onSuccess: () => {
                        message.success(tMessages('update-success', { entity: tCommon('student').toLowerCase() }));
                        setStudents(updateStudentState(students, value));
                        setIsModalVisible(false);
                        setSelectedStudent(null);
                        setIsResetModal(true);
                    },
                    onError: (error : any) => {
                        const errorMessage = error.response?.data?.errors
                            ? error.response.data.errors.map((error: any) => error.defaultMessage).join(' ')
                            : error.response?.data?.message || error.message || tMessages('update-error', { entity: tCommon('student').toLowerCase() });
                        message.error(`${tMessages('update-error', { entity: tCommon('student').toLowerCase() })}: ${errorMessage}`);
                    }
                }
            );
        } else {
            const validation = createStudentSchema(students).safeParse(value);
            if (!validation.success) {
                const errorMessages = validation.error.errors.map((error) => error.message).join(', ');
                message.error(`${tMessages('create-error', { entity: tCommon('student').toLowerCase() })}: ${errorMessages}`);
                return;
            }
            createStudent(
                value,
                {
                    onSuccess: () => {
                        message.success(tMessages('create-success', { entity: tCommon('student').toLowerCase() }));
                        setStudents(addStudentState(students, value));
                        setIsModalVisible(false);
                    },
                    onError: (error: any) => {
                        const errorMessage = error.response?.data?.errors
                            ? error.response.data.errors.map((error: any) => error.defaultMessage).join(' ')
                            : error.response?.data?.message || error.message || tMessages('create-error', { entity: tCommon('student').toLowerCase() });
                        message.error(`${tMessages('create-error', { entity: tCommon('student').toLowerCase() })}: ${errorMessage}`);
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
                    message.success(tMessages('delete-success', { entity: tCommon('student').toLowerCase() }));
                    setStudents(deleteStudentState(students, studentId));
                },
                onError: (error: any) => {
                    const errorMessage = error.response?.data?.errors
                        ? error.response.data.errors.map((error: any) => error.defaultMessage).join(' ')
                        : error.response?.data?.message || error.message || tMessages('delete-error', { entity: tCommon('student').toLowerCase() });
                    message.error(`${tMessages('delete-error', { entity: tCommon('student').toLowerCase() })}: ${errorMessage}`);
                }
            }
        );
    };

    const isLoading = isLoadingStudents || isCreating || isUpdating || isDeleting;

    return (
        <div>
            <h1>{t('student-management')}</h1>
            <div style={{ display: 'flex', gap: '10px', marginBottom: '20px' }}>
                <Button 
                    type='primary' 
                    icon={<PlusOutlined />} 
                    onClick={() => { setSelectedStudent(null); setIsModalVisible(true); }}
                    loading={isCreating}
                >
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
                loading={isLoading}
            />
            <StudentModal
                visible={isModalVisible}
                onCancel={() => { setIsModalVisible(false); setSelectedStudent(null); }}
                onSubmit={handleAddOrUpdateStudent}
                student={selectedStudent || undefined}
                isResetModal={isResetModal}
                setIsResetModal={setIsResetModal}
                isSubmitting={isCreating || isUpdating}
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