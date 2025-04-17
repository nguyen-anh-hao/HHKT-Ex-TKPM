'use client';

import { Form, Input, Modal, Button, Select, message, Switch, Spin } from 'antd';
import { useEffect, useState } from 'react';
import { Course } from '../../../interfaces/Course';
import { useFaculties } from '@/libs/hooks/useReferences';

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
        message.error('Vui lòng kiểm tra lại thông tin!');
      });
  };

  return (
    <Modal
      title={isEdit ? 'Sửa học phần' : 'Thêm học phần'}
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
          label="Mã học phần"
          name="courseCode"
          rules={[{ required: true, message: 'Mã học phần là bắt buộc!' }]}
        >
          <Input disabled={isEdit} />
        </Form.Item>

        <Form.Item
          label="Tên học phần"
          name="courseName"
          rules={[{ required: true, message: 'Tên học phần là bắt buộc!' }]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          label="Số tín chỉ"
          name="credits"
          rules={[{ required: true, message: 'Số tín chỉ là bắt buộc!' }]}
        >
          <Input type="number" min={1} />
        </Form.Item>

        <Form.Item label='Khoa' name='faculty' rules={[{ required: true, message: 'Khoa là bắt buộc!' }]}>
                                <Select>{renderOptions(facultyOptions)}</Select>
                            </Form.Item>

        <Form.Item label="Mô tả" name="description">
          <Input.TextArea rows={3} />
        </Form.Item>

        <Form.Item label="Học phần tiên quyết" name="prerequisiteCourseId">
          <Select placeholder="Chọn học phần tiên quyết" allowClear>
            {allCourses
              .filter((c) => c.courseId && (!course || c.courseId !== course.courseId))
              .map((c) => (
                <Option key={c.courseId} value={c.courseId}>
                  {c.courseCode} - {c.courseName}
                </Option>
              ))}
          </Select>
        </Form.Item>

        <Form.Item label="Tình trạng" name="isActive" valuePropName="checked">
          <Switch checkedChildren="Hoạt động" unCheckedChildren="Không hoạt động" />
        </Form.Item>
      </Form>

      <Button type="primary" onClick={handleSubmit}>
        Lưu
      </Button>
    </Modal>
  );
};

export default CourseModal;
