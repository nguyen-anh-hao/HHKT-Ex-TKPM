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
    const [studentId, setStudentId] = useState('');
    const t = useTranslations('register-class');
    const tCommon = useTranslations('common');

    useEffect(() => {
        if (registrationData) {
            form.setFieldsValue(registrationData);
            setStatus(registrationData.status);
            setStudentId(registrationData.studentId);
            setStudent(registrationData.studentName);
            setIsEdit(true);
        } else {
            // form.resetFields();
            setStatus('REGISTERED');
            setIsEdit(false);
        }
    }, [registrationData, form]);

    const handleSubmit = () => {
        form
            .validateFields()
            .then((value) => {
                // classId is now directly used from the form
                const finalValue = {
                    ...(registrationData || {}),
                    ...value,
                    studentId: studentId,
                    studentName: student
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
                    label={t('student-info')}
                    required
                >
                    <Input.Group compact>
                        <Form.Item
                            name="studentId"
                            noStyle
                            rules={[
                                { required: true, message: t('required-student-id') },
                                {
                                    validator: async (_, value) => {
                                        if (!value) return Promise.resolve();
                                        try {
                                            const studentData = await fetchStudentById(value);
                                            if (!studentData || !studentData.fullName) {
                                                return Promise.reject(new Error(t('student-not-found')));
                                            }
                                            return Promise.resolve();
                                        } catch {
                                            return Promise.reject(new Error(t('student-not-found')));
                                        }
                                    }
                                }
                            ]}
                        >
                            <Input
                                style={{ width: '30%' }}
                                placeholder={t('student-id')}
                                disabled={isEdit}
                                onChange={async (e) => {
                                    const newStudentId = e.target.value;
                                    setStudentId(newStudentId);
                                    form.setFieldsValue({ studentId: newStudentId });
                                    if (newStudentId) {
                                        try {
                                            const studentData = await fetchStudentById(newStudentId);
                                            setStudent(studentData.fullName);
                                        } catch (error) {
                                            setStudent('');
                                        }
                                    } else {
                                        setStudent('');
                                    }
                                }}
                                value={studentId}
                            />
                        </Form.Item>
                        <Input
                            style={{ width: '70%' }}
                            placeholder={t('student-name')}
                            value={student ? `${studentId} - ${student}` : ''}
                            disabled
                        />
                    </Input.Group>
                </Form.Item>

                <Form.Item
                    label={t('class-code')}
                    name="classId"
                    rules={[{ required: true, message: t('required-class-code') }]}
                >
                    <Select disabled={isEdit} placeholder={t('select-class')}>
                        {allClasses.map((cls) => (
                            <Option key={cls.id} value={cls.id}>
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
                        <Option value="REGISTERED">{t("REGISTERED")}</Option>
                        <Option value="COMPLETED">{t("COMPLETED")}</Option>
                        <Option value="CANCELLED">{t("CANCELLED")}</Option>
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