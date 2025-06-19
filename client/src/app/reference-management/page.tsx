'use client';

import { useState, useEffect, useMemo } from 'react';
import { Button, Modal, Input, Typography, Select, message } from 'antd';
import { PlusOutlined, SaveOutlined } from '@ant-design/icons';
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
    // Thêm state theo dõi thay đổi chưa lưu (đưa lên đầu)
    const [isDirty, setIsDirty] = useState(false);

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

    // Track edits
    const [editedFaculties, setEditedFaculties] = useState<{ [key: number]: string }>({});
    const [editedPrograms, setEditedPrograms] = useState<{ [key: number]: string }>({});
    const [editedStatuses, setEditedStatuses] = useState<{ [key: number]: string }>({});
    const [editedEmailDomains, setEditedEmailDomains] = useState<{ [key: number]: string }>({});

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
                    const newItem = { key: response.id, value: newItemValue, label: newItemValue };
                    if (newItemType === 'faculties') setFacultyValues([...facultyValues, newItem]);
                    else if (newItemType === 'programs') setProgramValues([...programValues, newItem]);
                    else if (newItemType === 'student-statuses') setStatusValues([...statusValues, newItem]);
                    else if (newItemType === 'email-domains') setEmailDomainValues([...emailDomainValues, newItem]);
                    setNewItemValue('');
                    setIsModalVisible(false);
                },
                onError: (error: any) => {
                    message.error(`${tMessages('create-error', { entity: t(newItemType) })}: ${error.response.data.message}`);
                },
            }
        );
    };

    const handleCancel = () => {
        setIsModalVisible(false);
        setNewItemValue('');
    };

    // Inline edit handlers (just update local state and track edits)
    const handleEdit = (
        type: ReferenceType,
        index: number,
        newValue: string
    ) => {
        // Đảm bảo luôn set isDirty true khi có thay đổi
        setIsDirty(true);
        if (type === 'faculties') {
            const updated = [...facultyValues];
            updated[index] = { ...updated[index], value: newValue };
            setFacultyValues(updated);
            setEditedFaculties({ ...editedFaculties, [updated[index].key]: newValue });
        } else if (type === 'programs') {
            const updated = [...programValues];
            updated[index] = { ...updated[index], value: newValue };
            setProgramValues(updated);
            setEditedPrograms({ ...editedPrograms, [updated[index].key]: newValue });
        } else if (type === 'student-statuses') {
            const updated = [...statusValues];
            updated[index] = { ...updated[index], value: newValue };
            setStatusValues(updated);
            setEditedStatuses({ ...editedStatuses, [updated[index].key]: newValue });
        } else if (type === 'email-domains') {
            const updated = [...emailDomainValues];
            updated[index] = { ...updated[index], value: newValue };
            setEmailDomainValues(updated);
            setEditedEmailDomains({ ...editedEmailDomains, [updated[index].key]: newValue });
        }
    };

    // Save all edits
    const handleSave = () => {
        const updatePromises: Promise<any>[] = [];
        let updateCount = 0; // Đếm số lượng update thực sự

        Object.entries(editedFaculties).forEach(([id, value]) => {
            updateCount++;
            updatePromises.push(
                new Promise((resolve, reject) => {
                    updateReference(
                        { type: 'faculties', id: Number(id), value },
                        {
                            onSuccess: resolve,
                            onError: reject,
                        }
                    );
                })
            );
        });
        Object.entries(editedPrograms).forEach(([id, value]) => {
            updateCount++;
            updatePromises.push(
                new Promise((resolve, reject) => {
                    updateReference(
                        { type: 'programs', id: Number(id), value },
                        {
                            onSuccess: resolve,
                            onError: reject,
                        }
                    );
                })
            );
        });
        Object.entries(editedStatuses).forEach(([id, value]) => {
            updateCount++;
            updatePromises.push(
                new Promise((resolve, reject) => {
                    updateReference(
                        { type: 'student-statuses', id: Number(id), value },
                        {
                            onSuccess: resolve,
                            onError: reject,
                        }
                    );
                })
            );
        });
        Object.entries(editedEmailDomains).forEach(([id, value]) => {
            updateCount++;
            updatePromises.push(
                new Promise((resolve, reject) => {
                    updateReference(
                        { type: 'email-domains', id: Number(id), value },
                        {
                            onSuccess: resolve,
                            onError: reject,
                        }
                    );
                })
            );
        });

        if (updateCount === 0) {
            message.info(tMessages('no-changes') || 'Không có thay đổi để lưu');
            setIsDirty(false);
            return;
        }

        // Đặt sẵn isDirty về false để tránh closure cũ khi nhiều promise resolve
        setIsDirty(false);

        Promise.allSettled(updatePromises).then((results) => {
            const failed = results.filter(r => r.status === 'rejected');
            if (failed.length === 0) {
                setEditedFaculties({});
                setEditedPrograms({});
                setEditedStatuses({});
                setEditedEmailDomains({});
                message.success(tMessages('update-success', { entity: tCommon('all') }) || 'Đã lưu thành công');
            } else {
                setIsDirty(true); // Nếu có lỗi, cho phép lưu lại
                message.error(tMessages('update-error', { entity: tCommon('all') }) || 'Có lỗi khi lưu');
            }
        });
    };

    return (
        <>
            <h1>{t('title')}</h1>
            <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'flex-start', gap: 16, marginBottom: 16 }}>
                <Button type="primary" icon={<PlusOutlined />} onClick={handleAdd}>
                    {t('add-item')}
                </Button>
                <Button
                    type="default"
                    icon={<SaveOutlined />}
                    onClick={handleSave}
                    disabled={!isDirty}
                >
                    {tCommon('save')}
                </Button>
            </div>
            <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'flex-end', gap: 16, marginBottom: 16 }}>
                {isDirty ? (
                    <span style={{ color: '#ff4d4f', fontWeight: 500 }}>
                        {tCommon('unsaved-changes')}
                    </span>
                ) : (
                    <span style={{ color: '#52c41a', fontWeight: 500 }}>
                        {tCommon('all-saved')}
                    </span>
                )}
            </div>
            <div style={{ display: 'flex', justifyContent: 'center', gap: '40px', padding: '20px', background: '#f8f6f2' }}>
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