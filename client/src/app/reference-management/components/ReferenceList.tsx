import React from 'react';
import { Button, Input, Popconfirm, Space, Typography } from 'antd';
import { useTranslations } from 'next-intl';

const { Title } = Typography;

type ReferenceListProps = {
    title: string;
    values: { key: number, value: string }[];
    onChange: (index: number, newValue: string) => void;
    onDelete: (id: number) => void;
    editable?: boolean;
};

const ReferenceList: React.FC<ReferenceListProps> = ({ title, values, onChange, onDelete, editable }) => {
    const tCommon = useTranslations('common');

    return (
        <div style={{ textAlign: 'center' }}>
            <Title level={3}>{title}</Title>
            {values.map(({ key, value }, index) => (
                <Space key={key} style={{ display: 'flex', marginBottom: '8px' }}>
                    <Input
                        value={value}
                        onChange={editable ? (e) => onChange(index, e.target.value) : undefined}
                        disabled={!editable}
                    />
                    <Popconfirm
                        title={tCommon('confirm-delete')}
                        onConfirm={() => { onDelete(key); }}
                        okText={tCommon('delete')}
                        cancelText={tCommon('cancel')}
                    >
                        <Button danger>{tCommon('delete')}</Button>
                    </Popconfirm>
                </Space>
            ))}
            {/* Removed per-category Add button */}
        </div>
    );
};

export default ReferenceList;