import { Table, Button, Popconfirm, Input, Tag, Space, message } from 'antd';
import { EditOutlined, DeleteOutlined, SearchOutlined } from '@ant-design/icons';
import { useEffect, useState } from 'react';
import { fetchReference } from '@/libs/services/referenceService';
import { Course } from '@/interfaces/course/Course';
import { useTranslations } from 'next-intl';
import { useSearchCourses, useCourses } from '@/libs/hooks/course/useCourseQuery';
import { useDebounce } from 'use-debounce';
import type { TablePaginationConfig } from 'antd/es/table';
import type { SorterResult } from 'antd/es/table/interface';

interface CourseTableProps {
    onEdit: (course: Course) => void;
    onDelete: (id: number) => void;
    openModal?: (visible: boolean) => void;
}

const CourseTable = ({ onEdit, onDelete, openModal }: CourseTableProps) => {
    const [searchText, setSearchText] = useState('');
    const [debouncedSearchText] = useDebounce(searchText, 500);
    const [facultyMap, setFacultyMap] = useState<Record<number, string>>({});
    const t = useTranslations('course-management');
    const tCommon = useTranslations('common');
    
    const [pagination, setPagination] = useState<TablePaginationConfig>({
        current: 1,
        pageSize: 5,
    });
    const [sortField, setSortField] = useState<string>('courseCode');
    const [sortOrder, setSortOrder] = useState<string>('asc');

    // Fetch courses data with pagination
    const { 
        data: coursesData, 
        isLoading: coursesLoading,
        error: coursesError
    } = useCourses({
        page: (pagination.current || 1) - 1,
        pageSize: pagination.pageSize || 5,
        sortField,
        sortOrder
    });

    // Search courses with pagination
    const { 
        data: searchResults, 
        isLoading: searchLoading,
        error: searchError
    } = useSearchCourses(
        debouncedSearchText,
        (pagination.current || 1) - 1,
        pagination.pageSize || 5,
        {
            enabled: debouncedSearchText.length > 0,
        }
    );

    // Determine data source and loading state
    const tableData = debouncedSearchText ? searchResults : coursesData;
    const loading = debouncedSearchText ? searchLoading : coursesLoading;

    // Update pagination when data changes
    useEffect(() => {
        if (tableData?.pagination) {
            setPagination(prev => ({
                ...prev,
                total: tableData.pagination.total
            }));
        }
    }, [tableData]);

    // Load faculty options
    useEffect(() => {
        const fetchFacultyOptions = async () => {
            try {
                const response = await fetchReference('faculties');
                const map: Record<number, string> = {};
                response.forEach((f: any) => {
                    map[f.id] = f.facultyName;
                });
                setFacultyMap(map);
            } catch (error) {
                console.error('Error fetching faculty options:', error);
            }
        };

        fetchFacultyOptions();
    }, []);

    // Handle errors
    useEffect(() => {
        if (coursesError && !debouncedSearchText) {
            message.error('Failed to load courses. Please try again.');
        }
    }, [coursesError, debouncedSearchText]);

    useEffect(() => {
        if (searchError && debouncedSearchText) {
            message.error('Search failed. Please try again.');
        }
    }, [searchError, debouncedSearchText]);

    // Handle table changes (pagination, sorting)
    const handleTableChange = (
        newPagination: TablePaginationConfig,
        filters: Record<string, any>,
        sorter: SorterResult<Course> | SorterResult<Course>[]
    ) => {
        // Handle sorting
        if (!Array.isArray(sorter) && sorter.field) {
            setSortField(sorter.field as string);
            setSortOrder(sorter.order === 'descend' ? 'desc' : 'asc');
        }

        // Update pagination
        setPagination(prev => ({
            ...prev,
            current: newPagination.current || prev.current,
            pageSize: newPagination.pageSize || prev.pageSize
        }));
    };

    // Handle search input changes
    const handleSearch = (value: string) => {
        setSearchText(value);
        setPagination(prev => ({ ...prev, current: 1 }));
    };

    const columns = [
        {
            title: t('course-code'),
            dataIndex: 'courseCode',
        },
        { title: t('course-name'), dataIndex: 'courseName' },
        { title: t('credits'), dataIndex: 'credits' },
        {
            title: t('faculty'),
            dataIndex: 'facultyName',
            render: (facultyName: string) => facultyName || 'N/A',
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
            title: tCommon('actions'),
            render: (_: any, record: Course) => (
                <Space>
                    <Button
                        icon={<EditOutlined />}
                        onClick={() => {
                            onEdit(record);
                            openModal?.(true);
                        }}
                    >
                        {tCommon('edit')}
                    </Button>
                    <Popconfirm
                        title={t('confirm-delete')}
                        onConfirm={() => onDelete(record.courseId)}
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

    // Show error state
    if ((coursesError && !debouncedSearchText) || (searchError && debouncedSearchText)) {
        return (
            <div>
                <p>{tCommon('error')}</p>
                <Button onClick={() => window.location.reload()}>{tCommon('retry')}</Button>
            </div>
        );
    }

    return (
        <div>
            <Input.Search
                placeholder={t('search-placeholder')}
                allowClear
                onChange={(e) => handleSearch(e.target.value)}
                style={{
                    marginBottom: 16,
                    width: 300,
                    float: 'right'
                }}
                prefix={<SearchOutlined />}
            />
            <Table 
                columns={columns} 
                dataSource={tableData?.data || []} 
                rowKey='courseId' 
                loading={loading}
                pagination={{ 
                    current: pagination.current,
                    pageSize: pagination.pageSize,
                    total: tableData?.pagination?.total || 0,
                    showSizeChanger: true,
                    pageSizeOptions: ['5', '10', '20', '50'],
                    showTotal: (total, range) => tCommon('pagination', { item: `${range[0]}-${range[1]}`, items: total }),
                }}
                onChange={handleTableChange}
            />
        </div>
    );
};

export default CourseTable;
