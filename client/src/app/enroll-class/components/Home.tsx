'use client';

import { useState } from 'react';
import { Button, message, Card } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import RegisterTable from './RegisterTable';
import RegisterModal from './RegisterModal';
import { RegisterResponse } from '@/interfaces/RegisterResponse';
import { useTranslations } from 'next-intl';
import { useClasses } from '@/libs/hooks/useClassQuery';

const Home = () => {
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [selectedRegistration, setSelectedRegistration] = useState<RegisterResponse | null>(null);
    const [registrations, setRegistrations] = useState<RegisterResponse[]>([]);
    const [loading, setLoading] = useState(false);
    const [isResetModal, setIsResetModal] = useState(false);
    const t = useTranslations('enroll-class');
    const { data: allClasses = [] } = useClasses();

    const handleAdd = () => {
        setSelectedRegistration(null);
        setIsModalVisible(true);
    };

    const handleEdit = (registration: RegisterResponse) => {
        setSelectedRegistration(registration);
        setIsModalVisible(true);
    };

    const handleModalClose = () => {
        setIsModalVisible(false);
        setSelectedRegistration(null);
    };

    const handleSubmit = async (data: RegisterResponse) => {
        try {
            if (selectedRegistration) {
                // Update existing registration
                const response = await fetch(`/api/registrations/${data.id}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(data),
                });

                if (!response.ok) {
                    throw new Error(t('update-error'));
                }

                setRegistrations(registrations.map(reg => 
                    reg.id === data.id ? data : reg
                ));
                message.success(t('update-success'));
            } else {
                // Create new registration
                const response = await fetch('/api/registrations', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(data),
                });

                if (!response.ok) {
                    throw new Error(t('create-error'));
                }

                const newRegistration = await response.json();
                setRegistrations([...registrations, newRegistration]);
                message.success(t('create-success'));
            }
            handleModalClose();
        } catch (error) {
            message.error(error instanceof Error ? error.message : t('error'));
        }
    };

    return (
        <div>
            <Card>
                <Button
                    type="primary"
                    icon={<PlusOutlined />}
                    onClick={handleAdd}
                    style={{ marginBottom: 16 }}
                >
                    {t('add-enrollment')}
                </Button>
                <RegisterTable
                    registrations={registrations}
                    onEdit={handleEdit}
                    loading={loading}
                />
            </Card>
            <RegisterModal
                visible={isModalVisible}
                onCancel={handleModalClose}
                onSubmit={handleSubmit}
                registrationData={selectedRegistration || undefined}
                allClasses={allClasses}
                isResetModal={isResetModal}
                setIsResetModal={setIsResetModal}
                isUpdating={loading}
            />
        </div>
    );
};

export default Home;