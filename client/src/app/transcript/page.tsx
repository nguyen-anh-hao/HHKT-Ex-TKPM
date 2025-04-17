'use client'

import { Table, Input } from 'antd';
import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { Student } from '@/interfaces/Student';
import { useStudents } from '@/libs/hooks/useStudents';
import moment from 'moment';

const TranscriptTable = () => {
    const router = useRouter();
    const { data: students, isLoading, error } = useStudents();

    const [searchText, setSearchText] = useState('');

    const filteredStudents = students?.filter((student: Student) =>
        student.studentId.toLowerCase().includes(searchText.toLowerCase()) ||
        student.fullName.toLowerCase().includes(searchText.toLowerCase())
    );

    const columns = [
        { title: 'MSSV', dataIndex: 'studentId' },
        { title: 'Họ tên', dataIndex: 'fullName' },
        { title: 'Ngày sinh', dataIndex: 'dob', render: (dob: string) => moment(dob).format('YYYY-MM-DD') },
        { title: 'Giới tính', dataIndex: 'gender' },
        { title: 'Khoa', dataIndex: 'faculty' },
        { title: 'Khóa', dataIndex: 'intake' },
        { title: 'Tình trạng', dataIndex: 'studentStatus' },
    ];

    if (isLoading) return <div>Loading...</div>;
    if (error) return <div>Error: {error.message}</div>;

    return (
        <div>
            <h1>Bảng điểm sinh viên</h1>
            <Input.Search
                placeholder='Tìm kiếm theo MSSV hoặc họ tên'
                allowClear
                onChange={(e) => setSearchText(e.target.value)}
                style={{
                    marginBottom: 16,
                    width: 300,
                    float: 'right'
                }}
            />
            <Table
                columns={columns}
                dataSource={filteredStudents}
                rowKey='studentId'
                onRow={(record : any) => ({
                    onClick: () => router.push(`./transcript/${record.studentId}`),
                })}
                style={{ cursor: 'pointer' }}
            />
        </div>
    );
};

export default TranscriptTable;