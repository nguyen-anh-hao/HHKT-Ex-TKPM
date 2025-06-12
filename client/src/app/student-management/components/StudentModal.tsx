'use client'

import { Tabs, Form, Input, Button, Modal, Select, Row, Col, DatePicker, Checkbox, message } from 'antd';
import { UserOutlined, MailOutlined, PhoneOutlined, HomeOutlined } from '@ant-design/icons';
import moment from 'moment';
import { useState, useEffect, use } from 'react';
import { Student } from '../../../interfaces/student/Student';
import { useFaculties, usePrograms, useStudentStatuses, useEmailDomains } from '@/libs/hooks/reference/useReferences';
import { useTranslations } from 'next-intl';

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
    const t = useTranslations('student-management');
    const tCommon = useTranslations('common');
    const tValidation = useTranslations('validation');

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
                issuedDate: student.issuedDate ? moment(student.issuedDate) : null,
                expiredDate: student.expiredDate ? moment(student.expiredDate) : null,
            });
            setDocumentType(documents[0]?.documentType || null);
            setIsEdit(true);
        } else {
            studentForm.resetFields();
            setIsEdit(false);
            setDocumentType(null);
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
            label: t('personal-info'),
            children: (
                <Form form={studentForm} layout='vertical'>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item
                                label={t('mssv')}
                                name='studentId'
                                rules={[
                                    { required: true, message: tValidation('required', { field: t('mssv') }) },
                                    ({ getFieldValue }) => ({
                                        validator(_, value) {
                                            if (isEdit) {
                                                return Promise.resolve();
                                            }
                                            if (!value || !student || value !== student.studentId) {
                                                return Promise.resolve();
                                            }
                                            return Promise.reject(new Error(tValidation('duplicate', { field: t('mssv') })));
                                        },
                                    }),
                                ]}
                            >
                                <Input prefix={<UserOutlined />} disabled={!!student} />
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item 
                                label={t('full-name')} 
                                name='fullName' 
                                rules={[{ required: true, message: tValidation('required', { field: t('full-name') }) }]}
                            >
                                <Input />
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item label={t('dob')} name='dob' rules={[{ required: true, message: t('required-dob') }]}>
                                <DatePicker style={{ width: '100%' }} />
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item label={t('gender')} name='gender' rules={[{ required: true, message: t('required-gender') }]}>
                                <Select placeholder={t('select-gender')}>
                                    <Option value='Nam'>{t('male')}</Option>
                                    <Option value='Nữ'>{t('female')}</Option>
                                </Select>
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={6}>
                            <Form.Item label={t('faculty')} name='faculty' rules={[{ required: true, message: t('required-faculty') }]}>
                                <Select>{renderOptions(facultyOptions)}</Select>
                            </Form.Item>
                        </Col>
                        <Col span={6}>
                            <Form.Item label={t('year')} name='intake' rules={[{ required: true, message: t('required-year') }]}>
                                <Input />
                            </Form.Item>
                        </Col>
                        <Col span={6}>
                            <Form.Item label={t('program')} name='program' rules={[{ required: true, message: t('required-program') }]}>
                                <Select>{renderOptions(programOptions)}</Select>
                            </Form.Item>
                        </Col>
                        <Col span={6}>
                            <Form.Item label={t('state')} name='studentStatus' rules={[{ required: true, message: t('required-state') }]}>
                                <Select>{renderOptions(studentStatusOptions)}</Select>
                            </Form.Item>
                        </Col>
                    </Row>
                </Form>
            ),
        },
        {
            key: '2',
            label: t('contact-info'),
            children: (
                <Form form={studentForm} layout='vertical'>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item
                                label={t('permanent-address')}
                                name='permanentAddress'
                            >
                                <Input prefix={<HomeOutlined />} />
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item
                                label={t('temporary-address')}
                                name='temporaryAddress'
                            >
                                <Input prefix={<HomeOutlined />} />
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item label={t('country-code')} name='phoneCountry' rules={[{ required: true, message: t('required-country-code') }]}>
                                <Select placeholder={t('select-country-code')}>
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
                                label={t('phone')}
                                name='phone'
                                rules={[
                                    { required: true, message: tValidation('required', { field: t('phone') }) },
                                    ({ getFieldValue }) => ({
                                        validator(_, value) {
                                            const countryCode = getFieldValue('phoneCountry');
                                            if (!value || !countryCode) return Promise.resolve();
                                            
                                            // Make phone number validation more user-friendly
                                            // Just check for basic format based on country code
                                            const regexMap: Record<string, RegExp> = {
                                                VN: /^\+?84[0-9]{9}$/,
                                                US: /^\+?1[2-9][0-9]{9}$/,
                                                UK: /^\+?44[0-9]{10}$/,
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

                                            const regex = regexMap[countryCode];
                                            // Allow with or without + prefix
                                            const normalizedValue = value.startsWith('+') ? value : `+${value}`;
                                            
                                            if (!regex || regex.test(normalizedValue)) {
                                                return Promise.resolve();
                                            }
                                            return Promise.reject(new Error(tValidation('invalid', { field: t('phone') })));
                                        },
                                    }),
                                ]}
                            >
                                <Input prefix={<PhoneOutlined />} placeholder="+84xxxxxxxxx" />
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item
                                label={t('email')}
                                name='email'
                                rules={[
                                    {
                                        required: true,
                                        message: t('required-email'),
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
                                            return Promise.reject(new Error(t('invalid-email')));
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
            label: t('documents'),
            children: (
                <Form form={studentForm} layout='vertical'>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item
                                label={t('document-type')}
                                name='documentType'
                            >
                                <Select placeholder={t('select-document-type')} onChange={setDocumentType}>
                                    <Option value='CMND'>{t('id-card')}</Option>
                                    <Option value='CCCD'>{t('citizen-id')}</Option>
                                    <Option value='Passport'>{t('passport')}</Option>
                                </Select>
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item
                                label={t('document-number')}
                                name='documentNumber'
                            >
                                <Input />
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item
                                label={t('issue-date')}
                                name='issuedDate'
                            >
                                <DatePicker style={{ width: '100%' }} />
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item
                                label={t('issue-place')}
                                name='issuedBy'
                            >
                                <Input />
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item
                                label={t('expiry-date')}
                                name='expiredDate'
                            >
                                <DatePicker style={{ width: '100%' }} />
                            </Form.Item>
                        </Col>
                        {documentType === 'Passport' && (
                            <Col span={12}>
                                <Form.Item
                                    label={t('issue-country')}
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
                                    <Checkbox>{t('has-chip')}</Checkbox>
                                </Form.Item>
                            </Col>
                        </Row>
                    )}
                    <Row gutter={16}>
                        <Col span={12}>
                            <Form.Item label={t('nationality')} name='nationality' rules={[{ required: true, message: t('required-nationality') }]}>
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
            title={student ? t('edit-student') : t('add-student')}
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
                            message.error(tValidation('check-info'));
                        });
                }}
            >
                {tCommon('save')}
            </Button>
        </Modal>
    );
};

export default StudentModal