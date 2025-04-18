import { Table, Button, Input, Tag, Popconfirm } from 'antd';
import { EditOutlined } from '@ant-design/icons';
import { useState } from 'react';
import { RegisterResponse } from '@/interfaces/RegisterResponse';

interface RegisterTableProps {
  registrations: RegisterResponse[];
  onEdit: (data: RegisterResponse) => void;
  loading?: boolean;
}

const RegisterTable = ({ registrations, onEdit, loading }: RegisterTableProps) => {
  const [searchText, setSearchText] = useState('');

  const filteredData = registrations.filter((item) =>
    item.studentId.toLowerCase().includes(searchText.toLowerCase()) ||
    item.studentName?.toLowerCase().includes(searchText.toLowerCase()) ||
    item.classId?.toLowerCase().includes(searchText.toLowerCase()) ||
    item.courseName?.toLowerCase().includes(searchText.toLowerCase())
  );

  const columns = [
    {
      title: 'MSSV',
      dataIndex: 'studentId',
      key: 'studentId',
    },
    {
      title: 'Họ tên',
      dataIndex: 'studentName',
      key: 'studentName',
    },
    {
      title: 'Mã lớp',
      dataIndex: 'classCode',
      key: 'classCode',
    },
    {
      title: 'Trạng thái',
      dataIndex: 'status',
      key: 'status',
      render: (status: string) => {
        let color = 'blue';
        if (status === 'COMPLETED') color = 'green';
        else if (status === 'CANCELLED') color = 'red';
        return <Tag color={color}>{status}</Tag>;
      },
    },
    {
      title: 'Hành động',
      key: 'action',
      render: (_: any, record: RegisterResponse) => (
        <Button
          icon={<EditOutlined />}
          onClick={() => onEdit(record)}
        >
          Sửa
        </Button>
      ),
    },
  ];

  return (
    <div>
      <Input.Search
        placeholder="Tìm theo MSSV, họ tên, mã lớp, tên môn học"
        allowClear
        onChange={(e) => setSearchText(e.target.value)}
        style={{ marginBottom: 16, width: 350, float: 'right' }}
      />
      <Table
        columns={columns}
        dataSource={filteredData}
        rowKey={(record) => `${record.studentId}-${record.classId}`} 
        pagination={{ pageSize: 10 }}
        loading={loading}
      />
    </div>
  );
};

export default RegisterTable;
