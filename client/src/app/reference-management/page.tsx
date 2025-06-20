'use client';

import { useState, useEffect, useMemo } from 'react';
import { Button, Modal, Input, Typography } from 'antd';
import { useFaculties, usePrograms, useStudentStatuses, useEmailDomains } from '@/libs/hooks/reference/useReferences';
import { useCreateReference, useUpdateReference, useDeleteReference } from '@/libs/hooks/reference/useReferenceMutation';
import ReferenceList from './components/ReferenceList';
import { message } from 'antd';
import { useTranslations } from 'next-intl';

const ReferencePage = () => {
    const { mutate: createReference } = useCreateReference();
    const { mutate: updateReference } = useUpdateReference();
    const { mutate: deleteReference } = useDeleteReference();
    const t = useTranslations('reference-management');
    const tCommon = useTranslations('common');
    const tMessages = useTranslations('messages');
    const tValidation = useTranslations('validation');

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
                    message.success(tMessages('delete-success', { entity: t(type) }));
                },
                onError: (error: any) => {
                    message.error(`${tMessages('delete-error', { entity: t(type) })}: ${error.response.data.message}`);
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
            message.error(tValidation('required', { field: t('item-name') }));
            return;
        }
        createReference(
            { type: currentType!, value: newItemValue },
            {
                onSuccess: (response) => {
                    message.success(tMessages('create-success', { entity: t(currentType!) }));
                    const newItem = { key: response.id, value: newItemValue, label: newItemValue };
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
                    message.error(`${tMessages('create-error', { entity: t(currentType!) })}: ${error.response.data.message}`);
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
        updateReference(
            { type, id: updatedValues[index].key, value: newValue },
            {
                onSuccess: () => {
                    message.success(tMessages('update-success', { entity: tCommon('status-rules').toLowerCase() }));
                },
                onError: (error: any) => {
                    message.error(t('update-error', { error: error.response.data.message }));
                },
            }
        );
    };

    const getModalTitle = () => {
        switch (currentType) {
            case 'faculties':
                return t('add-item', { item: t('faculties') });
            case 'programs':
                return t('add-item', { item: t('programs') });
            case 'student-statuses':
                return t('add-item', { item: t('student-statuses') });
            case 'email-domains':
                return t('add-item', { item: t('email-domains') });
            default:
                return t('add-item');
        }
    };

    return (
        <>
            <h1>{t('title')}</h1>
            <div style={{ display: 'flex', justifyContent: 'center', gap: '40px', padding: '20px', background: '#f8f6f2' }}>
                <ReferenceList
                    title={t('faculties')}
                    values={facultyValues}
                    onChange={(index, newValue) => handleChange(setFacultyValues, index, newValue, facultyValues, 'faculties')}
                    onDelete={(id) => handleDelete(id, 'faculties')}
                    onAdd={() => handleAdd('faculties')}
                />

                <ReferenceList
                    title={t('programs')}
                    values={programValues}
                    onChange={(index, newValue) => handleChange(setProgramValues, index, newValue, programValues, 'programs')}
                    onDelete={(id) => handleDelete(id, 'programs')}
                    onAdd={() => handleAdd('programs')}
                />

                <ReferenceList
                    title={t('student-statuses')}
                    values={statusValues}
                    onChange={(index, newValue) => handleChange(setStatusValues, index, newValue, statusValues, 'student-statuses')}
                    onDelete={(id) => handleDelete(id, 'student-statuses')}
                    onAdd={() => handleAdd('student-statuses')}
                />

                <ReferenceList
                    title={t('email-domains')}
                    values={emailDomainValues}
                    onChange={(index, newValue) => handleChange(setEmailDomainValues, index, newValue, emailDomainValues, 'email-domains')}
                    onDelete={(id) => handleDelete(id, 'email-domains')}
                    onAdd={() => handleAdd('email-domains')}
                />

                <Modal
                    title={getModalTitle()}
                    open={isModalVisible}
                    onOk={handleOk}
                    onCancel={handleCancel}
                >
                    <Input
                        value={newItemValue}
                        onChange={(e) => setNewItemValue(e.target.value)}
                        placeholder={t('item-name')}
                    />
                </Modal>
            </div>
        </>
    );
};

export default ReferencePage;