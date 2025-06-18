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
    useFetchRegistrations
} from '@/libs/hooks/register/useRegisterMutation';
import RegisterTable from './RegisterTable';
import RegisterModal from './RegisterModal';

const Home = () => {
    const t = useTranslations('register-class');
    const [state, setState] = useState<{
        isModalVisible: boolean;
        selectedRegistration: RegisterResponse | null;
        isResetModal: boolean;
    }>({
        isModalVisible: false,
        selectedRegistration: null,
        isResetModal: false
    });

    // Queries
    const { data: allClasses = [] } = useClasses();
    const { data: registrations = [], isLoading } = useFetchRegistrations();

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
                            grade: data.grade === null ? undefined : data.grade 
                        } 
                    },
                    {
                        onSuccess: () => {
                            message.success(t('update-success'));
                            handleModalClose();
                        },
                        onError: (error) => {
                            message.error(error.message || t('update-error'));
                        }
                    }
                );
            } else {
                createMutation.mutate(
                    { 
                        ...data, 
                        grade: data.grade === null ? undefined : data.grade 
                    },
                    {
                        onSuccess: () => {
                            message.success(t('create-success'));
                            handleModalClose();
                        },
                        onError: (error) => {
                            message.error(error.message || t('create-error'));
                        }
                    }
                );
            }
        } catch (error) {
            message.error(error instanceof Error ? error.message : t('error'));
        }
    };

    return (
        <div className="p-6">
            <h1 className="text-2xl font-bold mb-6">{t('register-class')}</h1>
            <Button
                type="primary"
                icon={<PlusOutlined />}
                onClick={handleAdd}
                className="mb-4"
            >
                {t('add-enrollment')}
            </Button>

            <RegisterTable
                registrations={registrations}
                onEdit={handleEdit}
                loading={isLoading}
            />

            <RegisterModal
                visible={state.isModalVisible}
                onCancel={handleModalClose}
                onSubmit={handleSubmit}
                registrationData={state.selectedRegistration ?? undefined}
                allClasses={allClasses}
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