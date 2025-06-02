'use client';

import { useEffect, useState } from 'react';
import { Button, message } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import ClassTable from './ClassTable';
import ClassModal from './ClassModal';
import { Class } from '@/interfaces/ClassResponse';
import { addClass as addClassState } from '../actions/ClassActions';
import { useCreateClass } from '@/libs/hooks/useClassMutation';
import useReferenceStore from '@/libs/stores/referenceStore';
import { useTranslations } from 'next-intl';

export default function Home({ initialClasses }: { initialClasses: Class[] }) {
    const [classes, setClasses] = useState<Class[]>(initialClasses);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [selectedClass, setSelectedClass] = useState<Class | null>(null);
    const [isResetModal, setIsResetModal] = useState(false);

    const { mutate: createClass } = useCreateClass();
    const fetchReference = useReferenceStore((state) => state.fetchReference);
    const t = useTranslations('class-management');

    useEffect(() => {
        fetchReference();
    }, [fetchReference]);

    const handleAddOrUpdateClass = (value: Class) => {
        createClass(value, {
            onSuccess: () => {
                message.success(t('add-success'));
                setClasses(addClassState(classes, value));
                setIsModalVisible(false);
            },
            onError: (error: any) => {
                message.error(
                    t('add-error', {
                        error: error.response?.data?.errors?.map((e: any) => e.defaultMessage).join(' ') ||
                            error.response?.data?.message
                    })
                );
            },
        });
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
                >
                    {t('add-class')}
                </Button>
            </div>
            <ClassTable
                classes={classes}
                openModal={setIsModalVisible}
                onEdit={function (classData: Class): void {
                    throw new Error('Function not implemented.');
                }}
                onDelete={function (id: number): void {
                    throw new Error('Function not implemented.');
                }}
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
                allClasses={classes}
            />
        </div>
    );
}
