import { Table, Button, Popconfirm, Input, Space } from 'antd';
import { EditOutlined, DeleteOutlined, SearchOutlined } from '@ant-design/icons';
import { Student } from '@/interfaces/student/Student';
import moment from 'moment';
import { useState } from 'react';
import { SortOrder } from 'antd/es/table/interface';
import { useEffect } from 'react';
import { fetchReference } from '@/libs/services/referenceService';
import { useTranslations } from 'next-intl';

const StudentTable = ({ students, onEdit, onDelete, openModal }: any) => {
    const [searchText, setSearchText] = useState('');
    const [facultyOptions, setFacultyOptions] = useState<{ text: string; value: string }[]>([]);
    const t = useTranslations('student-management');
    const tCommon = useTranslations('common');

    // Enhanced search functionality to include more fields
    const filteredStudents = students.filter((student: Student) =>
        student.studentId.toLowerCase().includes(searchText.toLowerCase()) ||
        student.fullName.toLowerCase().includes(searchText.toLowerCase()) ||
        student.faculty.toLowerCase().includes(searchText.toLowerCase()) ||
        String(student.intake).includes(searchText)
    );
    
    useEffect(() => {
        const fetchFacultyOptions = async () => {
            const response = await fetchReference('faculties');
            const options = response.map((option: any) => ({
                text: option.facultyName,
                value: option.facultyName,
            }));
            setFacultyOptions(options);
        };

        fetchFacultyOptions();
    }, []);

    const columns = [
        { 
            title: t('mssv'), 
            dataIndex: 'studentId',
        },
        { 
            title: t('full-name'), 
            dataIndex: 'fullName',
        },
        { 
            title: t('dob'), 
            dataIndex: 'dob', 
            render: (dob: string) => moment(dob).format('YYYY-MM-DD'),
        },
        { title: t('gender'), dataIndex: 'gender' },
        {
            title: t('faculty'),
            dataIndex: 'faculty',
            filters: facultyOptions?.map((option) => ({
                text: option.text,
                value: option.value,
            })) || [],
            onFilter: (value: any, record: Student) => record.faculty === value as string,
        },
        { 
            title: t('year'), 
            dataIndex: 'intake',
        },
        { title: t('state'), dataIndex: 'studentStatus' },
        {
            title: tCommon('actions'),
            render: (_: any, record: Student) => (
                <Space>
                    <Button
                        icon={<EditOutlined />}
                        onClick={() => {
                            onEdit(record);
                            openModal(true);
                        }}
                    >
                        {tCommon('edit')}
                    </Button>
                    <Popconfirm
                        title={tCommon('confirm-delete')}
                        onConfirm={() => onDelete(record.studentId)}
                        okText={tCommon('delete')}
                        cancelText={tCommon('cancel')}
                    >
                        <Button icon={<DeleteOutlined />} danger>
                            {tCommon('delete')}
                        </Button>
                    </Popconfirm>
                </Space>
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
                prefix={<SearchOutlined />}
            />
            <Table 
                columns={columns} 
                dataSource={filteredStudents} 
                rowKey='studentId'
                pagination={{ 
                    showSizeChanger: true,
                    pageSizeOptions: ['10', '20', '50'],
                    showTotal: (total, range) => `${range[0]}-${range[1]} of ${total} items`
                }}
            />
        </div>
    );
};

export default StudentTable;