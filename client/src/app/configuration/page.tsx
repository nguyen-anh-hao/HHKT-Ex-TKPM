'use client'

import React, { useState } from 'react';
import { Form, Input, Select, Button, message } from 'antd';

const { Option } = Select;

const ConfigurationPage: React.FC = () => {
    const [emailDomain, setEmailDomain] = useState('@student.university.edu.vn');
    const [phonePrefix, setPhonePrefix] = useState(['+84', '0']);
    const [studentStatusRules, setStudentStatusRules] = useState<Record<string, string[]>>({
        'Đang học': ['Bảo lưu', 'Tốt nghiệp', 'Đình chỉ'],
        'Đã tốt nghiệp': [],
    });

    const validateEmail = (_: any, value: string) => {
        if (value.endsWith(emailDomain)) {
            return Promise.resolve();
        }
        return Promise.reject(new Error(`Email phải thuộc tên miền ${emailDomain}`));
    };

    const validatePhone = (_: any, value: string) => {
        const regex = new RegExp(`^(${phonePrefix.join('|')})[0-9]{8}$`);
        if (regex.test(value)) {
            return Promise.resolve();
        }
        return Promise.reject(new Error('Số điện thoại không hợp lệ.'));
    };

    const handleSubmit = (values: any) => {
        const { email, phone, statusFrom, statusTo }: { email: string; phone: string; statusFrom: keyof typeof studentStatusRules; statusTo: string } = values;
        if (
            studentStatusRules[statusFrom] &&
            studentStatusRules[statusFrom].includes(statusTo)
        ) {
            message.success('Dữ liệu hợp lệ!');
        } else {
            message.error('Thay đổi tình trạng sinh viên không hợp lệ.');
        }
    };

    return (
        <div style={{ maxWidth: 600, margin: '0 auto', padding: 20 }}>
            <h2>Cấu hình Business Rules</h2>
            <Form layout="vertical" onFinish={handleSubmit}>
                <Form.Item
                    label="Cấu hình đuôi email"
                    name="email"
                >
                    <Input placeholder="Nhập cấu hình đuôi email cho phép" />
                </Form.Item>
                <Form.Item
                    label="Tình trạng sinh viên (Từ)"
                    name="statusFrom"
                    rules={[{ required: true, message: 'Vui lòng chọn tình trạng hiện tại.' }]}
                >
                    <Select placeholder="Chọn tình trạng hiện tại">
                        {Object.keys(studentStatusRules).map((status) => (
                            <Option key={status} value={status}>
                                {status}
                            </Option>
                        ))}
                    </Select>
                </Form.Item>
                <Form.Item
                    label="Tình trạng sinh viên (Đến)"
                    name="statusTo"
                    rules={[{ required: true, message: 'Vui lòng chọn tình trạng mới.' }]}
                >
                    <Select placeholder="Chọn tình trạng mới">
                        {Object.values(studentStatusRules)
                            .flat()
                            .map((status) => (
                                <Option key={status} value={status}>
                                    {status}
                                </Option>
                            ))}
                    </Select>
                </Form.Item>
                <Form.Item>
                    <Button type="primary" htmlType="submit">
                        Xác nhận
                    </Button>
                </Form.Item>
            </Form>
        </div>
    );
};

export default ConfigurationPage;