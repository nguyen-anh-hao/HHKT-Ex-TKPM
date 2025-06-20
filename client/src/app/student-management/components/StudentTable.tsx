import { Table, Button, Popconfirm, Input, Space, message } from 'antd';
import { EditOutlined, DeleteOutlined, SearchOutlined } from '@ant-design/icons';
import { Student } from '@/interfaces/student/Student';
import moment from 'moment';
import { useState, useEffect } from 'react';
import { fetchReference } from '@/libs/services/referenceService';
import { useTranslations } from 'next-intl';
import { useSearchStudents, useStudents } from '@/libs/hooks/student/useStudents';
import { useDebounce } from 'use-debounce';
import type { TablePaginationConfig } from 'antd/es/table';
import type { SorterResult } from 'antd/es/table/interface';

interface StudentTableProps {
  students?: Student[];
  onEdit: (student: Student) => void;
  onDelete: (studentId: string) => void;
  openModal: (visible: boolean) => void;
  loading?: boolean;
}

const StudentTable = ({ 
  students: externalStudents, 
  onEdit, 
  onDelete, 
  openModal, 
  loading: externalLoading 
}: StudentTableProps) => {
  const [searchText, setSearchText] = useState('');
  const [debouncedSearchText] = useDebounce(searchText, 500);
  const [facultyOptions, setFacultyOptions] = useState<{ text: string; value: string }[]>([]);
  const t = useTranslations('student-management');
  const tCommon = useTranslations('common');
  
  const [pagination, setPagination] = useState<TablePaginationConfig>({
    current: 1,
    pageSize: 5,
  });
  const [sortField, setSortField] = useState<string>('studentId');
  const [sortOrder, setSortOrder] = useState<string>('asc');

  const useInternalData = !externalStudents;
  
  // Fetch students data with pagination
  const { 
    data: studentsData, 
    isLoading: studentsLoading,
    error: studentsError
  } = useStudents({
    page: (pagination.current || 1) - 1,
    pageSize: pagination.pageSize || 5, // Fixed: Match the default pageSize
    sortField,
    sortOrder
  }, { 
    enabled: useInternalData,
  });

  // Search students with pagination
  const { 
    data: searchResults, 
    isLoading: searchLoading,
    error: searchError
  } = useSearchStudents(
    debouncedSearchText,
    (pagination.current || 1) - 1,
    pagination.pageSize || 5, // Fixed: Match the default pageSize
    {
      enabled: useInternalData && debouncedSearchText.length > 0,
    }
  );

  // Determine data source and loading state
  const tableData = useInternalData
    ? (debouncedSearchText ? searchResults : studentsData)
    : { data: externalStudents || [], pagination: { total: externalStudents?.length || 0 } };
  
  const loading = externalLoading !== undefined 
    ? externalLoading 
    : (debouncedSearchText ? searchLoading : studentsLoading);

  // Update pagination when data changes
  useEffect(() => {
    if (useInternalData && tableData?.pagination) {
      setPagination(prev => ({
        ...prev,
        total: tableData.pagination.total
      }));
    } else if (externalStudents) {
      setPagination(prev => ({
        ...prev,
        total: externalStudents.length
      }));
    }
  }, [tableData, externalStudents, useInternalData]);

  // Load faculty options for filtering
  useEffect(() => {
    const fetchFacultyOptions = async () => {
      try {
        const response = await fetchReference('faculties');
        const options = response.map((option: any) => ({
          text: option.facultyName,
          value: option.facultyName,
        }));
        setFacultyOptions(options);
      } catch (error) {
        console.error('Error fetching faculty options:', error);
      }
    };

    fetchFacultyOptions();
  }, []);

  // Handle errors with useEffect
  useEffect(() => {
    if (studentsError && !debouncedSearchText) {
      message.error('Failed to load students. Please try again.');
    }
  }, [studentsError, debouncedSearchText]);

  useEffect(() => {
    if (searchError && debouncedSearchText) {
      message.error('Search failed. Please try again.');
    }
  }, [searchError, debouncedSearchText]);

  // Handle table changes (pagination, sorting, filtering)
  const handleTableChange = (
    newPagination: TablePaginationConfig,
    filters: Record<string, any>,
    sorter: SorterResult<Student> | SorterResult<Student>[]
  ) => {
    if (!useInternalData) return;

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

  // Table columns configuration
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

  // Show error state
  if ((useInternalData && studentsError && !debouncedSearchText) || 
      (useInternalData && searchError && debouncedSearchText)) {
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
        rowKey='studentId'
        loading={loading}
        pagination={{ 
          current: pagination.current,
          pageSize: pagination.pageSize,
          total: tableData?.pagination?.total || 0,
          showSizeChanger: true,
          pageSizeOptions: ['5', '10', '20', '50'],
          showTotal: (total, range) => tCommon('pagination', { item: `${range[0]}-${range[1]}`, items: total }),
          // showQuickJumper: true, // Commented out - can enable later when needed
        }}
        onChange={handleTableChange}
      />
    </div>
  );
};

export default StudentTable;