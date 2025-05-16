import React from 'react';
import { Button, Input, Popconfirm, Space, Typography } from 'antd';
import { useTranslations } from 'next-intl';

const { Title } = Typography;

type ReferenceListProps = {
    title: string;
    values: { key: number, value: string }[];
    onChange: (index: number, newValue: string) => void;
    onDelete: (id: number) => void;
    onAdd: () => void;
};

const ReferenceList: React.FC<ReferenceListProps> = ({ title, values, onChange, onDelete, onAdd }) => {
    const t = useTranslations('reference-management');

    return (
        <div style={{ textAlign: 'center' }}>
            <Title level={3}>{title}</Title>
            {values.map(({ key, value }, index) => (
                <Space key={key} style={{ display: 'flex', marginBottom: '8px' }}>
                    <Input
                        value={value}
                        onChange={(e) => onChange(index, e.target.value)}
                    />
                    <Popconfirm
                        title={t('confirm-delete')}
                        onConfirm={() => { onDelete(key); }}
                        okText={t('delete')}
                        cancelText={t('cancel')}
                    >
                        <Button danger>{t('delete')}</Button>
                    </Popconfirm>
                </Space>
            ))}
            <Button style={{ marginTop: 16 }} type='primary' onClick={onAdd}>
                {t('add-item')} {title}
            </Button>
        </div>
    );
};

export default ReferenceList;