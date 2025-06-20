'use client';

import { useEffect, useState } from 'react';
import { Button, message } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import ClassTable from './ClassTable';
import ClassModal from './ClassModal';
import { Class } from '@/interfaces/class/Class';
import { useCreateClass, useUpdateClass, useDeleteClass } from '@/libs/hooks/class/useClassMutation';
import useReferenceStore from '@/libs/stores/referenceStore';
import { useTranslations } from 'next-intl';

const Home = () => {
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [selectedClass, setSelectedClass] = useState<Class | null>(null);
    const [isResetModal, setIsResetModal] = useState(false);

    const { mutate: createClass, isPending: isCreating } = useCreateClass();
    const { mutate: updateClass, isPending: isUpdating } = useUpdateClass();
    const { mutate: deleteClass, isPending: isDeleting } = useDeleteClass();

    const fetchReference = useReferenceStore((state) => state.fetchReference);
    const t = useTranslations('class-management');
    const tCommon = useTranslations('common');
    const tMessages = useTranslations('messages');

    useEffect(() => {
        fetchReference();
    }, [fetchReference]);

    const handleAddOrUpdateClass = (value: Class) => {
        if (selectedClass) {
            updateClass(
                { ...value, id: selectedClass.id },
                {
                    onSuccess: () => {
                        message.success(tMessages('update-success', { entity: tCommon('class').toLowerCase() }));
                        setIsModalVisible(false);
                        setSelectedClass(null);
                        setIsResetModal(true);
                    },
                    onError: (error: any) => {
                        const errorMessage = error.response?.data?.errors
                            ? error.response.data.errors.map((e: any) => e.defaultMessage).join(' ')
                            : error.response?.data?.message || error.message;
                        message.error(`${tMessages('update-error', { entity: tCommon('class').toLowerCase() })}: ${errorMessage}`);
                    },
                }
            );
        } else {
            createClass(value, {
                onSuccess: () => {
                    message.success(tMessages('create-success', { entity: tCommon('class').toLowerCase() }));
                    setIsModalVisible(false);
                },
                onError: (error: any) => {
                    const errorMessage = error.response?.data?.errors
                        ? error.response.data.errors.map((e: any) => e.defaultMessage).join(' ')
                        : error.response?.data?.message || error.message;
                    message.error(`${tMessages('create-error', { entity: tCommon('class').toLowerCase() })}: ${errorMessage}`);
                },
            });
        }
    };

    const handleDeleteClass = (id: number) => {
        deleteClass(id, {
            onSuccess: () => {
                message.success(tMessages('delete-success', { entity: tCommon('class').toLowerCase() }));
            },
            onError: (error: any) => {
                const errorMessage = error.response?.data?.errors
                    ? error.response.data.errors.map((e: any) => e.defaultMessage).join(' ')
                    : error.response?.data?.message || error.message;
                message.error(`${tMessages('delete-error', { entity: tCommon('class').toLowerCase() })}: ${errorMessage}`);
            },
        });
    };

    const handleEditClass = (classData: Class) => {
        setSelectedClass(classData);
        setIsModalVisible(true);
    };

    return (
        <div>
            <h1>{t('class-management')}</h1>
            <div style={{ display: 'flex', gap: '10px', marginBottom: '20px' }}>
                <Button
                    type='primary'
                    icon={<PlusOutlined />}
                    onClick={() => {
                        setSelectedClass(null);
                        setIsModalVisible(true);
                    }}
                    loading={isCreating}
                >
                    {t('add-class')}
                </Button>
            </div>
            <ClassTable
                onEdit={handleEditClass}
                onDelete={handleDeleteClass}
                openModal={setIsModalVisible}
            />
            <ClassModal
                visible={isModalVisible}
                onCancel={() => {
                    setIsModalVisible(false);
                    setSelectedClass(null);
                }}
                onSubmit={handleAddOrUpdateClass}
                classData={selectedClass || undefined}
                isResetModal={isResetModal}
                setIsResetModal={setIsResetModal}
            />
        </div>
    );
};

export default Home;
