'use client';

import { useState, useEffect, useMemo } from 'react';
import { Button, Modal, Input, Typography } from 'antd';
import { useFaculties, usePrograms, useStudentStatuses, useEmailDomains } from '@/libs/hooks/useReferences';
import { useCreateReference, useUpdateReference, useDeleteReference } from '@/libs/hooks/useReferenceMutation';
import ReferenceList from './components/ReferenceList';
import { message } from 'antd';

const ReferencePage = () => {
    const { mutate: createReference } = useCreateReference();
    const { mutate: updateReference } = useUpdateReference();
    const { mutate: deleteReference } = useDeleteReference();

    const facultiesQuery = useFaculties();
    const programsQuery = usePrograms();
    const studentStatusesQuery = useStudentStatuses();
    const emailDomainQuery = useEmailDomains();

    const faculties = useMemo(() => facultiesQuery.data || [], [facultiesQuery.data]);
    const programs = useMemo(() => programsQuery.data || [], [programsQuery.data]);
    const studentStatuses = useMemo(() => studentStatusesQuery.data || [], [studentStatusesQuery.data]);
    const emailDomains = useMemo(() => emailDomainQuery.data || [], [emailDomainQuery.data]);

    const [facultyValues, setFacultyValues] = useState(faculties);
    const [programValues, setProgramValues] = useState(programs);
    const [statusValues, setStatusValues] = useState(studentStatuses);
    const [emailDomainValues, setEmailDomainValues] = useState(emailDomains);

    const [isModalVisible, setIsModalVisible] = useState(false);
    const [newItemValue, setNewItemValue] = useState('');
    const [currentType, setCurrentType] = useState<ReferenceType | null>(null);

    useEffect(() => {
        setFacultyValues(faculties);
    }, [faculties]);

    useEffect(() => {
        setProgramValues(programs);
    }, [programs]);

    useEffect(() => {
        setStatusValues(studentStatuses);
    }, [studentStatuses]);

    useEffect(() => {
        setEmailDomainValues(emailDomains);
    }, [emailDomains]);

    type ReferenceType = 'faculties' | 'programs' | 'student-statuses' | 'email-domains';

    const handleDelete = (id: number, type: ReferenceType) => {
        if (type === 'faculties') {
            setFacultyValues(facultyValues.filter((item: any) => item.key !== id));
        } else if (type === 'programs') {
            setProgramValues(programValues.filter((item: any) => item.key !== id));
        } else if (type === 'student-statuses') {
            setStatusValues(studentStatuses.filter((item: any) => item.key !== id));
        } else if (type === 'email-domains') {
            setEmailDomainValues(emailDomainValues.filter((item: any) => item.key !== id));
        }

        deleteReference(
            { id, type },
            {
                onSuccess: () => {
                    message.success('Xóa thành công');
                },
                onError: (error: any) => {
                    message.error(`Lỗi khi xóa: ${error.response.data.message}`);
                },
            }
        );
    };

    const handleAdd = (type: ReferenceType) => {
        setCurrentType(type);
        setIsModalVisible(true);
    };

    const handleOk = () => {
        if (!newItemValue) {
            message.error('Vui lòng nhập tên mới');
            return;
        }
        createReference(
            { type: currentType!, value: newItemValue },
            {
                onSuccess: (response) => {
                    message.success('Thêm mới thành công');
                    const newItem = { key: response.id, value: newItemValue };
                    if (currentType === 'faculties') {
                        setFacultyValues([...facultyValues, newItem]);
                    } else if (currentType === 'programs') {
                        setProgramValues([...programValues, newItem]);
                    } else if (currentType === 'student-statuses') {
                        setStatusValues([...statusValues, newItem]);
                    } else if (currentType === 'email-domains') {
                        setEmailDomainValues([...emailDomainValues, newItem]);
                    }
                    setNewItemValue('');
                    setIsModalVisible(false);
                },
                onError: (error: any) => {
                    message.error(`Lỗi khi thêm mới: ${error.response.data.message}`);
                },
            }
        );
    };

    const handleCancel = () => {
        setIsModalVisible(false);
        setNewItemValue('');
    };

    const handleChange = (
        setState: React.Dispatch<React.SetStateAction<any>>,
        index: number,
        newValue: string,
        values: any[],
        type: ReferenceType
    ) => {
        const updatedValues = [...values];
        updatedValues[index].value = newValue;
        setState(updatedValues);
        updateReference({ type, id: updatedValues[index].key, value: newValue });
    };

    return (
        <div style={{ display: 'flex', justifyContent: 'center', gap: '40px', padding: '20px', background: '#f8f6f2' }}>
            {/* Khoa */}
            <ReferenceList
                title="Khoa"
                values={facultyValues}
                onChange={(index, newValue) => handleChange(setFacultyValues, index, newValue, facultyValues, 'faculties')}
                onDelete={(id) => handleDelete(id, 'faculties')}
                onAdd={() => handleAdd('faculties')}
            />

            {/* Chương trình */}
            <ReferenceList
                title="Chương trình"
                values={programValues}
                onChange={(index, newValue) => handleChange(setProgramValues, index, newValue, programValues, 'programs')}
                onDelete={(id) => handleDelete(id, 'programs')}
                onAdd={() => handleAdd('programs')}
            />

            {/* Trạng thái */}
            <ReferenceList
                title="Trạng thái"
                values={statusValues}
                onChange={(index, newValue) => handleChange(setStatusValues, index, newValue, statusValues, 'student-statuses')}
                onDelete={(id) => handleDelete(id, 'student-statuses')}
                onAdd={() => handleAdd('student-statuses')}
            />

            {/* Email Domain */}
            <ReferenceList
                title="Email domain"
                values={emailDomainValues}
                onChange={(index, newValue) => handleChange(setEmailDomainValues, index, newValue, emailDomainValues, 'email-domains')}
                onDelete={(id) => handleDelete(id, 'email-domains')}
                onAdd={() => handleAdd('email-domains')}
            />

            {/* Modal để thêm item mới */}
            <Modal
                title={`Thêm ${currentType === 'faculties' ? 'Khoa' : currentType === 'programs' ? 'Chương trình' : currentType === 'student-statuses' ? 'Trạng thái' : 'Email domain'}`}
                open={isModalVisible}
                onOk={handleOk}
                onCancel={handleCancel}
            >
                <Input
                    value={newItemValue}
                    onChange={(e) => setNewItemValue(e.target.value)}
                    placeholder="Nhập tên mới"
                />
            </Modal>
        </div>
    );
};

export default ReferencePage;