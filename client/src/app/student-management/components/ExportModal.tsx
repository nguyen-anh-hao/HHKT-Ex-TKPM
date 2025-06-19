import { Button, Form, Input, Modal, Select } from 'antd';
import { useState } from 'react';
import { downloadFile } from '@/libs/services/studentsFileService';
import { message } from 'antd';
import { useTranslations } from 'next-intl';

interface ExportModalProps {
    visible: boolean;
    onCancel: () => void;
}

const ExportModal = ({ visible, onCancel }: ExportModalProps) => {
    const [fileName, setFileName] = useState<string>('data');
    const [fileType, setFileType] = useState<string>('.json');
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [page, setPage] = useState<number>(0);
    const [size, setSize] = useState<number>(10);
    const t = useTranslations('student-management');
    const tMessages = useTranslations('messages');
    const tCommon = useTranslations('common');
    const tValidation = useTranslations('validation');
    
    const handleExport = async () => {
        try {
            setIsLoading(true);
            const response = await downloadFile(fileName + fileType, page, size);
            const link = document.createElement('a');
            link.href = response;
            link.click();
        } catch (error) {
            message.error(tMessages('export-error'));
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <Modal
            title={t('export-title')}
            open={visible}
            onCancel={onCancel}
            footer={[
                <Button key="cancel" onClick={onCancel}>
                    {tCommon('cancel')}
                </Button>,
                <Button key="export" type='primary' onClick={handleExport} loading={isLoading}>
                    {t('export-file')}
                </Button>
            ]}
        >
            <Form layout='vertical'>
                <Form.Item 
                    label={t('file-name')}
                    rules={[{ required: true, message: tValidation('required', { field: t('file-name') }) }]}
                    style={{ marginTop: 16 }}
                >
                    <div style={{ display: 'flex', flexDirection: 'row', gap: 8 }}>
                        <Input
                            value={fileName}
                            onChange={(e) => setFileName(e.target.value)}
                            placeholder="students_export"
                        />
                        <Select
                            value={fileType}
                            onChange={(value) => setFileType(value)}
                            style={{ width: 120 }}
                            options={[
                                { value: '.json', label: '.JSON' },
                                { value: '.xlsx', label: '.XLSX' },
                                { value: '.pdf', label: '.PDF' },
                            ]}
                        />
                    </div>
                </Form.Item>
                
                <Form.Item label={t('export-from-page')}>
                    <Input
                        type='number'
                        min={0}
                        value={page}
                        onChange={(e) => setPage(Number(e.target.value))}
                    />
                </Form.Item>
                
                <Form.Item label={t('size')}>
                    <Input
                        type='number'
                        min={1}
                        max={100}
                        value={size}
                        onChange={(e) => setSize(Number(e.target.value))}
                    />
                </Form.Item>
            </Form>
        </Modal>
    );
};

export default ExportModal;