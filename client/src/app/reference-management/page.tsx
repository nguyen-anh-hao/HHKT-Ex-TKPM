'use client';

import { useState, useEffect, useMemo } from 'react';
import { Button, Modal, Input, Typography, Select, message } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import { useFaculties, usePrograms, useStudentStatuses, useEmailDomains } from '@/libs/hooks/reference/useReferences';
import { useCreateReference, useUpdateReference, useDeleteReference } from '@/libs/hooks/reference/useReferenceMutation';
import ReferenceList from './components/ReferenceList';
import { useTranslations } from 'next-intl';

const { Option } = Select;

type ReferenceType = 'faculties' | 'programs' | 'student-statuses' | 'email-domains';

const referenceTypes: { value: ReferenceType; labelKey: string }[] = [
    { value: 'faculties', labelKey: 'faculties' },
    { value: 'programs', labelKey: 'programs' },
    { value: 'student-statuses', labelKey: 'student-statuses' },
    { value: 'email-domains', labelKey: 'email-domains' },
];

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

    const faculties = useMemo(() => (facultiesQuery.data || []).map(f => ({
        ...f,
        label: f.facultyName,
        value: f.facultyName
    })), [facultiesQuery.data]);
    
    const programs = useMemo(() => (programsQuery.data || []).map(p => ({
        ...p,
        label: p.programName,
        value: p.programName
    })), [programsQuery.data]);
    
    const studentStatuses = useMemo(() => (studentStatusesQuery.data || []).map(s => ({
        ...s,
        label: s.studentStatusName,
        value: s.studentStatusName
    })), [studentStatusesQuery.data]);
    
    const emailDomains = useMemo(() => (emailDomainQuery.data || []).map(d => ({
        ...d,
        label: d.domain,
        value: d.domain
    })), [emailDomainQuery.data]);

    // Track local edits for each category
    const [facultyValues, setFacultyValues] = useState(faculties);
    const [programValues, setProgramValues] = useState(programs);
    const [statusValues, setStatusValues] = useState(studentStatuses);
    const [emailDomainValues, setEmailDomainValues] = useState(emailDomains);

    // Track original values for comparison
    useEffect(() => { setFacultyValues(faculties); }, [faculties]);
    useEffect(() => { setProgramValues(programs); }, [programs]);
    useEffect(() => { setStatusValues(studentStatuses); }, [studentStatuses]);
    useEffect(() => { setEmailDomainValues(emailDomains); }, [emailDomains]);

    // Add modal state
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [newItemValue, setNewItemValue] = useState('');
    const [newItemType, setNewItemType] = useState<ReferenceType>('faculties');

    // Handle delete (unchanged)
    const handleDelete = (id: number, type: ReferenceType) => {
        deleteReference(
            { id, type },
            {
                onSuccess: () => {
                    if (type === 'faculties') setFacultyValues(facultyValues.filter((item: any) => item.key !== id));
                    else if (type === 'programs') setProgramValues(programValues.filter((item: any) => item.key !== id));
                    else if (type === 'student-statuses') setStatusValues(statusValues.filter((item: any) => item.key !== id));
                    else if (type === 'email-domains') setEmailDomainValues(emailDomainValues.filter((item: any) => item.key !== id));
                    message.success(tMessages('delete-success', { entity: t(type) }));
                },
                onError: (error: any) => {
                    message.error(`${tMessages('delete-error', { entity: t(type) })}: ${error.response.data.message}`);
                },
            }
        );
    };

    // Handle add
    const handleAdd = () => {
        setIsModalVisible(true);
        setNewItemValue('');
        setNewItemType('faculties');
    };

    const handleOk = () => {
        if (!newItemValue) {
            message.error(tValidation('required', { field: t('item-name') }));
            return;
        }
        createReference(
            { type: newItemType, value: newItemValue },
            {
                onSuccess: (response) => {
                    message.success(tMessages('create-success', { entity: t(newItemType) }));
                    
                    // Create type-specific items with the required properties
                    let newItem;
                    if (newItemType === 'faculties') {
                        newItem = { key: response.id, value: response.id, label: newItemValue, facultyName: newItemValue };
                        setFacultyValues([...facultyValues, newItem]);
                    }
                    else if (newItemType === 'programs') {
                        newItem = { key: response.id, value: response.id, label: newItemValue, programName: newItemValue };
                        setProgramValues([...programValues, newItem]);
                    }
                    else if (newItemType === 'student-statuses') {
                        newItem = { key: response.id, value: response.id, label: newItemValue, studentStatusName: newItemValue };
                        setStatusValues([...statusValues, newItem]);
                    }
                    else if (newItemType === 'email-domains') {
                        newItem = { key: response.id, value: response.id, label: newItemValue, domain: newItemValue };
                        setEmailDomainValues([...emailDomainValues, newItem]);
                    }
                    
                    setNewItemValue('');
                    setIsModalVisible(false);
                },
                onError: (error: any) => {
                    message.error(`${tMessages('create-error', { entity: t(newItemType) })}: ${error.response?.data?.message || error.message}`);
                },
            }
        );
    };

    const handleCancel = () => {
        setIsModalVisible(false);
        setNewItemValue('');
    };

    // Inline edit handlers - now save immediately
    const handleEdit = (
        type: ReferenceType,
        index: number,
        newValue: string
    ) => {
        let itemToUpdate;
        let updatedValues;

        if (type === 'faculties') {
            updatedValues = [...facultyValues];
            itemToUpdate = { ...updatedValues[index] };
            itemToUpdate = { 
                ...itemToUpdate, 
                value: newValue,
                label: newValue,
                facultyName: newValue 
            };
            updatedValues[index] = itemToUpdate;
            setFacultyValues(updatedValues);
        } else if (type === 'programs') {
            updatedValues = [...programValues];
            itemToUpdate = { ...updatedValues[index] };
            itemToUpdate = { 
                ...itemToUpdate, 
                value: newValue,
                label: newValue,
                programName: newValue 
            };
            updatedValues[index] = itemToUpdate;
            setProgramValues(updatedValues);
        } else if (type === 'student-statuses') {
            updatedValues = [...statusValues];
            itemToUpdate = { ...updatedValues[index] };
            itemToUpdate = { 
                ...itemToUpdate, 
                value: newValue,
                label: newValue,
                studentStatusName: newValue 
            };
            updatedValues[index] = itemToUpdate;
            setStatusValues(updatedValues);
        } else if (type === 'email-domains') {
            updatedValues = [...emailDomainValues];
            itemToUpdate = { ...updatedValues[index] };
            itemToUpdate = { 
                ...itemToUpdate, 
                value: newValue,
                label: newValue,
                domain: newValue 
            };
            updatedValues[index] = itemToUpdate;
            setEmailDomainValues(updatedValues);
        }

        // Save immediately to API
        if (itemToUpdate) {
            updateReference(
                { 
                    type: type, 
                    id: itemToUpdate.key, 
                    value: newValue 
                },
                {
                    onSuccess: () => {
                        message.success(tMessages('update-success', { entity: t(type) }));
                    },
                    onError: (error: any) => {
                        message.error(`${tMessages('update-error', { entity: t(type) })}: ${error.response?.data?.message || error.message}`);
                        
                        // Revert changes on error
                        if (type === 'faculties') setFacultyValues([...faculties]);
                        else if (type === 'programs') setProgramValues([...programs]);
                        else if (type === 'student-statuses') setStatusValues([...studentStatuses]);
                        else if (type === 'email-domains') setEmailDomainValues([...emailDomains]);
                    },
                }
            );
        }
    };

    return (
        <>
            <h1>{t('title')}</h1>
            <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'flex-start', gap: 16, marginBottom: 16 }}>
                <Button type="primary" icon={<PlusOutlined />} onClick={handleAdd}>
                    {t('add-item')}
                </Button>
                {/* Removed save button */}
            </div>
            <div style={{ display: 'flex', justifyContent: 'center', gap: '40px', padding: '0px', background: '#f8f6f2' }}>
                <ReferenceList
                    title={t('faculties')}
                    values={facultyValues}
                    onChange={(index, newValue) => handleEdit('faculties', index, newValue)}
                    onDelete={(id) => handleDelete(id, 'faculties')}
                    editable
                />
                <ReferenceList
                    title={t('programs')}
                    values={programValues}
                    onChange={(index, newValue) => handleEdit('programs', index, newValue)}
                    onDelete={(id) => handleDelete(id, 'programs')}
                    editable
                />
                <ReferenceList
                    title={t('student-statuses')}
                    values={statusValues}
                    onChange={(index, newValue) => handleEdit('student-statuses', index, newValue)}
                    onDelete={(id) => handleDelete(id, 'student-statuses')}
                    editable
                />
                <ReferenceList
                    title={t('email-domains')}
                    values={emailDomainValues}
                    onChange={(index, newValue) => handleEdit('email-domains', index, newValue)}
                    onDelete={(id) => handleDelete(id, 'email-domains')}
                    editable
                />
                <Modal
                    title={t('add-item')}
                    open={isModalVisible}
                    onOk={handleOk}
                    onCancel={handleCancel}
                >
                    <div style={{ marginBottom: 16 }}>
                        <Select
                            value={newItemType}
                            onChange={setNewItemType}
                            style={{ width: '100%' }}
                        >
                            {referenceTypes.map(rt => (
                                <Option key={rt.value} value={rt.value}>{t(rt.labelKey)}</Option>
                            ))}
                        </Select>
                    </div>
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