'use client'

import { Tabs, Form, Input, Button, Modal, Select, Row, Col, DatePicker, Checkbox, message } from 'antd';
import { UserOutlined, MailOutlined, PhoneOutlined, HomeOutlined } from '@ant-design/icons';
import moment from 'moment';
import { useState, useEffect, use } from 'react';
import { Student } from '../../../interfaces/Student';
import { useFaculties, usePrograms, useStudentStatuses, useEmailDomains } from '@/libs/hooks/useReferences';

const { Option } = Select;

interface StudentModalProps {
    visible: boolean;
    onCancel: () => void;
    onSubmit: (value: any) => void;
    student?: Student;
    isResetModal?: boolean;
    setIsResetModal?: any;
}

const StudentModal = ({ visible, onCancel, onSubmit, student, isResetModal, setIsResetModal }: StudentModalProps) => {
    const [studentForm] = Form.useForm();
    const [isEdit, setIsEdit] = useState<boolean>(false);
    const [documentType, setDocumentType] = useState<string | null>(null);

    const { data: facultyOptions } = useFaculties();
    const { data: programOptions } = usePrograms();
    const { data: studentStatusOptions } = useStudentStatuses();
    const { data: emailDomainOptions } = useEmailDomains();

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
                <Form form={studentForm} layout='vertical'>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item
                                label='Mã số sinh viên'
                                name='studentId'
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
                            <Form.Item label='Họ tên' name='fullName' rules={[{ required: true, message: 'Họ tên là bắt buộc!' }]}>
                                <Input />
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item label='Ngày tháng năm sinh' name='dob' rules={[{ required: true, message: 'Ngày tháng năm sinh là bắt buộc!' }]}>
                                <DatePicker style={{ width: '100%' }} />
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item label='Giới tính' name='gender' rules={[{ required: true, message: 'Giới tính là bắt buộc!' }]}>
                                <Select placeholder='Chọn giới tính'>
                                    <Option value='Nam'>Nam</Option>
                                    <Option value='Nữ'>Nữ</Option>
                                </Select>
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={6}>
                            <Form.Item label='Khoa' name='faculty' rules={[{ required: true, message: 'Khoa là bắt buộc!' }]}>
                                <Select>{renderOptions(facultyOptions)}</Select>
                            </Form.Item>
                        </Col>
                        <Col span={6}>
                            <Form.Item label='Khóa' name='intake' rules={[{ required: true, message: 'Khóa là bắt buộc!' }]}>
                                <Input />
                            </Form.Item>
                        </Col>
                        <Col span={6}>
                            <Form.Item label='Chương trình' name='program' rules={[{ required: true, message: 'Chương trình là bắt buộc!' }]}>
                                <Select>{renderOptions(programOptions)}</Select>
                            </Form.Item>
                        </Col>
                        <Col span={6}>
                            <Form.Item label='Tình trạng' name='studentStatus' rules={[{ required: true, message: 'Tình trạng là bắt buộc!' }]}>
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
                <Form form={studentForm} layout='vertical'>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item
                                label='Địa chỉ thường trú'
                                name='permanentAddress'
                            >
                                <Input prefix={<HomeOutlined />} />
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item
                                label='Địa chỉ tạm trú'
                                name='temporaryAddress'
                            >
                                <Input prefix={<HomeOutlined />} />
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item label='Mã quốc gia' name='phoneCountry' rules={[{ required: true, message: 'Mã quốc gia là bắt buộc!' }]}>
                                <Select placeholder='Chọn mã quốc gia'>
                                    <Option value='VN'>🇻🇳 Vietnam (+84)</Option>
                                    <Option value='US'>🇺🇸 USA (+1)</Option>
                                    <Option value='UK'>🇬🇧 UK (+44)</Option>
                                    <Option value='AU'>🇦🇺 Australia (+61)</Option>
                                    <Option value='JP'>🇯🇵 Japan (+81)</Option>
                                    <Option value='DE'>🇩🇪 Germany (+49)</Option>
                                    <Option value='FR'>🇫🇷 France (+33)</Option>
                                    <Option value='IT'>🇮🇹 Italy (+39)</Option>
                                    <Option value='ES'>🇪🇸 Spain (+34)</Option>
                                    <Option value='RU'>🇷🇺 Russia (+7)</Option>
                                    <Option value='CN'>🇨🇳 China (+86)</Option>
                                    <Option value='IN'>🇮🇳 India (+91)</Option>
                                    <Option value='ID'>🇮🇩 Indonesia (+62)</Option>
                                    <Option value='PH'>🇵🇭 Philippines (+63)</Option>
                                    <Option value='MY'>🇲🇾 Malaysia (+60)</Option>
                                    <Option value='BR'>🇧🇷 Brazil (+55)</Option>
                                    <Option value='MX'>🇲🇽 Mexico (+52)</Option>
                                    <Option value='KR'>🇰🇷 South Korea (+82)</Option>
                                    <Option value='CA'>🇨🇦 Canada (+1)</Option>
                                </Select>
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item
                                label='Số điện thoại'
                                name='phone'
                                rules={[
                                    { required: true, message: 'Số điện thoại là bắt buộc!' },
                                    ({ getFieldValue }) => ({
                                        validator(_, value) {
                                            const countryCode = getFieldValue('phoneCountry');
                                            const regexMap: Record<string, RegExp> = {
                                                VN: /^\+84[0-9]{9}$/,
                                                US: /^\+1[2-9][0-9]{9}$/,
                                                UK: /^\+44[0-9]{10}$/,
                                                AU: /^\+61[2-478][0-9]{8}$/,
                                                JP: /^\+81[1-9][0-9]{9}$/,
                                                DE: /^\+49[1-9][0-9]{10}$/,
                                                FR: /^\+33[1-9][0-9]{8}$/,
                                                IT: /^\+39[0-9]{10}$/,
                                                ES: /^\+34[6-9][0-9]{8}$/,
                                                RU: /^\+7[0-9]{10}$/,
                                                CN: /^\+861[3-9][0-9]{9}$/,
                                                IN: /^\+91[6789][0-9]{9}$/,
                                                ID: /^\+62[1-9][0-9]{10}$/,
                                                PH: /^\+63[9][0-9]{9}$/,
                                                MY: /^\+60[1-9][0-9]{8}$/,
                                                BR: /^\+55[1-9][0-9]{10}$/,
                                                MX: /^\+52[1-9][0-9]{9}$/,
                                                KR: /^\+82[1-9][0-9]{8}$/,
                                                CA: /^\+1[2-9][0-9]{9}$/,
                                            };

                                            if (!value || !countryCode || regexMap[countryCode]?.test(value)) {
                                                return Promise.resolve();
                                            }
                                            return Promise.reject(new Error('Số điện thoại không hợp lệ!'));
                                        },
                                    }),
                                ]}
                            >
                                <Input prefix={<PhoneOutlined />} />
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item
                                label='Email'
                                name='email'
                                rules={[
                                    {
                                        required: true,
                                        message: 'Email là bắt buộc!',
                                    },
                                    ({ getFieldValue }) => ({
                                        validator(_, value) {
                                            if (!value) {
                                                return Promise.resolve();
                                            }
                                            const allowedDomains = emailDomainOptions?.map((option: any) => option.value) || [];
                                            const emailDomain = value.split('@')[1];
                                            if (allowedDomains.includes(emailDomain)) {
                                                return Promise.resolve();
                                            }
                                            return Promise.reject(new Error('Email không hợp lệ! Chỉ chấp nhận các domain được phép.'));
                                        },
                                    }),
                                ]}
                            >
                                <Input prefix={<MailOutlined />} />
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
                <Form form={studentForm} layout='vertical'>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item
                                label='Loại giấy tờ'
                                name='documentType'
                            >
                                <Select placeholder='Chọn loại giấy tờ' onChange={setDocumentType}>
                                    <Option value='CMND'>Chứng minh nhân dân (CMND)</Option>
                                    <Option value='CCCD'>Căn cước công dân (CCCD)</Option>
                                    <Option value='Passport'>Hộ chiếu</Option>
                                </Select>
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item
                                label='Số giấy tờ'
                                name='documentNumber'
                            >
                                <Input />
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item
                                label='Ngày cấp'
                                name='issuedDate'
                            >
                                <DatePicker style={{ width: '100%' }} />
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item
                                label='Nơi cấp'
                                name='issuedBy'
                            >
                                <Input />
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item
                                label='Ngày hết hạn'
                                name='expiredDate'
                            >
                                <DatePicker style={{ width: '100%' }} />
                            </Form.Item>
                        </Col>
                        {documentType === 'Passport' && (
                            <Col span={12}>
                                <Form.Item
                                    label='Quốc gia cấp'
                                    name='issuedCountry'
                                >
                                    <Input />
                                </Form.Item>
                            </Col>
                        )}
                    </Row>
                    {documentType === 'CCCD' && (
                        <Row gutter={16}>
                            <Col span={12}>
                                <Form.Item
                                    name='hasChip'
                                    valuePropName='checked'
                                >
                                    <Checkbox>Có gắn chip hay không?</Checkbox>
                                </Form.Item>
                            </Col>
                        </Row>
                    )}
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item label='Quốc tịch' name='nationality' rules={[{ required: true, message: 'Quốc tịch là bắt buộc!' }]}>
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
            title={student ? 'Sửa sinh viên' : 'Thêm sinh viên'}
            open={visible}
            onCancel={() => {
                onCancel();
                studentForm.resetFields();
            }}
            footer={null}
            width={800}
        >
            <Tabs defaultActiveKey='1' items={tabItems} />
            <Button
                type='primary'
                onClick={() => {
                    studentForm.validateFields()
                        .then((value) => {
                            onSubmit(value);
                            if (!isEdit) {
                                if (isResetModal) {
                                    studentForm.resetFields();
                                    setIsResetModal(false);
                                }
                            };
                        })
                        .catch((error) => {
                            message.error('Vui lòng kiểm tra lại thông tin!');
                        });
                }}
            >
                Lưu
            </Button>
        </Modal>
    );
};

export default StudentModal