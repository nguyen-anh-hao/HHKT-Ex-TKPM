import { Button, Form, Input, Modal, Select } from 'antd';
import { useState } from 'react';
import { downloadFile } from '@/libs/services/fileService';
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
            footer={null}
        >
            <Form layout='vertical'>
                <Form.Item style={{ marginTop: 32, marginBottom: 24 }}>
                    <div style={{ marginLeft: 2, marginBottom: 4 }}>{t('file-name')}</div>
                    <div style={{ display: 'flex', flexDirection: 'row', gap: 8 }}>
                        <Input
                            value={fileName}
                            onChange={(e) => setFileName(e.target.value)}
                        />
                        <Select
                            value={fileType}
                            onChange={(value) => setFileType(value)}
                            style={{ width: 120 }}
                            options={[
                                { value: '.json', label: '.JSON' },
                                { value: '.xlsx', label: '.XLSX' },
                            ]}
                        />
                    </div>
                    
                    <div style={{ width: '100%' }}>
                        <div style={{ marginLeft: 2, marginBottom: 4 }}>{t('export-from-page')}</div>
                        <Input
                            type='number'
                            value={page}
                            onChange={(e) => setPage(Number(e.target.value))}
                        />
                    </div>
                    <div style={{ width: '100%' }}>
                        <div style={{ marginLeft: 2, marginBottom: 4 }}>{t('size')}</div>
                        <Input
                            type='number'
                            value={size}
                            onChange={(e) => setSize(Number(e.target.value))}
                        />
                    </div>
                </Form.Item>
            </Form>
            <Button type='primary' onClick={handleExport} loading={isLoading}>
                {t('export-file')}
            </Button>
        </Modal>
    );
};

export default ExportModal;