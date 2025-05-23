import { Form, Input, Modal, Button, Select, message } from 'antd';
import { useEffect, useState } from 'react';
import { Class } from '../../../interfaces/ClassResponse';
import { useLecturers, useCourses } from '@/libs/hooks/useReferences';
import { useTranslations } from 'next-intl';

const { Option } = Select;

interface ClassModalProps {
    visible: boolean;
    onCancel: () => void;
    onSubmit: (value: any) => void;
    classData?: Class;
    allClasses: Class[];
    isResetModal?: boolean;
    setIsResetModal?: any;
}

const ClassModal = ({
    visible,
    onCancel,
    onSubmit,
    classData,
    allClasses,
    isResetModal,
    setIsResetModal,
}: ClassModalProps) => {
    const [classForm] = Form.useForm();
    const [isEdit, setIsEdit] = useState<boolean>(false);
    const { data: lecturerOptions } = useLecturers();
    const { data: courseOptions } = useCourses();
    const t = useTranslations('class-management');

    const renderOptions = (options?: { key: number; value: string; label: string }[]) =>
        options?.map((option) => (
            <Option key={option.key} value={option.value}>
                {option.label}
            </Option>
        )) ?? null;

    useEffect(() => {
        if (classData) {
            classForm.setFieldsValue(classData);
            setIsEdit(true);
        } else {
            setIsEdit(false);
        }
    }, [classData, classForm]);

    const handleSubmit = () => {
        classForm
            .validateFields()
            .then((value) => {
                const finalValue = {
                    ...value,
                    courseId: courseOptions?.find((course: any) => course.value === value.courseName)?.key,
                    lecturerId: lecturerOptions?.find((lecturer: any) => lecturer.value === value.lecturerName)?.key,
                };

                onSubmit(finalValue);
            })
            .catch(() => {
                message.error(t('check-info'));
            });
    };

    return (
        <Modal
            title={isEdit ? t('edit-class') : t('add-class')}
            open={visible}
            onCancel={() => {
                onCancel();
                classForm.resetFields();
            }}
            footer={null}
            width={700}
        >
            <Form form={classForm} layout="vertical">
                <Form.Item
                    label={t('class-code')}
                    name="classCode"
                    rules={[{ required: true, message: t('required-class-code') }]}
                >
                    <Input disabled={isEdit} />
                </Form.Item>

                <Form.Item
                    label={t('semester')}
                    name="semesterId"
                    rules={[{ required: true, message: t('required-semester') }]}
                >
                    <Select placeholder={t('select-semester')}>
                        <Option value={1}>{t('semester-1')}</Option>
                        <Option value={2}>{t('semester-2')}</Option>
                        <Option value={3}>{t('semester-3')}</Option>
                    </Select>
                </Form.Item>

                <Form.Item label={t('lecturer')} name='lecturerName' rules={[{ required: true, message: t('required-lecturer') }]}>
                    <Select>{renderOptions(lecturerOptions)} </Select>
                </Form.Item>

                <Form.Item label={t('course')} name='courseName' rules={[{ required: true, message: t('required-course') }]}>
                    <Select onChange={(value) => {
                        const selectedCourse = courseOptions?.find((course: any) => course.value === value);
                        if (selectedCourse) {
                            classForm.setFieldsValue({
                                courseCode: selectedCourse.code,
                            });
                        }
                    }
                    }>{renderOptions(courseOptions)}</Select>
                </Form.Item>

                <Form.Item name="courseCode" label={t('course-code')}>
                    <Input disabled />
                </Form.Item>

                <Form.Item
                    label={t('max-students')}
                    name="maxStudents"
                    rules={[{ required: true, message: t('required-max-students') }]}
                >
                    <Input type="number" min={1} />
                </Form.Item>

                <Form.Item label={t('schedule')} name="schedule" rules={[{ required: true, message: t('required-schedule') }]}>
                    <Input />
                </Form.Item>

                <Form.Item label={t('room')} name="room" rules={[{ required: true, message: t('required-room') }]}>
                    <Input />
                </Form.Item>
            </Form>

            <Button type="primary" onClick={handleSubmit}>
                {t('save')}
            </Button>
        </Modal>
    );
};

export default ClassModal;
