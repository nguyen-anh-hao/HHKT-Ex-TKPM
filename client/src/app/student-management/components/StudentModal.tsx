// components/StudentModal.tsx
import { Tabs, Form, Input, Button, Modal, Select, Row, Col, DatePicker, Checkbox } from 'antd';
import { UserOutlined, MailOutlined, PhoneOutlined, HomeOutlined } from '@ant-design/icons';
import { Student } from "../../../interfaces/student/state.interface";

import moment from "moment";
import { useState, useEffect } from "react";

import useReferenceDataStore from "../../../lib/stores/referenceDataStore";

const { Option } = Select;

interface StudentModalProps {
    visible: boolean;
    onCancel: () => void;
    onSubmit: (values: any) => void;
    student?: Student;
}

const StudentModal = ({ visible, onCancel, onSubmit, student }: StudentModalProps) => {
    const [studentForm] = Form.useForm();
    const [isEdit, setIsEdit] = useState<boolean | null>(null);
    const [documentType, setDocumentType] = useState<string | null>(student?.documentType || null);

    const { facultyOptions } = useReferenceDataStore() as { facultyOptions: { value: string; label: string }[] };
    const { programOptions } = useReferenceDataStore() as { programOptions: { value: string; label: string }[] };
    const { studentStatusOptions } = useReferenceDataStore() as { studentStatusOptions: { value: string; label: string }[] };

    useEffect(() => {
        if (student) {
            studentForm.setFieldsValue({ ...student, dob: moment(student.dob) });
            setDocumentType(student.documentType);
            setIsEdit(true);
        } else {
            studentForm.resetFields();
            setIsEdit(false);
        }
    }, [student, studentForm]);

    const tabItems = [
        {
            key: '1',
            label: 'Thông tin cá nhân và học tập',
            children: (
                <Form
                    form={studentForm}
                    initialValues={student ? {} : {}}
                    layout="vertical"
                >
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item label="Mã số sinh viên" name="studentId" rules={[{ required: true, message: 'Mã số sinh viên là bắt buộc!' }]}>
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
                                <Select>
                                    {facultyOptions.map((faculty: any) => (
                                        <Option key={faculty.value} value={faculty.value}>{faculty.label}</Option>
                                    ))}
                                </Select>
                            </Form.Item>
                        </Col>
                        <Col span={6}>
                            <Form.Item label="Khóa" name="intake" rules={[{ required: true, message: 'Khóa là bắt buộc!' }]}>
                                <Input />
                            </Form.Item>
                        </Col>
                        <Col span={6}>
                            <Form.Item label="Chương trình" name="program" rules={[{ required: true, message: 'Chương trình là bắt buộc!' }]}>
                                <Select>
                                    {programOptions.map((program: any) => (
                                        <Option key={program.value} value={program.value}>{program.label}</Option>
                                    ))}
                                </Select>
                            </Form.Item>
                        </Col>
                        <Col span={6}>
                            <Form.Item label="Tình trạng" name="status" rules={[{ required: true, message: 'Tình trạng là bắt buộc!' }]}>
                                <Select placeholder="Chọn tình trạng">
                                    {studentStatusOptions.map((studentStatus: any) => (
                                        <Option key={studentStatus.value} value={studentStatus.value}>{studentStatus.label}</Option>
                                    ))}
                                </Select>
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
                <Form
                    form={studentForm}
                    initialValues={student ? { ...student, dob: moment(student.dob) } : {}}
                    layout="vertical"
                >
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item label="Địa chỉ thường trú" name="permanentAddress">
                                <Input prefix={<HomeOutlined />} />
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item label="Địa chỉ tạm trú" name="temporaryAddress">
                                <Input prefix={<HomeOutlined />} />
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item label="Email" name="email" rules={[{ type: 'email', message: 'Email không hợp lệ!' }]}>
                                <Input prefix={<MailOutlined />} />
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item label="Số điện thoại" name="phone" rules={[{ pattern: /^[0-9]{10}$/, message: 'Số điện thoại không hợp lệ!' }]}>
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
                <Form
                    form={studentForm}
                    initialValues={student ? { ...student, dob: moment(student.dob) } : {}}
                    layout="vertical"
                >
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item label="Loại giấy tờ" name="documentType">
                                <Select placeholder="Chọn loại giấy tờ" onChange={(value) => setDocumentType(value)}>
                                    <Option value="CMND">Chứng minh nhân dân (CMND)</Option>
                                    <Option value="CCCD">Căn cước công dân (CCCD)</Option>
                                    <Option value="Passport">Hộ chiếu</Option>
                                </Select>
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item label="Số giấy tờ" name="documentNumber">
                                <Input />
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item label="Ngày cấp" name="issuedDate">
                                <DatePicker style={{ width: '100%' }} />
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item label="Nơi cấp" name="issuedBy">
                                <Input />
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item label="Ngày hết hạn" name="expiredDate">
                                <DatePicker style={{ width: '100%' }} />
                            </Form.Item>
                        </Col>
                        {documentType === "Passport" && (
                            <Col span={12}>
                                <Form.Item label="Quốc gia cấp" name="issuedCountry">
                                    <Input />
                                </Form.Item>
                            </Col>
                        )}
                    </Row>
                    <Row gutter={16}>
                        {documentType === "CCCD" && (
                            <Col span={12}>
                                <Form.Item name="hasChip" valuePropName='checked'>
                                    <Checkbox>Có gắn chip hay không?</Checkbox>
                                </Form.Item>
                            </Col>
                        )}
                    </Row>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item label="Quốc tịch" name="nationality">
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
            <Button onClick={() => {
                studentForm.validateFields().then((values) => {
                    onSubmit(values);
                    if (!isEdit) {
                        studentForm.resetFields();
                    }
                });
            }} type="primary" htmlType="submit">Lưu</Button>
        </Modal>
    );
};

export default StudentModal;