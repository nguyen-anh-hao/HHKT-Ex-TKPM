'use client';

import { useState } from 'react';
import { Button, message } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import RegisterTable from './RegisterTable';
import RegisterModal from './RegisterModal';
import { RegisterResponse } from '@/interfaces/RegisterResponse';
import {
  updateRegister as updateRegisterState,
  addRegister as addRegisterState,
} from '../actions/RegisterActions';
import {
  useCreateRegister,
  useUpdateRegister,
} from '@/libs/hooks/useRegisterMutation';
import { useClasses } from '@/libs/hooks/useClassQuery'; 

interface RegisterHomeProps {
  initialRegisters: RegisterResponse[];
}

export default function RegisterHome({ initialRegisters }: RegisterHomeProps) {
  const [registers, setRegisters] = useState<RegisterResponse[]>(initialRegisters);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [selectedRegister, setSelectedRegister] = useState<RegisterResponse | null>(null);
  const [isResetModal, setIsResetModal] = useState(false);

  const { data: allClasses = [] } = useClasses();

  const { mutate: createRegister, isPending: isCreating } = useCreateRegister();
  const { mutate: updateRegister, isPending: isUpdating } = useUpdateRegister();

  const handleAddOrUpdateRegister = (value: RegisterResponse) => {
    if (selectedRegister) {
      // If it's an update
      updateRegister({ id: selectedRegister.id, value: { ...value, grade: value.grade ?? undefined } }, {
        onSuccess: () => {
          message.success('Cập nhật đăng ký thành công');
          setRegisters(updateRegisterState(registers, value));
          setIsResetModal(true);
        },
        onError: (error: any) => {
          message.error(
            `Cập nhật thất bại: ${
              error.response?.data?.errors?.map((e: any) => e.defaultMessage).join(' ') ||
              error.response?.data?.message
            }`
          );
        }
      });
    } else {

      createRegister({ ...value, grade: value.grade ?? undefined }, {
        onSuccess: () => {
          message.success('Thêm đăng ký thành công');
          setRegisters(addRegisterState(registers, value));
        },
        onError: (error: any) => {
          message.error(
            `Thêm thất bại: ${
              error.response?.data?.errors?.map((e: any) => e.defaultMessage).join(' ') ||
              error.response?.data?.message
            }`
          );
        }
      });
    }
  };

  return (
    <div>
      <h1>Quản lý đăng ký lớp học</h1>
      <div style={{ display: 'flex', gap: '10px', marginBottom: '20px' }}>
        <Button
          type="primary"
          icon={<PlusOutlined />}
          onClick={() => {
            setSelectedRegister(null); 
            setIsModalVisible(true); 
          }}
          loading={isCreating} 
        >
          Thêm đăng ký
        </Button>
      </div>

      <RegisterTable
  registrations={registers}
  onEdit={(register) => {
    setSelectedRegister(register);      
    setIsModalVisible(true);           
  }}
  loading={isUpdating}
/>
      <RegisterModal
        visible={isModalVisible}
        onCancel={() => {
          setIsModalVisible(false);
          setSelectedRegister(null); 
        }}
        onSubmit={handleAddOrUpdateRegister}
        registrationData={selectedRegister || undefined}
        allClasses={allClasses}
        isResetModal={isResetModal}
        setIsResetModal={setIsResetModal}
        isUpdating={isUpdating}
      />
    </div>
  );
}
