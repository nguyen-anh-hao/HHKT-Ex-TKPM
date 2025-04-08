'use client';

import {
  Table,
  Button,
  Input,
  Tag,
  Popconfirm,
  Modal,
  Form,
  Input as AntInput,
  InputNumber,
  Select,
} from 'antd';
import { EditOutlined, DeleteOutlined, PlusOutlined } from '@ant-design/icons';
import { useEffect, useState } from 'react';

type Course = {
  id: string;
  name: string;
  credits: number;
  department: string;
  description: string;
  prerequisiteId?: string;
  isActive: boolean;
};

const mockCourses: Course[] = [
  { id: 'CS101', name: 'Lập trình căn bản', credits: 3, department: 'CNTT', description: '', isActive: true },
  { id: 'CS102', name: 'Cấu trúc dữ liệu', credits: 4, department: 'CNTT', description: '', prerequisiteId: 'CS101', isActive: true },
  { id: 'MATH01', name: 'Toán rời rạc', credits: 3, department: 'Toán', description: '', isActive: true },
];

const departments = ['CNTT', 'Toán', 'Kinh tế']; // Có thể lấy từ API

const CourseTable = () => {
  const [courses, setCourses] = useState<Course[]>([]);
  const [search, setSearch] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [form] = Form.useForm();

  useEffect(() => {
    setCourses(mockCourses);
  }, []);

  const handleDelete = (id: string) => {
    const updated = courses.map(course =>
      course.id === id ? { ...course, isActive: false } : course
    );
    setCourses(updated);
  };

  const handleAddCourse = (values: Course) => {
    setCourses(prev => [...prev, { ...values, isActive: true }]);
    setIsModalOpen(false);
    form.resetFields();
  };

  const filteredCourses = courses.filter(
    (c) =>
      c.id.toLowerCase().includes(search.toLowerCase()) ||
      c.name.toLowerCase().includes(search.toLowerCase())
  );

  const columns = [
    { title: 'Mã', dataIndex: 'id' },
    { title: 'Tên khóa học', dataIndex: 'name' },
    { title: 'Số tín chỉ', dataIndex: 'credits' },
    { title: 'Khoa', dataIndex: 'department' },
    {
      title: 'Môn tiên quyết',
      dataIndex: 'prerequisiteId',
      render: (id: string) => id || '—',
    },
    {
      title: 'Trạng thái',
      dataIndex: 'isActive',
      render: (active: boolean) =>
        active ? <Tag color="green">Đang mở</Tag> : <Tag color="red">Ngưng mở</Tag>,
    },
    {
      title: 'Hành động',
      render: (_: any, record: Course) => (
        <>
          <Button icon={<EditOutlined />} style={{ marginRight: 8 }}>
            Sửa
          </Button>
          <Popconfirm
            title="Bạn có chắc chắn muốn xóa khoá học này?"
            onConfirm={() => handleDelete(record.id)}
            okText="Xoá"
            cancelText="Hủy"
          >
            <Button icon={<DeleteOutlined />} danger>
              Xoá
            </Button>
          </Popconfirm>
        </>
      ),
    },
  ];

  return (
    <div>
      <div style={{ marginBottom: 16, display: 'flex', justifyContent: 'space-between' }}>
        <Button
          type="primary"
          icon={<PlusOutlined />}
          onClick={() => setIsModalOpen(true)}
        >
          Thêm khóa học
        </Button>
        <Input.Search
          placeholder="Tìm theo mã / tên khóa học"
          onChange={(e) => setSearch(e.target.value)}
          style={{ width: 300 }}
          allowClear
        />
      </div>

      <Table columns={columns} dataSource={filteredCourses} rowKey="id" />

      <Modal
        title="Thêm khóa học mới"
        open={isModalOpen}
        onCancel={() => setIsModalOpen(false)}
        onOk={() => form.submit()}
        okText="Lưu"
        cancelText="Hủy"
      >
        <Form layout="vertical" form={form} onFinish={handleAddCourse}>
          <Form.Item
            label="Mã khóa học"
            name="id"
            rules={[{ required: true, message: 'Vui lòng nhập mã khóa học' }]}
          >
            <AntInput />
          </Form.Item>

          <Form.Item
            label="Tên khóa học"
            name="name"
            rules={[{ required: true, message: 'Vui lòng nhập tên khóa học' }]}
          >
            <AntInput />
          </Form.Item>

          <Form.Item
            label="Số tín chỉ"
            name="credits"
            rules={[{ required: true, message: 'Vui lòng nhập số tín chỉ' }]}
          >
            <InputNumber min={1} max={10} style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item
            label="Khoa"
            name="department"
            rules={[{ required: true, message: 'Vui lòng chọn khoa' }]}
          >
            <Select options={departments.map(d => ({ label: d, value: d }))} />
          </Form.Item>

          <Form.Item label="Môn tiên quyết" name="prerequisiteId">
            <Select
              allowClear
              placeholder="Chọn mã môn tiên quyết (nếu có)"
              options={courses.map(course => ({ label: course.name, value: course.id }))}
            />
          </Form.Item>

          <Form.Item label="Mô tả" name="description">
            <AntInput.TextArea rows={3} />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default CourseTable;
