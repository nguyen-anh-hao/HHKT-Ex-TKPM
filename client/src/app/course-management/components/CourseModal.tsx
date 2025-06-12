'use client';

import { Form, Input, Modal, Button, Select, message, Switch, Spin } from 'antd';
import { useEffect, useState } from 'react';
import { Course } from '../../../interfaces/course/Course';
import { useFaculties } from '@/libs/hooks/reference/useReferences';
import { useTranslations } from 'next-intl';

const { Option } = Select;

interface CourseModalProps {
    visible: boolean;
    onCancel: () => void;
    onSubmit: (value: any) => void;
    course?: Course;
    allCourses: Course[];
    isResetModal?: boolean;
    setIsResetModal?: any;
}

const CourseModal = ({
    visible,
    onCancel,
    onSubmit,
    course,
    allCourses,
    isResetModal,
    setIsResetModal,
}: CourseModalProps) => {
    const [courseForm] = Form.useForm();
    const [isEdit, setIsEdit] = useState<boolean>(false);
    const { data: facultyOptions } = useFaculties();
    const t = useTranslations('course-management');

    const renderOptions = (options?: { key: number; value: string; label: string }[]) =>
        options?.map((option) => (
            <Option key={option.key} value={option.value}>
                {option.label}
            </Option>
        )) ?? null;

    useEffect(() => {
        if (course) {
            courseForm.setFieldsValue(course);
            setIsEdit(true);
        } else {
            // courseForm.resetFields();
            setIsEdit(false);
        }
    }, [course, courseForm]);

    const handleSubmit = () => {
        courseForm
            .validateFields()
            .then((value) => {
                if (course) {
                    if (!course.courseId) {
                        message.error('Không thể cập nhật vì thiếu courseId!');
                        return;
                    }

                    const finalValue = {
                        ...value,
                        courseId: course.courseId,
                    };

                    onSubmit(finalValue);
                } else {
                    onSubmit(value);

                    if (isResetModal) {
                        courseForm.resetFields();
                        setIsResetModal(false);
                    }
                }
            })
            .catch(() => {
                message.error(t('check-info'));
            });
    };

    return (
        <Modal
            title={isEdit ? t('edit-course') : t('add-course')}
            open={visible}
            onCancel={() => {
                onCancel();
                courseForm.resetFields();
            }}
            footer={null}
            width={700}
        >
            <Form form={courseForm} layout="vertical">
                <Form.Item
                    label={t('course-code')}
                    name="courseCode"
                    rules={[{ required: true, message: t('required-course-code') }]}
                >
                    <Input disabled={isEdit} />
                </Form.Item>

                <Form.Item
                    label={t('course-name')}
                    name="courseName"
                    rules={[{ required: true, message: t('required-course-name') }]}
                >
                    <Input />
                </Form.Item>

                <Form.Item
                    label={t('credits')}
                    name="credits"
                    rules={[{ required: true, message: t('required-credits') }]}
                >
                    <Input type="number" min={1} />
                </Form.Item>

                <Form.Item label={t('faculty')} name='faculty' rules={[{ required: true, message: t('required-faculty') }]}>
                    <Select>{renderOptions(facultyOptions)}</Select>
                </Form.Item>

                <Form.Item label={t('description')} name="description">
                    <Input.TextArea rows={3} />
                </Form.Item>

                <Form.Item label={t('prerequisite')} name="prerequisiteCourseId">
                    <Select placeholder={t('select-prerequisite')} allowClear>
                        {allCourses
                            .filter((c) => c.courseId && (!course || c.courseId !== course.courseId))
                            .map((c) => (
                                <Option key={c.courseId} value={c.courseId}>
                                    {c.courseCode} - {c.courseName}
                                </Option>
                            ))}
                    </Select>
                </Form.Item>

                <Form.Item label={t('active')} name="isActive" valuePropName="checked">
                    <Switch checkedChildren={t('active')} unCheckedChildren={t('inactive')} />
                </Form.Item>
            </Form>

            <Button type="primary" onClick={handleSubmit}>
                {t('save')}
            </Button>
        </Modal>
    );
};

export default CourseModal;