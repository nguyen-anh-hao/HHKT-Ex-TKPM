'use client';

import { Form, Input, Modal, Button, Select, message, InputNumber } from 'antd';
import { useEffect, useState } from 'react';
import { RegisterResponse } from '@/interfaces/RegisterResponse';
import { Class } from '@/interfaces/ClassResponse';
import { fetchStudentById } from '@/libs/services/studentService';

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
                    message.error('Không tìm thấy lớp học tương ứng!');
                    return;
                }

                const selectedClassIndex = allClasses.findIndex(cls => cls.classCode === value.classCode);

                if (selectedClassIndex === -1) {
                    message.error('Không tìm thấy lớp học tương ứng!');
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
                message.error('Vui lòng kiểm tra lại thông tin!');
            });
    };

    return (
        <Modal
            title={isEdit ? 'Cập nhật đăng ký' : 'Thêm đăng ký lớp học'}
            open={visible}
            onCancel={() => {
                onCancel();
                form.resetFields();
            }}
            footer={null}
            width={600}
        >
            <Form form={form} layout="vertical">
                <Form.Item
                    label="Mã sinh viên"
                    name="studentId"
                    rules={[{ required: true, message: 'Mã sinh viên là bắt buộc!' }]}
                >
                    <Input disabled={isEdit}
                        onBlur={(e) => {
                            const studentId = e.target.value;
                            if (studentId) {
                                fetchStudentById(studentId)
                                    .then((response: any) => {
                                        if (response) {
                                            setStudent(response.fullName);
                                        } else {
                                            message.error('Không tìm thấy sinh viên!');
                                            setStudent('');
                                        }
                                    })
                                    .catch(() => {
                                        message.error('Lỗi khi tìm kiếm sinh viên!');
                                    });
                            } else {
                                setStudent('');
                            }
                        }}
                    />
                </Form.Item>

                <Form.Item label="Tên sinh viên" name="studentName">
                    <Input disabled />
                </Form.Item>

                <Form.Item
                    label="Mã lớp học"
                    name="classCode"
                    rules={[{ required: true, message: 'Mã lớp học là bắt buộc!' }]}
                >
                    <Select disabled={isEdit} placeholder="Chọn mã lớp học">
                        {allClasses.map((cls) => (
                            <Option key={cls.classCode} value={cls.classCode}>
                                {cls.classCode} - {cls.courseName}
                            </Option>
                        ))}
                    </Select>
                </Form.Item>

                <Form.Item
                    label="Trạng thái"
                    name="status"
                    rules={[{ required: true, message: 'Trạng thái là bắt buộc!' }]}
                >
                    <Select onChange={(value) => setStatus(value)}>
                        <Option value="REGISTERED">REGISTERED</Option>
                        <Option value="COMPLETED">COMPLETED</Option>
                        <Option value="CANCELLED">CANCELLED</Option>
                    </Select>
                </Form.Item>

                {status === 'COMPLETED' && (
                    <Form.Item
                        label="Điểm"
                        name="grade"
                        rules={[
                            {
                                required: true,
                                message: 'Vui lòng nhập điểm!',
                            },
                        ]}
                    >
                        <InputNumber min={0} max={10} step={0.1} style={{ width: '100%' }} />
                    </Form.Item>
                )}
            </Form>

            <Button type="primary" onClick={handleSubmit}>
                Lưu
            </Button>
        </Modal>
    );
};

export default RegisterModal;
