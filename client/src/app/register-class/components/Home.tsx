'use client';

import { useState } from 'react';
import { Button, message } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import { useTranslations } from 'next-intl';
import { RegisterResponse } from '@/interfaces/register/RegisterResponse';
import { useClasses } from '@/libs/hooks/class/useClassQuery';
import {
    useCreateRegister,
    useUpdateRegister,
} from '@/libs/hooks/register/useRegisterMutation';
import RegisterTable from './RegisterTable';
import RegisterModal from './RegisterModal';

const Home = () => {
    const t = useTranslations('register-class');
    const tCommon = useTranslations('common');
    const tMessages = useTranslations('messages');
    
    const [state, setState] = useState<{
        isModalVisible: boolean;
        selectedRegistration: RegisterResponse | null;
        isResetModal: boolean;
    }>({
        isModalVisible: false,
        selectedRegistration: null,
        isResetModal: false
    });

    // Get all classes for dropdown selection
    const { data: classesData } = useClasses({
        page: 0,
        pageSize: 1000,
        sortField: 'classCode',
        sortOrder: 'asc'
    });

    // Mutations
    const createMutation = useCreateRegister();
    const updateMutation = useUpdateRegister();

    const handleAdd = () => {
        setState(prev => ({
            ...prev,
            selectedRegistration: null,
            isModalVisible: true
        }));
    };

    const handleEdit = (registration: RegisterResponse) => {
        setState(prev => ({
            ...prev,
            selectedRegistration: registration,
            isModalVisible: true
        }));
    };

    const handleModalClose = () => {
        setState(prev => ({
            ...prev,
            isModalVisible: false,
            selectedRegistration: null
        }));
    };

    const handleSubmit = async (data: RegisterResponse) => {
        try {
            if (state.selectedRegistration) {
                updateMutation.mutate(
                    { 
                        id: data.id, 
                        value: { 
                            ...data, 
                            grade: data.grade === undefined ? null : data.grade // Convert undefined to null
                        } 
                    },
                    {
                        onSuccess: () => {
                            message.success(tMessages('update-success', { entity: tCommon('registration').toLowerCase() }));
                            handleModalClose();
                        },
                        onError: (error) => {
                            message.error(`${tMessages('update-error', { entity: tCommon('registration').toLowerCase() })}: ${error.message}`);
                        }
                    }
                );
            } else {
                createMutation.mutate(
                    { 
                        ...data, 
                        grade: data.grade === undefined ? null : data.grade // Convert undefined to null
                    },
                    {
                        onSuccess: () => {
                            message.success(tMessages('create-success', { entity: tCommon('registration').toLowerCase() }));
                            handleModalClose();
                        },
                        onError: (error) => {
                            message.error(`${tMessages('create-error', { entity: tCommon('registration').toLowerCase() })}: ${error.message}`);
                        }
                    }
                );
            }
        } catch (error) {
            message.error(error instanceof Error ? error.message : tCommon('error'));
        }
    };

    return (
        <div>
            <h1>{t('register-class')}</h1>
            <div style={{ display: 'flex', gap: '10px', marginBottom: '20px' }}>
                <Button
                    type="primary"
                    icon={<PlusOutlined />}
                    onClick={handleAdd}
                    className="mb-4"
                >
                    {t('add-enrollment')}
                </Button>
            </div>

            <RegisterTable
                onEdit={handleEdit}
                // Removed registrations and loading props - now managed internally
            />

            <RegisterModal
                visible={state.isModalVisible}
                onCancel={handleModalClose}
                onSubmit={handleSubmit}
                registrationData={state.selectedRegistration ?? undefined}
                allClasses={classesData?.data || []}
                isResetModal={state.isResetModal}
                setIsResetModal={(value: any) =>
                    setState(prev => ({ ...prev, isResetModal: value }))
                }
                isUpdating={createMutation.isPending || updateMutation.isPending}
            />
        </div>
    );
};

export default Home;