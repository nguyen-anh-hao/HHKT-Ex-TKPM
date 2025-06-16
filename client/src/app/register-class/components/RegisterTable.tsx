import { Table, Button, Input, Tag, Space, message } from 'antd';
import { EditOutlined, SearchOutlined } from '@ant-design/icons';
import { useState, useEffect } from 'react';
import { RegisterResponse } from '@/interfaces/register/RegisterResponse';
import { useTranslations } from 'next-intl';
import { useSearchRegistrations, useRegistrations } from '@/libs/hooks/register/useRegisterQuery';
import { useDebounce } from 'use-debounce';
import type { TablePaginationConfig } from 'antd/es/table';
import type { SorterResult } from 'antd/es/table/interface';

interface RegisterTableProps {
    onEdit: (registration: RegisterResponse) => void;
}

const RegisterTable = ({ onEdit }: RegisterTableProps) => {
    const [searchText, setSearchText] = useState('');
    const [debouncedSearchText] = useDebounce(searchText, 500);
    const t = useTranslations('register-class');
    const tCommon = useTranslations('common');
    
    const [pagination, setPagination] = useState<TablePaginationConfig>({
        current: 1,
        pageSize: 5,
    });
    const [sortField, setSortField] = useState<string>('id');
    const [sortOrder, setSortOrder] = useState<string>('desc'); // Show newest first

    // Fetch registrations data with pagination
    const { 
        data: registrationsData, 
        isLoading: registrationsLoading,
        error: registrationsError
    } = useRegistrations({
        page: (pagination.current || 1) - 1,
        pageSize: pagination.pageSize || 5,
        sortField,
        sortOrder
    });

    // Search registrations with pagination
    const { 
        data: searchResults, 
        isLoading: searchLoading,
        error: searchError
    } = useSearchRegistrations(
        debouncedSearchText,
        (pagination.current || 1) - 1,
        pagination.pageSize || 5,
        {
            enabled: debouncedSearchText.length > 0,
        }
    );

    // Determine data source and loading state
    const tableData = debouncedSearchText ? searchResults : registrationsData;
    const loading = debouncedSearchText ? searchLoading : registrationsLoading;

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
        if (registrationsError && !debouncedSearchText) {
            message.error('Failed to load registrations. Please try again.');
        }
    }, [registrationsError, debouncedSearchText]);

    useEffect(() => {
        if (searchError && debouncedSearchText) {
            message.error('Search failed. Please try again.');
        }
    }, [searchError, debouncedSearchText]);

    // Handle table changes (pagination, sorting)
    const handleTableChange = (
        newPagination: TablePaginationConfig,
        filters: Record<string, any>,
        sorter: SorterResult<RegisterResponse> | SorterResult<RegisterResponse>[]
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
            title: t('student-id'),
            dataIndex: 'studentId',
            key: 'studentId',
        },
        {
            title: t('student-name'),
            dataIndex: 'studentName',
            key: 'studentName',
        },
        {
            title: t('class-code'),
            dataIndex: 'classCode',
            key: 'classCode',
        },
        {
            title: t('status'),
            dataIndex: 'status',
            key: 'status',
            render: (status: string) => {
                let color = 'blue';
                if (status === 'COMPLETED') color = 'green';
                else if (status === 'CANCELLED') color = 'red';
                return <Tag color={color}>{t(status)}</Tag>;
            },
        },
        {
            title: tCommon('actions'),
            key: 'action',
            render: (_: any, record: RegisterResponse) => (
                <Space>
                    <Button
                        icon={<EditOutlined />}
                        onClick={() => onEdit(record)}
                    >
                        {tCommon('edit')}
                    </Button>
                </Space>
            ),
        },
    ];

    // Show error state
    if ((registrationsError && !debouncedSearchText) || (searchError && debouncedSearchText)) {
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

export default RegisterTable;
