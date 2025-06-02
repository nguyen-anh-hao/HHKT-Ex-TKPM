import { Table, Button, Popconfirm, Input, Tag } from 'antd';
import { EditOutlined, DeleteOutlined } from '@ant-design/icons';
import { useEffect, useState } from 'react';
import { SortOrder } from 'antd/es/table/interface';
import { fetchReference } from '@/libs/services/referenceService';
import { Course } from '@/interfaces/Course';
import { useTranslations } from 'next-intl';

interface CourseTableProps {
    courses: Course[];
    onEdit: (course: Course) => void;
    onDelete: (id: number) => void;
    openModal?: (visible: boolean) => void;
    loading?: boolean;
}

const CourseTable = ({ courses, onEdit, onDelete, openModal, loading }: CourseTableProps) => {
    const [searchText, setSearchText] = useState('');
    const [facultyMap, setFacultyMap] = useState<Record<number, string>>({});
    const [courseCodeMap, setCourseCodeMap] = useState<Record<number, string>>({});
    const t = useTranslations('course-management');

    useEffect(() => {
        const fetchFacultyOptions = async () => {
            const response = await fetchReference('faculties');
            const map: Record<number, string> = {};
            response.forEach((f: any) => {
                map[f.id] = f.facultyName;
            });
            setFacultyMap(map);
        };

        fetchFacultyOptions();

        // Tạo map từ id => courseCode
        const map: Record<number, string> = {};
        courses.forEach((course: Course) => {
            map[course.courseId] = course.courseCode;
        });
        setCourseCodeMap(map);

    }, [courses]);

    const filteredCourses = courses.filter((course: Course) =>
        course.courseCode.toLowerCase().includes(searchText.toLowerCase()) ||
        course.courseName.toLowerCase().includes(searchText.toLowerCase())
    );

    const columns = [
        {
            title: t('course-code'),
            dataIndex: 'courseCode',
            sorter: (a: Course, b: Course) => a.courseCode.localeCompare(b.courseCode),
            defaultSortOrder: 'ascend' as SortOrder,
            sortDirections: ['ascend', 'descend'] as SortOrder[],
        },
        { title: t('course-name'), dataIndex: 'courseName' },
        { title: t('credits'), dataIndex: 'credits' },
        {
            title: t('faculty'),
            dataIndex: 'faculty',
            // render: (facultyId: number) => facultyMap[facultyId] || 'Không xác định',
        },
        {
            title: 'Học phần tiên quyết',
            dataIndex: 'prerequisiteCourseId',
            render: (courseId: number | null) =>
                courseId && courseCodeMap[courseId] ? courseCodeMap[courseId] : 'Không có',
        },
        {
            title: t('description'),
            dataIndex: 'description',
            ellipsis: true,
        },
        {
            title: t('active'),
            dataIndex: 'isActive',
            render: (isActive: boolean) => (
                <Tag color={isActive ? 'green' : 'red'}>
                    {isActive ? t('active') : t('inactive')}
                </Tag>
            ),
        },
        {
            title: t('actions'),
            render: (_: any, record: Course) => (
                <>
                    <Button
                        style={{ marginRight: 8, marginBottom: 8 }}
                        icon={<EditOutlined />}
                        onClick={() => {
                            onEdit(record);
                            openModal?.(true);
                        }}
                    >
                        {t('edit')}
                    </Button>
                    <Popconfirm
                        title={t('confirm-delete')}
                        onConfirm={() => onDelete(record.courseId)}
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
            <Table columns={columns} dataSource={filteredCourses} rowKey='courseId' loading={loading} />
        </div>
    );
};

export default CourseTable;
