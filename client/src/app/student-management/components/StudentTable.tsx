import { Table, Button, Popconfirm, Input } from 'antd';
import { EditOutlined, DeleteOutlined } from '@ant-design/icons';
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

    const filteredStudents = students.filter((student: Student) =>
        student.studentId.toLowerCase().includes(searchText.toLowerCase()) ||
        student.fullName.toLowerCase().includes(searchText.toLowerCase())
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
        { title: t('mssv'), dataIndex: 'studentId'},
        { title: t('full-name'), dataIndex: 'fullName' },
        { title: t('dob'), dataIndex: 'dob', render: (dob: string) => moment(dob).format('YYYY-MM-DD') },
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
        { title: t('year'), dataIndex: 'intake' },
        { title: t('state'), dataIndex: 'studentStatus' },
        {
            title: t('actions'),
            render: (_: any, record: Student) => (
                <>
                    <Button
                        style={{ marginRight: 8, marginBottom: 8 }}
                        icon={<EditOutlined />}
                        onClick={() => {
                            onEdit(record);
                            openModal(true);
                        }}
                    >
                        {t('edit')}
                    </Button>
                    <Popconfirm
                        title={t('confirm-delete')}
                        onConfirm={() => onDelete(record.studentId)}
                        okText={t('delete')}
                        cancelText={t('cancel')}
                    >
                        <Button icon={<DeleteOutlined />} danger>
                            {t('delete')}
                        </Button>
                    </Popconfirm>
                </>
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
            <Table columns={columns} dataSource={filteredStudents} rowKey='studentId' />
        </div>
    );
};

export default StudentTable;