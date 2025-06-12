'use client'

import { Table, Input } from 'antd';
import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { Student } from '@/interfaces/student/Student';
import { useStudents } from '@/libs/hooks/student/useStudents';
import moment from 'moment';
import { useTranslations } from 'next-intl';

const TranscriptTable = () => {
    const router = useRouter();
    const { data: students, isLoading, error } = useStudents();
    const t = useTranslations('student-management');
    const tCommon = useTranslations('common');
    const ttran = useTranslations('transcript');

    const [searchText, setSearchText] = useState('');

    const filteredStudents = students?.filter((student: Student) =>
        student.studentId.toLowerCase().includes(searchText.toLowerCase()) ||
        student.fullName.toLowerCase().includes(searchText.toLowerCase())
    );

    const columns = [
        { title: t('mssv'), dataIndex: 'studentId' },
        { title: t('full-name'), dataIndex: 'fullName' },
        { title: t('dob'), dataIndex: 'dob', render: (dob: string) => moment(dob).format('YYYY-MM-DD') },
        { title: t('gender'), dataIndex: 'gender' },
        { title: t('faculty'), dataIndex: 'faculty' },
        { title: t('year'), dataIndex: 'intake' },
        { title: t('state'), dataIndex: 'studentStatus' },
    ];    if (isLoading) return <div>{tCommon('loading')}</div>;
    if (error) return <div>{tCommon('error')}: {error.message}</div>;

    return (
        <div>
            <h1>{ttran('title')}</h1>
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