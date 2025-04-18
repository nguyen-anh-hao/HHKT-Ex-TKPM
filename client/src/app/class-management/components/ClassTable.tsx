import { Table, Button, Popconfirm, Input, Tag } from 'antd';
import { EditOutlined, DeleteOutlined } from '@ant-design/icons';
import { useEffect, useState } from 'react';
import { SortOrder } from 'antd/es/table/interface';
import { fetchReference } from '@/libs/services/referenceService';
import { Class } from '@/interfaces/ClassResponse';

interface ClassTableProps {
  classes: Class[];
  onEdit: (classData: Class) => void;
  onDelete: (id: number) => void;
  openModal?: (visible: boolean) => void;
  loading?: boolean;
}

const ClassTable = ({ classes, openModal, loading }: ClassTableProps) => {
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

    const map: Record<number, string> = {};
    classes.forEach((classData: Class) => {
      map[classData.courseId] = classData.courseCode || '';
    });
    setCourseCodeMap(map);
  }, [classes]);

  const filteredClasses = classes.filter((classData: Class) =>
    classData.classCode.toLowerCase().includes(searchText.toLowerCase()) ||
    classData.courseName?.toLowerCase().includes(searchText.toLowerCase())
  );

  const columns = [
    {
      title: 'Mã lớp học',
      dataIndex: 'classCode',
      sorter: (a: Class, b: Class) => a.classCode.localeCompare(b.classCode),
      defaultSortOrder: 'ascend' as SortOrder,
      sortDirections: ['ascend', 'descend'] as SortOrder[],
    },
    { title: 'Mã khóa học', dataIndex: 'courseCode' },
        { title: 'Tên khóa học', dataIndex: 'courseName' },
      { title: 'Giảng viên', dataIndex: 'lecturerName' },
    { title: 'Số học sinh tối đa', dataIndex: 'maxStudents' }, 
    { title: 'Lịch học', dataIndex: 'schedule' }, 
    { title: 'Phòng', dataIndex: 'room' }, 
   
    
    
  ];

  return (
    <div>
      <Input.Search
        placeholder="Tìm kiếm theo mã lớp học hoặc tên lớp học"
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
        dataSource={filteredClasses}
        rowKey="classCode" 
        pagination={{
          pageSize: 10,
          showSizeChanger: false,
        }}
        loading={loading} 
      />
    </div>
  );
};

export default ClassTable;
