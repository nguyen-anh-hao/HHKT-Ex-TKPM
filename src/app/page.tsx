'use client'

import { useState } from 'react';
import { Button, Table, Modal, Form, Input, DatePicker, Select, message } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';
import moment from 'moment';

const { Option } = Select;

const initialData = [
  {
    mssv: '123456',
    fullName: 'Nguyen Van A',
    dob: '2000-01-01',
    gender: 'Nam',
    faculty: 'Khoa Luật',
    course: 'Khóa 2020',
    program: 'Cử nhân Luật',
    address: 'Hà Nội',
    email: 'nguyen.a@example.com',
    phone: '0123456789',
    status: 'Đang học',
  },
  {
    mssv: '654321',
    fullName: 'Tran Thi B',
    dob: '1999-02-02',
    gender: 'Nữ',
    faculty: 'Khoa Tiếng Anh thương mại',
    course: 'Khóa 2019',
    program: 'Cử nhân Tiếng Anh',
    address: 'Hồ Chí Minh',
    email: 'tran.b@example.com',
    phone: '0987654321',
    status: 'Đã tốt nghiệp',
  },
];

const Home = () => {
  const [students, setStudents] = useState(initialData);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [selectedStudent, setSelectedStudent] = useState<any>(null);

  const handleAddStudent = () => {
    setIsModalVisible(true);
    setSelectedStudent(null); // Đặt selectedStudent về null để tạo sinh viên mới
  };

  const handleEditStudent = (record: any) => {
    setSelectedStudent(record); // Lấy dữ liệu của sinh viên cần sửa
    setIsModalVisible(true);
  };

  const handleDeleteStudent = (mssv: string) => {
    setStudents(students.filter(student => student.mssv !== mssv));
  };

  const handleModalCancel = () => {
    setIsModalVisible(false);
    setSelectedStudent(null);
  };

  const handleSubmit = (values: any) => {
    try {
      if (selectedStudent) {
        // Cập nhật sinh viên
        const updatedStudents = students.map(student =>
          student.mssv === selectedStudent.mssv ? { ...student, ...values } : student
        );
        setStudents(updatedStudents);
      } else {
        // Thêm sinh viên mới
        setStudents([...students, values]);
      }
      message.success("Thông tin sinh viên đã được lưu!");
      handleModalCancel();
    } catch (error) {
      message.error("Dữ liệu không hợp lệ!");
    }
  };

  const columns = [
    { title: 'MSSV', dataIndex: 'mssv' },
    { title: 'Họ Tên', dataIndex: 'fullName' },
    {
      title: 'Ngày Sinh',
      dataIndex: 'dob',
      render: (dob: string) => dob ? moment(dob).format('DD/MM/YYYY') : '',
    },
    { title: 'Giới Tính', dataIndex: 'gender' },
    { title: 'Khoa', dataIndex: 'faculty' },
    { title: 'Khóa', dataIndex: 'course' },
    { title: 'Chương Trình', dataIndex: 'program' },
    { title: 'Địa Chỉ', dataIndex: 'address' },
    { title: 'Email', dataIndex: 'email' },
    { title: 'Số Điện Thoại', dataIndex: 'phone' },
    { title: 'Tình Trạng', dataIndex: 'status' },
    {
      title: 'Hành động',
      render: (_: any, record: any) => (
        <>
          <Button icon={<EditOutlined />} onClick={() => handleEditStudent(record)}>Sửa</Button>
          <Button icon={<DeleteOutlined />} danger onClick={() => handleDeleteStudent(record.mssv)}>Xóa</Button>
        </>
      ),
    },
  ];

  return (
    <div>
      <h1>Quản lý sinh viên</h1>
      <Button style={{marginBottom: 16}} type="primary" icon={<PlusOutlined />} onClick={handleAddStudent}>Thêm Sinh Viên</Button>
      <Table columns={columns} dataSource={students} rowKey="mssv" />

      <Modal
        title={selectedStudent ? "Sửa Sinh Viên" : "Thêm Sinh Viên"}
        open={isModalVisible}
        onCancel={handleModalCancel}
        footer={null}
      >
        <Form
          initialValues={selectedStudent ? {
            ...selectedStudent,
            dob: moment(selectedStudent.dob), 
          } : {}}
          onFinish={handleSubmit}
          layout="vertical"
        >
          <Form.Item name="mssv" label="Mã số sinh viên" rules={[{ required: true, message: 'Vui lòng nhập MSSV!' }]}>
            <Input />
          </Form.Item>
          <Form.Item name="fullName" label="Họ Tên" rules={[{ required: true, message: 'Vui lòng nhập họ tên!' }]}>
            <Input />
          </Form.Item>
          <Form.Item name="dob" label="Ngày Sinh">
            <DatePicker style={{ width: '100%' }} />
          </Form.Item>
          <Form.Item name="gender" label="Giới Tính">
            <Select>
              <Option value="Nam">Nam</Option>
              <Option value="Nữ">Nữ</Option>
            </Select>
          </Form.Item>
          <Form.Item name="faculty" label="Khoa" rules={[{ required: true, message: 'Vui lòng chọn khoa!' }]}>
            <Select>
              <Option value="Khoa Luật">Khoa Luật</Option>
              <Option value="Khoa Tiếng Anh thương mại">Khoa Tiếng Anh thương mại</Option>
              <Option value="Khoa Tiếng Nhật">Khoa Tiếng Nhật</Option>
              <Option value="Khoa Tiếng Pháp">Khoa Tiếng Pháp</Option>
            </Select>
          </Form.Item>
          <Form.Item name="course" label="Khóa">
            <Input />
          </Form.Item>
          <Form.Item name="program" label="Chương Trình">
            <Input />
          </Form.Item>
          <Form.Item name="address" label="Địa Chỉ">
            <Input />
          </Form.Item>
          <Form.Item name="email" label="Email" rules={[{ required: true, type: 'email', message: 'Vui lòng nhập email hợp lệ!' }]}>
            <Input />
          </Form.Item>
          <Form.Item name="phone" label="Số điện thoại" rules={[{ required: true, message: 'Vui lòng nhập số điện thoại!' }]}>
            <Input />
          </Form.Item>
          <Form.Item name="status" label="Tình Trạng">
            <Select>
              <Option value="Đang học">Đang học</Option>
              <Option value="Đã tốt nghiệp">Đã tốt nghiệp</Option>
              <Option value="Đã thôi học">Đã thôi học</Option>
              <Option value="Tạm dừng học">Tạm dừng học</Option>
            </Select>
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit">Lưu</Button>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default Home;
