'use client'

import { useState, useEffect } from 'react';
import { Button, Table, Modal, Form, Input, DatePicker, Select, message } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';
import moment from 'moment';
import { z } from 'zod'; // Import Zod

const { Option } = Select;

// Định nghĩa schema với Zod
const studentSchema = z.object({
  mssv: z.string().min(1, { message: "Mã số sinh viên không được để trống!" }),
  fullName: z.string().min(1, { message: "Họ tên không được để trống!" }),
  dob: z.string().min(1, { message: "Ngày sinh không được để trống!" }),
  gender: z.string().min(1, { message: "Giới tính không được để trống!" }),
  faculty: z.string().min(1, { message: "Khoa không được để trống!" }),
  course: z.string().min(1, { message: "Khóa không được để trống!" }),
  program: z.string().min(1, { message: "Chương trình không được để trống!" }),
  permanentAddress: z.string().optional(),
  temporaryAddress: z.string().optional(),
  mailAddress: z.string().optional(),
  idCard: z.object({
    type: z.string(),
    number: z.string(),
    issuedDate: z.string(),
    expiredDate: z.string(),
    issuedBy: z.string(),
  }),
  nationality: z.string().min(1, { message: "Quốc tịch không được để trống!" }),
  email: z.string().email({ message: "Email không hợp lệ!" }),
  phone: z.string().min(1, { message: "Số điện thoại không được để trống!" }),
  status: z.string().min(1, { message: "Tình trạng không được để trống!" }),
});

const initialData = [
  {
    mssv: '123456',
    fullName: 'Nguyen Van A',
    dob: '2000-01-01',
    gender: 'Nam',
    faculty: 'Khoa Luật',
    course: 'Khóa 2020',
    program: 'Cử nhân Luật',
    permanentAddress: '',
    temporaryAddress: '',
    mailAddress: '',
    idCard: { type: 'CMND', number: '123456789', issuedDate: '2018-01-01', expiredDate: '2038-01-01', issuedBy: 'Hà Nội' },
    nationality: 'Việt Nam',
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
    permanentAddress: '',
    temporaryAddress: '',
    mailAddress: '',
    idCard: { type: 'CCCD', number: '987654321', issuedDate: '2020-05-15', expiredDate: '2035-05-15', issuedBy: 'Hồ Chí Minh', withChip: true },
    nationality: 'Việt Nam',
    email: 'tran.b@example.com',
    phone: '0987654321',
    status: 'Đã tốt nghiệp',
  },
];

const Home = () => {
  const [students, setStudents] = useState(initialData);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [selectedStudent, setSelectedStudent] = useState<any>(null);
  const [actionStatus, setActionStatus] = useState<{ type: string, message: string } | null>(null);
  const [messageApi, contextHolder] = message.useMessage();

  const handleAddStudent = () => {
    setIsModalVisible(true);
    setSelectedStudent(null);
  };

  const handleEditStudent = (record: any) => {
    setSelectedStudent(record);
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
      // Sử dụng Zod để xác thực dữ liệu
      const parsedData = studentSchema.parse(values);

      // Nếu xác thực thành công, thực hiện hành động
      if (selectedStudent) {
        // Cập nhật sinh viên
        const updatedStudents = students.map(student =>
          student.mssv === selectedStudent.mssv ? { ...student, ...parsedData } : student
        );
        setStudents(updatedStudents);
        setActionStatus({ type: 'success', message: 'Thông tin sinh viên đã được lưu!' });
      } else {
        // Kiểm tra trùng MSSV
        if (students.some(student => student.mssv === parsedData.mssv)) {
          setActionStatus({ type: 'error', message: 'MSSV này đã tồn tại!' });
          return;
        }
        // Thêm sinh viên mới
        setStudents([
          ...students,
          {
            ...parsedData,
            permanentAddress: parsedData.permanentAddress || '',
            temporaryAddress: parsedData.temporaryAddress || '',
            mailAddress: parsedData.mailAddress || '',
          },
        ]);
        setActionStatus({ type: 'success', message: 'Thông tin sinh viên đã được lưu!' });
      }

      handleModalCancel();
    } catch (error) {
      if (error instanceof z.ZodError) {
        // Lỗi xác thực
        setActionStatus({ type: 'error', message: error.errors[0].message });
      } else {
        setActionStatus({ type: 'error', message: 'Dữ liệu không hợp lệ!' });
      }
    }
  };

  // Dùng useEffect để trigger thông báo sau khi actionStatus thay đổi
  useEffect(() => {
    if (actionStatus) {
      if (actionStatus.type === 'success') {
        messageApi.success(actionStatus.message);
      } else if (actionStatus.type === 'error') {
        messageApi.error(actionStatus.message);
      }
    }
  }, [actionStatus]);

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
    { title: 'Tình Trạng', dataIndex: 'status' },
    {
      title: 'Hành động',
      render: (_: any, record: any) => (
        <>
          <Button style={{ marginRight: 8 }} icon={<EditOutlined />} onClick={() => handleEditStudent(record)}>Sửa</Button>
          <Button icon={<DeleteOutlined />} danger onClick={() => handleDeleteStudent(record.mssv)}>Xóa</Button>
        </>
      ),
    },
  ];

  return (
    <div>
      <h1>Quản lý sinh viên</h1>
      <Button style={{ marginBottom: 16 }} type="primary" icon={<PlusOutlined />} onClick={handleAddStudent}>Thêm Sinh Viên</Button>
      <Table columns={columns} dataSource={students} rowKey="mssv" />

      {/* Render the context holder for message */}
      {contextHolder}

      <Modal
        title={selectedStudent ? "Sửa Sinh Viên" : "Thêm Sinh Viên"}
        key={selectedStudent ? selectedStudent.mssv : 'new'}
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
          <Form.Item name="permanentAddress" label="Địa Chỉ Thường Trú">
            <Input />
          </Form.Item>
          <Form.Item name="temporaryAddress" label="Địa Chỉ Tạm Trú">
            <Input />
          </Form.Item>
          <Form.Item name="mailAddress" label="Địa Chỉ Nhận Thư">
            <Input />
          </Form.Item>
          <Form.Item name="idCard" label="Giấy Tờ Chứng Minh Nhân Thân">
            <Select>
              <Option value="CMND">Chứng minh nhân dân (CMND)</Option>
              <Option value="CCCD">Căn cước công dân (CCCD)</Option>
              <Option value="passport">Hộ chiếu (Passport)</Option>
            </Select>
          </Form.Item>
          <Form.Item name="nationality" label="Quốc Tịch">
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
