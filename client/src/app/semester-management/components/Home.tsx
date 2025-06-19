'use client';

import { useState, useEffect } from 'react';
import { Table, Button, Input, Space, Popconfirm, Typography, Tag, message } from 'antd';
import { EditOutlined, DeleteOutlined, PlusOutlined, SearchOutlined } from '@ant-design/icons';
import { useTranslations } from 'next-intl';
import { useSemesters } from '@/libs/hooks/semester/useSemesters';
import { useSemesterMutations } from '@/libs/hooks/semester/useSemesterMutation';
import { useDebounce } from 'use-debounce';
import SemesterModal from './SemesterModal';
import { Semester } from '@/interfaces/semester/Semester';
import moment from 'moment';
import type { TablePaginationConfig } from 'antd/es/table';
import type { SorterResult } from 'antd/es/table/interface';

const { Title } = Typography;

const Home = () => {
  // Translations
  const t = useTranslations('semester-management');
  const tCommon = useTranslations('common');

  // State
  const [searchText, setSearchText] = useState('');
  const [debouncedSearchText] = useDebounce(searchText, 500);
  const [pagination, setPagination] = useState<TablePaginationConfig>({
    current: 1,
    pageSize: 5,
  });
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [selectedSemester, setSelectedSemester] = useState<Semester | undefined>(undefined);

  // Fetch data with React Query - using object parameter pattern
  const { 
    data: semestersData, 
    isLoading, 
    error,
    refetch 
  } = useSemesters({
    page: (pagination.current || 1) - 1, 
    pageSize: pagination.pageSize || 5
  });

  const { addSemester, editSemester, deleteSemester, isLoading: isMutating } = useSemesterMutations();

  // Determine data source for the table
  const tableData = semestersData || { data: [], pagination: { total: 0 } };

  // Update pagination when data changes
  useEffect(() => {
    if (tableData?.pagination) {
      setPagination(prev => ({
        ...prev,
        total: tableData.pagination.total
      }));
    }
  }, [tableData]);

  // Handle errors
  useEffect(() => {
    if (error) {
      message.error('Failed to load semesters. Please try again.');
    }
  }, [error]);

  // Filter data based on search - fix data access pattern
  const filteredData = semestersData?.data?.filter((semester: Semester) => {
    if (!debouncedSearchText) return true;
    
    const searchLower = debouncedSearchText.toLowerCase();
    return (
      semester.academicYear.toLowerCase().includes(searchLower) ||
      `${t(`semester-${semester.semester}`)}`.toLowerCase().includes(searchLower)
    );
  });

  // Handle modal visibility
  const showModal = (semester?: Semester) => {
    setSelectedSemester(semester);
    setIsModalVisible(true);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
    setSelectedSemester(undefined);
  };

  // Handle form submit
  const handleSubmit = async (values: any) => {
    try {
      if (selectedSemester) {
        await editSemester(selectedSemester.id, values);
      } else {
        await addSemester(values);
      }
      setIsModalVisible(false);
      refetch();
    } catch (error) {
      console.error('Error submitting form:', error);
    }
  };

  // Handle delete
  const handleDelete = async (semesterId: number) => {
    try {
      await deleteSemester(semesterId);
      refetch();
    } catch (error) {
      console.error('Error deleting semester:', error);
    }
  };

  // Handle search input changes
  const handleSearch = (value: string) => {
    setSearchText(value);
    setPagination(prev => ({ ...prev, current: 1 }));
  };

  // Handle table changes (pagination)
  const handleTableChange = (
    newPagination: TablePaginationConfig,
    filters: Record<string, any>,
    sorter: SorterResult<Semester> | SorterResult<Semester>[]
  ) => {
    // Update pagination
    setPagination(prev => ({
      ...prev,
      current: newPagination.current || prev.current,
      pageSize: newPagination.pageSize || prev.pageSize
    }));
  };

  // Table columns
  const columns = [
    {
      title: t('semester-number'),
      dataIndex: 'semester',
      key: 'semester',
      render: (semester: number) => t(`semester-${semester}`),
    },
    {
      title: t('academic-year'),
      dataIndex: 'academicYear',
      key: 'academicYear',
    },
    {
      title: t('start-date'),
      dataIndex: 'startDate',
      key: 'startDate',
      render: (date: string) => moment(date).format('DD/MM/YYYY'),
    },
    {
      title: t('end-date'),
      dataIndex: 'endDate',
      key: 'endDate',
      render: (date: string) => moment(date).format('DD/MM/YYYY'),
    },
    {
      title: t('last-cancel-date'),
      dataIndex: 'lastCancelDate',
      key: 'lastCancelDate',
      render: (date: string) => moment(date).format('DD/MM/YYYY'),
    },
    {
      title: t('status'),
      key: 'status',
      render: (record: Semester) => {
        const now = moment();
        const startDate = moment(record.startDate);
        const endDate = moment(record.endDate);

        if (now.isBefore(startDate)) {
          return <Tag color="blue">{t('upcoming')}</Tag>;
        } else if (now.isAfter(endDate)) {
          return <Tag color="red">{t('completed')}</Tag>;
        } else {
          return <Tag color="green">{t('ongoing')}</Tag>;
        }
      },
    },
    {
      title: tCommon('actions'),
      key: 'actions',
      render: (record: Semester) => (
        <Space size="middle">
          <Button
            icon={<EditOutlined />}
            onClick={() => showModal(record)}
          >
            {tCommon('edit')}
          </Button>
          <Popconfirm
            title={tCommon('confirm-delete')}
            onConfirm={() => handleDelete(record.id)}
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
  if (error) {
    return (
      <div>
        <p>{tCommon('error')}</p>
        <Button onClick={() => window.location.reload()}>{tCommon('retry')}</Button>
      </div>
    );
  }

  return (
    <div>
      <h1>{t('title')}</h1>
      
      <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '20px' }}>
        <Button
          type="primary"
          icon={<PlusOutlined />}
          onClick={() => showModal()}
          loading={isMutating}
        >
          {t('add-semester')}
        </Button>
      </div>

      <div style={{ marginBottom: '20px', textAlign: 'right' }}>
        <Input.Search
          placeholder={t('search-placeholder')}
          allowClear
          onChange={(e) => handleSearch(e.target.value)}
          style={{ width: 300 }}
          prefix={<SearchOutlined />}
        />
      </div>

      <Table
        dataSource={filteredData || []}
        columns={columns}
        rowKey="id"
        loading={isLoading}
        pagination={{
          current: pagination.current,
          pageSize: pagination.pageSize,
          total: semestersData?.pagination?.total || 0,
          showSizeChanger: true,
          pageSizeOptions: ['5', '10', '20', '50'],
          showTotal: (total, range) => 
            tCommon('pagination', { item: `${range[0]}-${range[1]}`, items: total }),
        }}
        onChange={handleTableChange}
      />

      {isModalVisible && (
        <SemesterModal
          visible={isModalVisible}
          onCancel={handleCancel}
          onSubmit={handleSubmit}
          semesterData={selectedSemester}
          isSubmitting={isMutating}
        />
      )}
    </div>
  );
};

export default Home;
