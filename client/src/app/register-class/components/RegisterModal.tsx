'use client';

import { Form, Input, Modal, Button, Select, message, InputNumber } from 'antd';
import { useEffect, useState } from 'react';
import { RegisterResponse } from '@/interfaces/register/RegisterResponse';
import { Class } from '@/interfaces/class/Class';
import { fetchStudentById } from '@/libs/services/studentService';
import { useTranslations } from 'next-intl';

const { Option } = Select;

interface RegisterModalProps {
    visible: boolean;
    onCancel: () => void;
    onSubmit: (value: any) => void;
    registrationData?: RegisterResponse;
    allClasses: Class[];
    isResetModal?: boolean;
    setIsResetModal?: any;
    isUpdating?: boolean;
}

const RegisterModal = ({
    visible,
    onCancel,
    onSubmit,
    registrationData,
    allClasses,
    isResetModal,
    setIsResetModal,
}: RegisterModalProps) => {
    const [form] = Form.useForm();
    const [isEdit, setIsEdit] = useState(false);
    const [status, setStatus] = useState('REGISTERED');
    const [student, setStudent] = useState('');
    const t = useTranslations('register-class');
    const tCommon = useTranslations('common');

    useEffect(() => {
        if (registrationData) {
            form.setFieldsValue(registrationData);
            setStatus(registrationData.status);
            setIsEdit(true);
        } else {
            // form.resetFields();
            setStatus('REGISTERED');
            setIsEdit(false);
        }
    }, [registrationData, form]);

    useEffect(() => {
        if (student)
            form.setFieldsValue({ studentName: student });
    }, [student, form]);

    const handleSubmit = () => {
        form
            .validateFields()
            .then((value) => {
                const selectedClass = allClasses.find(cls => cls.classCode === value.classCode);

                if (!selectedClass) {
                    message.error(t('class-not-found'));
                    return;
                }

                const selectedClassIndex = allClasses.findIndex(cls => cls.classCode === value.classCode);

                if (selectedClassIndex === -1) {
                    message.error(t('class-not-found'));
                    return;
                }

                const finalValue = {
                    ...(registrationData || {}),
                    ...value,
                    classId: selectedClassIndex + 1,
                }

                onSubmit(finalValue);

                if (!isEdit && isResetModal) {
                    form.resetFields();
                    setIsResetModal(false);
                }
            })
            .catch(() => {
                message.error(t('check-info'));
            });
    };

    return (
        <Modal
            title={isEdit ? t('edit-enrollment') : t('add-enrollment')}
            open={visible}
            onCancel={() => {
                onCancel();
                form.resetFields();
            }}
            footer={null}
            width={700}
        >
            <Form form={form} layout="vertical">
                <Form.Item
                    label={t('student-id')}
                    name="studentId"
                    rules={[{ required: true, message: t('required-student-id') }]}
                >
                    <Input
                        disabled={isEdit}
                        onChange={async (e) => {
                            const studentId = e.target.value;
                            if (studentId) {
                                try {
                                    const studentData = await fetchStudentById(studentId);
                                    setStudent(studentData.fullName);
                                } catch (error) {
                                    // console.error('Error fetching student:', error);
                                }
                            }
                        }}
                    />
                </Form.Item>

                <Form.Item
                    label={t('student-name')}
                    name="studentName"
                >
                    <Input disabled />
                </Form.Item>

                <Form.Item
                    label={t('class-code')}
                    name="classCode"
                    rules={[{ required: true, message: t('required-class-code') }]}
                >
                    <Select disabled={isEdit} placeholder={t('select-class')}>
                        {allClasses.map((cls) => (
                            <Option key={cls.classCode} value={cls.classCode}>
                                {cls.classCode} - {cls.courseName}
                            </Option>
                        ))}
                    </Select>
                </Form.Item>

                <Form.Item
                    label={t('status')}
                    name="status"
                    rules={[{ required: true, message: t('required-status') }]}
                >
                    <Select onChange={(value) => setStatus(value)}>
                        <Option value="REGISTERED">REGISTERED</Option>
                        <Option value="COMPLETED">COMPLETED</Option>
                        <Option value="CANCELLED">CANCELLED</Option>
                    </Select>
                </Form.Item>

                {status === 'COMPLETED' && (
                    <Form.Item
                        label={t('grade')}
                        name="grade"
                        rules={[
                            {
                                required: true,
                                message: t('required-grade'),
                            },
                        ]}
                    >
                        <InputNumber min={0} max={10} step={0.1} style={{ width: '100%' }} />
                    </Form.Item>
                )}
            </Form>

            <Button type="primary" onClick={handleSubmit}>
                {tCommon('save')}
            </Button>
        </Modal>
    );
};

export default RegisterModal;
