import { Table, Button, Input, Tag } from 'antd';
import { EditOutlined } from '@ant-design/icons';
import { useEffect, useState } from 'react';
import { RegisterResponse } from '@/interfaces/RegisterResponse';
import { useTranslations } from 'next-intl';

interface RegisterTableProps {
    registrations: RegisterResponse[];
    onEdit: (registration: RegisterResponse) => void;
    loading?: boolean;
}

const RegisterTable = ({ registrations, onEdit, loading }: RegisterTableProps) => {
    const [searchText, setSearchText] = useState('');
    const t = useTranslations('enroll-class');

    const filteredData = registrations.filter((item) =>
        item.studentId.toLowerCase().includes(searchText.toLowerCase()) ||
        item.studentName?.toLowerCase().includes(searchText.toLowerCase()) ||
        item.classId?.toLowerCase().includes(searchText.toLowerCase()) ||
        item.courseName?.toLowerCase().includes(searchText.toLowerCase())
    );

    const columns = [
        {
            title: t('student-id'),
            dataIndex: 'studentId',
            key: 'studentId',
        },
        {
            title: t('student-name'),
            dataIndex: 'studentName',
            key: 'studentName',
        },
        {
            title: t('class-code'),
            dataIndex: 'classCode',
            key: 'classCode',
        },
        {
            title: t('status'),
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
            title: t('actions'),
            key: 'action',
            render: (_: any, record: RegisterResponse) => (
                <Button
                    icon={<EditOutlined />}
                    onClick={() => onEdit(record)}
                >
                    {t('edit')}
                </Button>
            ),
        },
    ];

    return (
        <div>
            <Input.Search
                placeholder={t('search-placeholder')}
                allowClear
                onChange={(e) => setSearchText(e.target.value)}
                style={{
                    marginBottom: 16,
                    width: 300,
                    float: 'right'
                }}
            />
            <Table columns={columns} dataSource={filteredData} rowKey='id' loading={loading} />
        </div>
    );
};

export default RegisterTable;
