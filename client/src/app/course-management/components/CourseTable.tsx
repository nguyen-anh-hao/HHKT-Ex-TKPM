import { Table, Button, Popconfirm, Input, Tag } from 'antd';
import { EditOutlined, DeleteOutlined } from '@ant-design/icons';
import { useEffect, useState } from 'react';
import { SortOrder } from 'antd/es/table/interface';
import { fetchReference } from '@/libs/services/referenceService';
import { Course } from '@/interfaces/CourseResponse';
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
            title: 'Mã học phần',
            dataIndex: 'courseCode',
            sorter: (a: Course, b: Course) => a.courseCode.localeCompare(b.courseCode),
            defaultSortOrder: 'ascend' as SortOrder,
            sortDirections: ['ascend', 'descend'] as SortOrder[],
        },
        { title: 'Tên học phần', dataIndex: 'courseName' },
        { title: 'Số tín chỉ', dataIndex: 'credits' },
        {
            title: 'Khoa',
            dataIndex: 'facultyId',
            render: (facultyId: number) => facultyMap[facultyId] || 'Không xác định',
        },
        {
            title: 'Học phần tiên quyết',
            dataIndex: 'prerequisiteCourseId',
            render: (courseId: number | null) =>
                courseId && courseCodeMap[courseId] ? courseCodeMap[courseId] : 'Không có',
        },
        {
            title: 'Mô tả',
            dataIndex: 'description',
            ellipsis: true,
        },
        {
            title: 'Tình trạng',
            dataIndex: 'isActive',
            render: (active: boolean) =>
                active ? <Tag color="green">Hoạt động</Tag> : <Tag color="red">Không hoạt động</Tag>,
        },
        {
            title: 'Hành động',
            render: (_: any, record: Course) => (
                <>
                    <Button
                        style={{ marginRight: 8, marginBottom: 8 }}
                        icon={<EditOutlined />}
                        onClick={() => {
                            onEdit(record);
                            openModal && openModal(true);
                        }}
                    >
                        Sửa
                    </Button>
                    <Popconfirm
                        title='Bạn có chắc chắn muốn xóa học phần này?'
                        onConfirm={() =>{  console.log('Record khi xóa:', record);onDelete(record.courseId)}}
                        okText='Xóa'
                        cancelText='Hủy'
                    >
                        
                        <Button icon={<DeleteOutlined />} danger>
                            Xóa
                        </Button>
                    </Popconfirm>
                </>
            ),
        },
    ];

    return (
        <div>
            <Input.Search
                placeholder='Tìm kiếm theo mã hoặc tên học phần'
                allowClear
                onChange={(e) => {
                    setSearchText(e.target.value);
                }}
                style={{
                    marginBottom: 16,
                    width: 300,
                    float: 'right',
                }}
            />
           <Table
    columns={columns}
    dataSource={filteredCourses}
    rowKey='id'
    pagination={{
        pageSize: 10,
        showSizeChanger: false,
    }}
/>

        </div>
    );
};

export default CourseTable;
