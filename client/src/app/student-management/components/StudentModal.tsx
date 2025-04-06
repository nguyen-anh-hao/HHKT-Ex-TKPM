'use client'

import { Tabs, Form, Input, Button, Modal, Select, Row, Col, DatePicker, Checkbox } from 'antd';
import { UserOutlined, MailOutlined, PhoneOutlined, HomeOutlined } from '@ant-design/icons';
import moment from "moment";
import { useState, useEffect } from "react";
import { Student } from "../../../interfaces/Student";
import { useFaculties, usePrograms, useStudentStatuses } from "@/libs/hooks/useReferences";

const { Option } = Select;

interface StudentModalProps {
    visible: boolean;
    onCancel: () => void;
    onSubmit: (values: any) => void;
    student?: Student;
}

const StudentModal = ({ visible, onCancel, onSubmit, student }: StudentModalProps) => {
    const [studentForm] = Form.useForm();
    const [isEdit, setIsEdit] = useState<boolean>(false);
    const [documentType, setDocumentType] = useState<string | null>(null);

    const { data: facultyOptions } = useFaculties();
    const { data: programOptions } = usePrograms();
    const { data: studentStatusOptions } = useStudentStatuses();

    useEffect(() => {
        if (student) {
            const documents = student.documents || [];
            studentForm.setFieldsValue({
                ...student,
                dob: moment(student.dob),
                issuedDate: moment(student.issuedDate),
                expiredDate: moment(student.expiredDate),
            });
            setDocumentType(documents[0]?.documentType || null);
            setIsEdit(true);
        } else {
            // studentForm.resetFields();
            setIsEdit(false);
        }
    }, [student, studentForm]);

    const renderOptions = (options?: { key: number; value: string; label: string }[]) =>
        options?.map((option) => (
            <Option key={option.key} value={option.value}>
                {option.label}
            </Option>
        )) ?? null;

    const tabItems = [
        {
            key: '1',
            label: 'Thông tin cá nhân và học tập',
            children: (
                <Form form={studentForm} layout="vertical">
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item
                                label="Mã số sinh viên"
                                name="studentId"
                                rules={[
                                    { required: true, message: 'Mã số sinh viên là bắt buộc!' },
                                    ({ getFieldValue }) => ({
                                        validator(_, value) {
                                            if (isEdit) {
                                                return Promise.resolve();
                                            }
                                            if (!value || !student || value !== student.studentId) {
                                                return Promise.resolve();
                                            }
                                            return Promise.reject(new Error('Mã số sinh viên không được trùng!'));
                                        },
                                    }),
                                ]}
                            >
                                <Input prefix={<UserOutlined />} disabled={!!student} />
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item label="Họ tên" name="fullName" rules={[{ required: true, message: 'Họ tên là bắt buộc!' }]}>
                                <Input />
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item label="Ngày tháng năm sinh" name="dob" rules={[{ required: true, message: 'Ngày tháng năm sinh là bắt buộc!' }]}>
                                <DatePicker style={{ width: '100%' }} />
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item label="Giới tính" name="gender" rules={[{ required: true, message: 'Giới tính là bắt buộc!' }]}>
                                <Select placeholder="Chọn giới tính">
                                    <Option value="Nam">Nam</Option>
                                    <Option value="Nữ">Nữ</Option>
                                </Select>
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={6}>
                            <Form.Item label="Khoa" name="faculty" rules={[{ required: true, message: 'Khoa là bắt buộc!' }]}>
                                <Select>{renderOptions(facultyOptions)}</Select>
                            </Form.Item>
                        </Col>
                        <Col span={6}>
                            <Form.Item label="Khóa" name="intake" rules={[{ required: true, message: 'Khóa là bắt buộc!' }]}>
                                <Input />
                            </Form.Item>
                        </Col>
                        <Col span={6}>
                            <Form.Item label="Chương trình" name="program" rules={[{ required: true, message: 'Chương trình là bắt buộc!' }]}>
                                <Select>{renderOptions(programOptions)}</Select>
                            </Form.Item>
                        </Col>
                        <Col span={6}>
                            <Form.Item label="Tình trạng" name="studentStatus" rules={[{ required: true, message: 'Tình trạng là bắt buộc!' }]}>
                                <Select>{renderOptions(studentStatusOptions)}</Select>
                            </Form.Item>
                        </Col>
                    </Row>
                </Form>
            ),
        },
        {
            key: '2',
            label: 'Liên hệ và Địa chỉ',
            children: (
                <Form form={studentForm} layout="vertical">
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item
                                label="Địa chỉ thường trú"
                                name="permanentAddress"
                            >
                                <Input prefix={<HomeOutlined />} />
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item
                                label="Địa chỉ tạm trú"
                                name="temporaryAddress"
                            >
                                <Input prefix={<HomeOutlined />} />
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item
                                label="Email"
                                name="email"
                                rules={[
                                    {
                                        required: true,
                                        type: 'email',
                                        message: 'Email không hợp lệ!'
                                    },
                                    {
                                        pattern: /^[a-zA-Z0-9._%+-]+@example\.com$/,
                                        message: 'Email phải có đuôi @example.com!'
                                    }
                                ]}
                            >
                                <Input prefix={<MailOutlined />} />
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item label="Số điện thoại" name="phone" rules={[{ required: true, pattern: /^\+84[0-9]{9}$/, message: 'Số điện thoại không hợp lệ! Số điện thoại phải bắt đầu bằng +84 và có 9 chữ số tiếp theo.' }]}>
                                <Input prefix={<PhoneOutlined />} />
                            </Form.Item>
                        </Col>
                    </Row>
                </Form>
            ),
        },
        {
            key: '3',
            label: 'Giấy tờ và Quốc tịch',
            children: (
                <Form form={studentForm} layout="vertical">
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item
                                label="Loại giấy tờ"
                                name="documentType"
                            >
                                <Select placeholder="Chọn loại giấy tờ" onChange={setDocumentType}>
                                    <Option value="CMND">Chứng minh nhân dân (CMND)</Option>
                                    <Option value="CCCD">Căn cước công dân (CCCD)</Option>
                                    <Option value="Passport">Hộ chiếu</Option>
                                </Select>
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item
                                label="Số giấy tờ"
                                name="documentNumber"
                            >
                                <Input />
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item
                                label="Ngày cấp"
                                name="issuedDate"
                            >
                                <DatePicker style={{ width: '100%' }} />
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item
                                label="Nơi cấp"
                                name="issuedBy"
                            >
                                <Input />
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item
                                label="Ngày hết hạn"
                                name="expiredDate"
                            >
                                <DatePicker style={{ width: '100%' }} />
                            </Form.Item>
                        </Col>
                        {documentType === "Passport" && (
                            <Col span={12}>
                                <Form.Item
                                    label="Quốc gia cấp"
                                    name="issuedCountry"
                                >
                                    <Input />
                                </Form.Item>
                            </Col>
                        )}
                    </Row>
                    {documentType === "CCCD" && (
                        <Row gutter={16}>
                            <Col span={12}>
                                <Form.Item
                                    name="hasChip"
                                    valuePropName="checked"
                                >
                                    <Checkbox>Có gắn chip hay không?</Checkbox>
                                </Form.Item>
                            </Col>
                        </Row>
                    )}
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item label="Quốc tịch" name="nationality" rules={[{ required: true, message: 'Quốc tịch là bắt buộc!' }]}>
                                <Input />
                            </Form.Item>
                        </Col>
                    </Row>
                </Form>
            ),
        },
    ];

    return (
        <Modal
            title={student ? "Sửa sinh viên" : "Thêm sinh viên"}
            open={visible}
            onCancel={() => {
                onCancel();
                studentForm.resetFields();
            }}
            footer={null}
            width={800}
        >
            <Tabs defaultActiveKey="1" items={tabItems} />
            <Button
                type="primary"
                onClick={() => {
                    studentForm.validateFields().then((values) => {
                        onSubmit(values);
                        if (!isEdit) studentForm.resetFields();
                    });
                }}
            >
                Lưu
            </Button>
        </Modal>
    );
};

export default StudentModal

// problem: id, value, label
// table data will be show as type value
// but modal will be show as type label and save as type value
// and when we post data to api, we need to convert it back to id
// solution: use key? không phải vấn đề của mình, mà vấn đề từ API khi mà trả ra name chứ không phải id
// giải pháp tạm thời: transform và ràng buộc không được trùng tên trong reference

// reference data
// import file
// configuration