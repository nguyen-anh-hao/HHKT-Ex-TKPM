import React, { useState, useRef, useEffect } from 'react';
import { List, Input, Button, Popconfirm } from 'antd';
import { DeleteOutlined, EditOutlined, CheckOutlined, CloseOutlined } from '@ant-design/icons';
import { useTranslations } from 'next-intl';

interface ReferenceListProps {
    title: string;
    values: any[];
    onChange: (index: number, newValue: string) => void;
    onDelete: (id: number) => void;
    editable?: boolean;
}

const ReferenceList: React.FC<ReferenceListProps> = ({ title, values, onChange, onDelete, editable = false }) => {
    const [editIndex, setEditIndex] = useState<number | null>(null);
    const [editValue, setEditValue] = useState<string>('');
    const [isSaving, setIsSaving] = useState(false);
    const inputRef = useRef<any>(null);
    const tCommon = useTranslations('common');

    // Focus input when entering edit mode
    useEffect(() => {
        if (editIndex !== null && inputRef.current) {
            inputRef.current.focus();
        }
    }, [editIndex]);

    const handleEdit = (index: number) => {
        // Get the correct label/name value to edit
        const itemToEdit = values[index];
        const displayValue = itemToEdit.label || itemToEdit.facultyName || 
                            itemToEdit.programName || itemToEdit.studentStatusName || 
                            itemToEdit.domain || '';
        
        setEditIndex(index);
        setEditValue(displayValue);
    };

    const handleSave = () => {
        if (editIndex !== null) {
            setIsSaving(true);
            // This will now trigger immediate API save in the parent component
            onChange(editIndex, editValue);
            setEditIndex(null);
            setIsSaving(false);
        }
    };

    const handleCancel = () => {
        setEditIndex(null);
    };

    return (
        <div style={{ width: '380px' }}>
            <h3 style={{ marginBottom: '12px', textAlign: 'center' }}>{title}</h3>
            <List
                size="small"
                bordered
                dataSource={values}
                renderItem={(item, index) => {
                    // Display the correct name property
                    const displayValue = item.label || item.facultyName || 
                                        item.programName || item.studentStatusName || 
                                        item.domain || '';
                    
                    return (
                        <List.Item
                            actions={
                                editable
                                    ? editIndex === index
                                        ? [
                                            <Button key="save" type="link" icon={<CheckOutlined />} onClick={handleSave} loading={isSaving} />,
                                            <Button key="cancel" type="link" icon={<CloseOutlined />} onClick={handleCancel} disabled={isSaving} />,
                                        ]
                                        : [
                                            <Button key="edit" type="link" icon={<EditOutlined />} onClick={() => handleEdit(index)} />,
                                            <Popconfirm
                                                key="delete"
                                                title={tCommon('confirm-delete')}
                                                onConfirm={() => onDelete(item.key)}
                                            >
                                                <Button type="link" danger icon={<DeleteOutlined />} />
                                            </Popconfirm>,
                                        ]
                                    : []
                            }
                        >
                            {editIndex === index ? (
                                <Input
                                    ref={inputRef}
                                    value={editValue}
                                    onChange={(e) => setEditValue(e.target.value)}
                                    onPressEnter={handleSave}
                                    style={{ width: '100%' }}
                                    disabled={isSaving}
                                />
                            ) : (
                                <span style={{ paddingLeft: '8px' }}>{displayValue}</span>
                            )}
                        </List.Item>
                    );
                }}
                style={{ maxHeight: '400px', overflowY: 'auto' }}
            />
        </div>
    );
};

export default ReferenceList;