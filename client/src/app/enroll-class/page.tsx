"use client";

import { useState } from "react";
import { Form, Input, Button, Select, InputNumber } from "antd";

const { Option } = Select;

const mockCourses = [
  { id: "CS101", name: "Lập trình căn bản" },
  { id: "MATH01", name: "Toán rời rạc" },
  { id: "ENGL01", name: "Tiếng Anh chuyên ngành" },
];

export default function CreateClassPage() {
  const [form] = Form.useForm();

  const handleFinish = (values: any) => {
    console.log("Tạo lớp học:", values);

  };

  const handleFinishFailed = () => {
  };

  return (
    <div className="p-6 max-w-3xl mx-auto">
      <h1 className="text-2xl font-bold mb-6">Tạo lớp học mới</h1>

      <Form
        form={form}
        layout="vertical"
        onFinish={handleFinish}
        onFinishFailed={handleFinishFailed}
        initialValues={{
          year: "2024-2025",
          semester: "1",
          maxSize: 30,
        }}
      >
        <Form.Item
          label="Mã lớp học"
          name="classId"
          rules={[{ required: true, message: "Vui lòng nhập mã lớp học" }]}
        >
          <Input placeholder="VD: L01-CS101" />
        </Form.Item>

        <Form.Item
          label="Khóa học"
          name="courseId"
          rules={[{ required: true, message: "Vui lòng chọn khóa học" }]}
        >
          <Select placeholder="Chọn khóa học">
            {mockCourses.map((course) => (
              <Option key={course.id} value={course.id}>
                {course.id} - {course.name}
              </Option>
            ))}
          </Select>
        </Form.Item>

        <div className="grid grid-cols-2 gap-4">
          <Form.Item label="Năm học" name="year">
            <Input />
          </Form.Item>

          <Form.Item label="Học kỳ" name="semester">
            <Select>
              <Option value="1">Học kỳ 1</Option>
              <Option value="2">Học kỳ 2</Option>
              <Option value="Hè">Học kỳ hè</Option>
            </Select>
          </Form.Item>
        </div>

        <div className="grid grid-cols-2 gap-4">
          <Form.Item label="Giảng viên" name="teacher">
            <Input />
          </Form.Item>

          <Form.Item label="Số lượng tối đa" name="maxSize">
            <InputNumber min={1} className="w-full" />
          </Form.Item>
        </div>

        <Form.Item label="Lịch học" name="schedule">
          <Input placeholder="VD: Thứ 2, 4, 6 - 8:00 ~ 9:30" />
        </Form.Item>

        <Form.Item label="Phòng học" name="room">
          <Input />
        </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit">
            Tạo lớp học
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
}
