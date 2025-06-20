import { Table, Button, Popconfirm, Input, Space, message } from 'antd';
import { EditOutlined, DeleteOutlined, SearchOutlined } from '@ant-design/icons';
import { useEffect, useState } from 'react';
import { fetchReference } from '@/libs/services/referenceService';
import { fetchAllSemesters } from '@/libs/services/semesterService';
import { Class } from '@/interfaces/class/Class';
import { useTranslations } from 'next-intl';
import { useSearchClasses, useClasses } from '@/libs/hooks/class/useClassQuery';
import { useDebounce } from 'use-debounce';
import type { TablePaginationConfig } from 'antd/es/table';
import type { SorterResult } from 'antd/es/table/interface';

interface ClassTableProps {
    onEdit: (classData: Class) => void;
    onDelete: (id: number) => void;
    openModal?: (visible: boolean) => void;
}

const ClassTable = ({ onEdit, onDelete, openModal }: ClassTableProps) => {
    const [searchText, setSearchText] = useState('');
    const [debouncedSearchText] = useDebounce(searchText, 500);
    const [facultyMap, setFacultyMap] = useState<Record<number, string>>({});
    const [semesterMap, setSemesterMap] = useState<Record<number, string>>({});
    const t = useTranslations('class-management');
    const tCommon = useTranslations('common');
    
    const [pagination, setPagination] = useState<TablePaginationConfig>({
        current: 1,
        pageSize: 5,
    });
    const [sortField, setSortField] = useState<string>('classCode');
    const [sortOrder, setSortOrder] = useState<string>('asc');

    // Fetch classes data with pagination
    const { 
        data: classesData, 
        isLoading: classesLoading,
        error: classesError
    } = useClasses({
        page: (pagination.current || 1) - 1,
        pageSize: pagination.pageSize || 5,
        sortField,
        sortOrder
    });

    // Search classes with pagination
    const { 
        data: searchResults, 
        isLoading: searchLoading,
        error: searchError
    } = useSearchClasses(
        debouncedSearchText,
        (pagination.current || 1) - 1,
        pagination.pageSize || 5,
        {
            enabled: debouncedSearchText.length > 0,
        }
    );

    // Determine data source and loading state
    const tableData = debouncedSearchText ? searchResults : classesData;
    const loading = debouncedSearchText ? searchLoading : classesLoading;

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

    // Load semester options
    useEffect(() => {
        const fetchSemesterOptions = async () => {
            try {
                const response = await fetchAllSemesters();
                const map: Record<number, string> = {};
                response.forEach((s: any) => {
                    map[s.id] = `${t('semester')} ${s.semester} - ${s.academicYear}`;
                });
                setSemesterMap(map);
            } catch (error) {
                console.error('Error fetching semester options:', error);
            }
        };

        fetchSemesterOptions();
    }, []);

    // Handle errors
    useEffect(() => {
        if (classesError && !debouncedSearchText) {
            message.error('Failed to load classes. Please try again.');
        }
    }, [classesError, debouncedSearchText]);

    useEffect(() => {
        if (searchError && debouncedSearchText) {
            message.error('Search failed. Please try again.');
        }
    }, [searchError, debouncedSearchText]);

    // Handle table changes (pagination, sorting)
    const handleTableChange = (
        newPagination: TablePaginationConfig,
        filters: Record<string, any>,
        sorter: SorterResult<Class> | SorterResult<Class>[]
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
            title: t('class-code'),
            dataIndex: 'classCode',
        },
        { title: t('course-code'), dataIndex: 'courseCode' },
        { title: t('semester'), dataIndex: 'semesterId', render: (semester: any) => semesterMap[semester]},
        { title: t('course-name'), dataIndex: 'courseName' },
        { title: t('lecturer'), dataIndex: 'lecturerName' },
        { title: t('max-students'), dataIndex: 'maxStudents' },
        { title: t('schedule'), dataIndex: 'schedule' },
        { title: t('room'), dataIndex: 'room' },
        // {
        //     title: tCommon('actions'),
        //     render: (_: any, record: Class) => (
        //         <Space>
        //             <Button
        //                 icon={<EditOutlined />}
        //                 onClick={() => {
        //                     onEdit(record);
        //                     openModal?.(true);
        //                 }}
        //             >
        //                 {tCommon('edit')}
        //             </Button>
        //             <Popconfirm
        //                 title={t('confirm-delete')}
        //                 onConfirm={() => onDelete(record.id)}
        //                 okText={tCommon('delete')}
        //                 cancelText={tCommon('cancel')}
        //             >
        //                 <Button icon={<DeleteOutlined />} danger>
        //                     {tCommon('delete')}
        //                 </Button>
        //             </Popconfirm>
        //         </Space>
        //     ),
        // },
    ];

    // Show error state
    if ((classesError && !debouncedSearchText) || (searchError && debouncedSearchText)) {
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
                rowKey='id' 
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

export default ClassTable;

