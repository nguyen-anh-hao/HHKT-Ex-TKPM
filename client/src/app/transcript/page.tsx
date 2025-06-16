'use client'

import { Table, Input, Alert } from 'antd';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { Student } from '@/interfaces/student/Student';
import { useStudents, useSearchStudents } from '@/libs/hooks/student/useStudents';
import { useDebounce } from 'use-debounce';
import moment from 'moment';
import { useTranslations } from 'next-intl';
import type { TablePaginationConfig } from 'antd/es/table';

const TranscriptTable = () => {
  const router = useRouter();
  const [searchText, setSearchText] = useState('');
  const [debouncedSearchText] = useDebounce(searchText, 500);
  
  const [pagination, setPagination] = useState<TablePaginationConfig>({
    current: 1,
    pageSize: 10,
  });
  
  const t = useTranslations('student-management');
  const tCommon = useTranslations('common');
  const ttran = useTranslations('transcript');

  // Fetch students data with pagination
  const { 
    data: studentsData, 
    isLoading: studentsLoading, 
    error: studentsError 
  } = useStudents({
    page: (pagination.current || 1) - 1,
    pageSize: pagination.pageSize || 10,
    sortField: 'studentId',
    sortOrder: 'asc'
  });

  // Search students with pagination
  const { 
    data: searchResults, 
    isLoading: searchLoading,
    error: searchError
  } = useSearchStudents(
    debouncedSearchText,
    (pagination.current || 1) - 1,
    pagination.pageSize || 10,
    {
      enabled: debouncedSearchText.length > 0,
    }
  );

  // Determine data source and loading state
  const tableData = debouncedSearchText ? searchResults : studentsData;
  const loading = debouncedSearchText ? searchLoading : studentsLoading;
  const error = debouncedSearchText ? searchError : studentsError;

  // Update pagination when data changes
  useEffect(() => {
    if (tableData?.pagination) {
      setPagination(prev => ({
        ...prev,
        total: tableData.pagination.total
      }));
    }
  }, [tableData]);

  // Handle table pagination changes
  const handleTableChange = (newPagination: TablePaginationConfig) => {
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
    { title: t('mssv'), dataIndex: 'studentId' },
    { title: t('full-name'), dataIndex: 'fullName' },
    { title: t('dob'), dataIndex: 'dob', render: (dob: string) => moment(dob).format('YYYY-MM-DD') },
    { title: t('gender'), dataIndex: 'gender' },
    { title: t('faculty'), dataIndex: 'faculty' },
    { title: t('year'), dataIndex: 'intake' },
    { title: t('state'), dataIndex: 'studentStatus' },
  ];

  if (loading) return <div>{tCommon('loading')}</div>;
  if (error) return <Alert 
    message={tCommon('error')} 
    description={error.message} 
    type="error" 
    showIcon 
  />;

  return (
    <div>
      <h1>{ttran('title')}</h1>
      <Input.Search
        placeholder={t('search-placeholder')}
        allowClear
        onChange={(e) => handleSearch(e.target.value)}
        style={{
          marginBottom: 16,
          width: 300,
          float: 'right'
        }}
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
          showTotal: (total, range) => `${range[0]}-${range[1]} of ${total} items`,
        }}
        onChange={handleTableChange}
        onRow={(record : any) => ({
          onClick: () => router.push(`./transcript/${record.studentId}`),
        })}
        style={{ cursor: 'pointer' }}
      />
    </div>
  );
};

export default TranscriptTable;