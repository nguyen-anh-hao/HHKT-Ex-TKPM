'use client';

import React, { useState, useEffect } from 'react';
import { Table, Button, Modal, Form, Select, message } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import useReferenceStore from '@/libs/stores/referenceStore';
import { useStatusRules } from '@/libs/hooks/useStatusRules';
import {
    useCreateStatusRule,
    useDeleteStatusRule,
    useUpdateStatusRule
} from '@/libs/hooks/useStatusRuleMutation';

const StatusMatrix = () => {
    const { data: statusRules } = useStatusRules();
    const { mutate: createStatusRule } = useCreateStatusRule();
    const { mutate: updateStatusRule } = useUpdateStatusRule();
    const { mutate: deleteStatusRule } = useDeleteStatusRule();

    const { fetchReference, studentStatuses } = useReferenceStore();

    const [data, setData] = useState<any[]>([]);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [form] = Form.useForm();

    const [editingRule, setEditingRule] = useState<null | {
        id: number;
        currentStatusName: string;
        allowedTransitionName: string;
        createdAt: string;
        updatedAt: string;
        createdBy: string;
        updatedBy: string;
    }>(null);

    useEffect(() => {
        fetchReference();
    });

    useEffect(() => {
        if (statusRules) {
            setData(statusRules);
        }
    }, [statusRules]);

    const statuses = studentStatuses.map((status: any) => ({
        studentStatusId: status.id,
        studentStatusName: status.studentStatusName
    }));

    const matrixData = statuses.map(from => {
        const row: { key: string; from: string;[key: string]: any } = {
            key: from.studentStatusName,
            from: from.studentStatusName
        };

        statuses.forEach(to => {
            const rule = data.find(
                item =>
                    item.currentStatusName === from.studentStatusName &&
                    item.allowedTransitionName === to.studentStatusName
            );
            row[to.studentStatusName] = rule || null;
        });

        return row;
    });

    const columns = [
        {
            title: 'Từ trạng thái',
            dataIndex: 'from',
            fixed: 'left' as const,
            width: 150
        },
        ...statuses.map(status => ({
            title: status.studentStatusName,
            dataIndex: status.studentStatusName,
            width: 180,
            render: (value: { id: number } | null | undefined, record: { from: string }) => {
                if (!value) {
                    return (
                        <Button
                            icon={<PlusOutlined />}
                            onClick={() => openModal(null, record.from, status.studentStatusName)}
                            disabled={record.from === status.studentStatusName}
                        />
                    );
                }

                return (
                    <div style={{ display: 'flex', gap: 8 }}>
                        <Button
                            size="small"
                            type="link"
                            onClick={() =>
                                openModal(
                                    data.find(item => item.id === value.id) || null
                                )
                            }
                        >
                            Sửa
                        </Button>
                        <Button
                            size="small"
                            type="link"
                            danger
                            onClick={() => onDelete(value.id)}
                        >
                            Xóa
                        </Button>
                    </div>
                );
            }
        }))
    ];

    const openModal = (
        rule: typeof editingRule = null,
        from = '',
        to = ''
    ) => {
        setEditingRule(rule);
        setIsModalVisible(true);
        form.setFieldsValue({
            currentStatusName: rule?.currentStatusName || from,
            allowedTransitionName: rule?.allowedTransitionName || to
        });
    };

    const handleOk = () => {
        form.validateFields().then(values => {
            if (editingRule) {
                const updatedRule = {
                    ...editingRule,
                    ...values,
                    updatedAt: new Date().toISOString(),
                    updatedBy: 'admin'
                };

                const value = {
                    id: editingRule.id,
                    currentStatusId: statuses.find(
                        s => s.studentStatusName === updatedRule.currentStatusName
                    )?.studentStatusId!,
                    allowedTransitionId: statuses.find(
                        s => s.studentStatusName === updatedRule.allowedTransitionName
                    )?.studentStatusId!
                };

                updateStatusRule(value, {
                    onSuccess: () => {
                        setData(prev =>
                            prev.map(item =>
                                item.id === editingRule.id ? updatedRule : item
                            )
                        );
                        message.success('Cập nhật thành công');
                        setIsModalVisible(false);
                    },
                    onError: (error: any) => {
                        message.error(
                            `Cập nhật thất bại: ${error.response?.data?.errors?.map(
                                (e: any) => e.defaultMessage
                            ).join(' ') || error.response?.data?.message
                            }`
                        );
                    }
                });
            } else {
                const newRule = {
                    id: Math.max(0, ...data.map(i => i.id)) + 1,
                    ...values,
                    createdAt: new Date().toISOString(),
                    updatedAt: new Date().toISOString(),
                    createdBy: 'admin',
                    updatedBy: 'admin'
                };

                const value = {
                    currentStatusId: statuses.find(
                        s => s.studentStatusName === newRule.currentStatusName
                    )?.studentStatusId!,
                    allowedTransitionId: statuses.find(
                        s => s.studentStatusName === newRule.allowedTransitionName
                    )?.studentStatusId!
                };

                console.log(statuses)

                createStatusRule(value, {
                    onSuccess: () => {
                        setData(prev => [...prev, newRule]);
                        message.success('Thêm mới thành công');
                        setIsModalVisible(false);
                    },
                    onError: (error: any) => {
                        message.error(
                            `Thêm mới thất bại: ${error.response?.data?.errors?.map(
                                (e: any) => e.defaultMessage
                            ).join(' ') || error.response?.data?.message
                            }`
                        );
                    }
                });
            }
        });
    };

    const onDelete = (id: number) => {
        Modal.confirm({
            title: 'Xác nhận xóa',
            onOk: () => {
                deleteStatusRule(id, {
                    onSuccess: () => {
                        setData(prev => prev.filter(item => item.id !== id));
                        message.success('Đã xóa quy tắc');
                    },
                    onError: (error: any) => {
                        message.error(
                            `Xóa thất bại: ${error.response?.data?.errors?.map(
                                (e: any) => e.defaultMessage
                            ).join(' ') || error.response?.data?.message
                            }`
                        );
                    }
                });
            }
        });
    };

    return (
        <div>
            <h1>Cấu hình quy tắc chuyển trạng thái sinh viên</h1>
            <Table
                columns={columns}
                dataSource={matrixData}
                pagination={false}
                bordered
                scroll={{ x: true }}
            />

            <Modal
                title={editingRule ? 'Sửa phép chuyển' : 'Thêm phép chuyển'}
                open={isModalVisible}
                onCancel={() => setIsModalVisible(false)}
                onOk={handleOk}
            >
                <Form form={form} layout="vertical">
                    <Form.Item
                        name="currentStatusName"
                        label="Từ trạng thái"
                        rules={[{ required: true }]}
                    >
                        <Select
                            options={statuses.map(s => ({
                                value: s.studentStatusName,
                                label: s.studentStatusName
                            }))}
                            disabled={!!editingRule}
                        />
                    </Form.Item>
                    <Form.Item
                        name="allowedTransitionName"
                        label="Đến trạng thái"
                        rules={[{ required: true }]}
                    >
                        <Select
                            options={statuses.map(s => ({
                                value: s.studentStatusName,
                                label: s.studentStatusName
                            }))}
                        />
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    );
};

export default StatusMatrix;
