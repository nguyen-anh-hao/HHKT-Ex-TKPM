'use client';

import { useEffect, useState } from 'react';
import { Button, message } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import ClassTable from './ClassTable';
import ClassModal from './ClassModal';
import { Class } from '@/interfaces/class/Class';
import { addClass as addClassState } from '../actions/ClassActions';
import { useCreateClass } from '@/libs/hooks/class/useClassMutation';
import useReferenceStore from '@/libs/stores/referenceStore';
import { useTranslations } from 'next-intl';

const Home = ({ initialClasses }: { initialClasses: Class[] }) => {
    const [classes, setClasses] = useState<Class[]>(initialClasses);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [selectedClass, setSelectedClass] = useState<Class | null>(null);
    const [isResetModal, setIsResetModal] = useState(false);

    const { mutate: createClass } = useCreateClass();
    const fetchReference = useReferenceStore((state) => state.fetchReference);
    const t = useTranslations('class-management');
    const tCommon = useTranslations('common');
    const tMessages = useTranslations('messages');

    useEffect(() => {
        fetchReference();
    }, [fetchReference]);

    const handleAddOrUpdateClass = (value: Class) => {
        createClass(value, {
            onSuccess: () => {
                message.success(tMessages('create-success', { entity: tCommon('class').toLowerCase() }));
                setClasses(addClassState(classes, value));
                setIsModalVisible(false);
            },
            onError: (error: any) => {
                message.error(
                    `${tMessages('create-error', { entity: tCommon('class').toLowerCase() })}: ${error.response?.data?.errors?.map((e: any) => e.defaultMessage).join(' ') ||
                        error.response?.data?.message}`
                );
            },
        });
    };

    const handleDeleteClass = (id: number) => {
        message.info(t('not-implemented'));
        // Implementation would go here
    };

    const handleEditClass = (classData: Class) => {
        message.info(t('not-implemented'));
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
                >
                    {t('add-class')}
                </Button>
            </div>
            <ClassTable
                classes={classes}
                openModal={setIsModalVisible}
                onEdit={handleEditClass}
                onDelete={handleDeleteClass}
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
};

export default Home;
