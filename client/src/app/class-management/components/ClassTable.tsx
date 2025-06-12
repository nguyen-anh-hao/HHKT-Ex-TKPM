import { Table, Input } from 'antd';
import { useEffect, useState } from 'react';
import { SortOrder } from 'antd/es/table/interface';
import { fetchReference } from '@/libs/services/referenceService';
import { Class } from '@/interfaces/class/ClassResponse';
import { useTranslations } from 'next-intl';

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
    const t = useTranslations('class-management');

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
            title: t('class-code'),
            dataIndex: 'classCode',
        },
        { title: t('course-code'), dataIndex: 'courseCode' },
        { title: t('course-name'), dataIndex: 'courseName' },
        { title: t('lecturer'), dataIndex: 'lecturerName' },
        { title: t('max-students'), dataIndex: 'maxStudents' },
        { title: t('schedule'), dataIndex: 'schedule' },
        { title: t('room'), dataIndex: 'room' },
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
            <Table columns={columns} dataSource={filteredClasses} rowKey='classCode' loading={loading} />
        </div>
    );
};

export default ClassTable;

