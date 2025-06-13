import { Button, Form, Modal, Upload, Typography } from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import { message } from 'antd';
import { useState } from 'react';
import type { RcFile, UploadRequestOption } from 'rc-upload/lib/interface';
import { uploadFile } from '@/libs/services/studentsFileService';
import { useTranslations } from 'next-intl';

const BASE_URL = 'http://localhost:9000/api';

interface ImportModalProps {
    visible: boolean;
    onCancel: () => void;
}

const ImportModal = ({ visible, onCancel }: ImportModalProps) => {
    const [fileName, setFileName] = useState<string>('');
    const [uploading, setUploading] = useState(false);
    const t = useTranslations('student-management');
    const tMessages = useTranslations('messages');
    const tCommon = useTranslations('common');
    const tValidation = useTranslations('validation');

    const handleUpload = async (options: UploadRequestOption) => {
        const { file, onSuccess, onError } = options;
        const realFile = file as RcFile;
        setFileName(realFile.name);

        try {
            setUploading(true);
            const result = await uploadFile(realFile);
            
            if (result.status !== 200) {
                const errorMessage = result.data?.message || result.statusText || tMessages('upload-error');
                message.error(`${tMessages('import-error', { fileName: realFile.name })}: ${errorMessage}`);
                onError?.(new Error(errorMessage));
                return;
            }
            
            message.success(tMessages('import-success', { fileName: realFile.name }));
            onSuccess?.(result, new XMLHttpRequest());
            setTimeout(() => onCancel(), 1000); // Close after success with delay
        } catch (error: any) {
            const errorMessage = error.response?.data?.message || tMessages('upload-error');
            message.error(`${tMessages('import-error', { fileName: realFile.name })}: ${errorMessage}`);
            onError?.(new Error(errorMessage));
        } finally {
            setUploading(false);
        }
    };

    return (
        <Modal
            title={t('import-title')}
            open={visible}
            onCancel={onCancel}
            footer={[
                <Button key="cancel" onClick={onCancel}>
                    {tCommon('cancel')}
                </Button>
            ]}
        >
            <Form layout='vertical'>
                <Form.Item style={{ marginTop: 32, marginBottom: 24, display: 'flex', justifyContent: 'center' }}>
                    <Upload
                        customRequest={handleUpload}
                        showUploadList={false}
                        accept=".json"
                        beforeUpload={(file) => {
                            const isValidType = file.type === 'application/json';
                            if (!isValidType) {
                                message.error(t('invalid-file-type'));
                            }
                            return isValidType;
                        }}
                    >
                        <Button icon={<UploadOutlined />} loading={uploading}>
                            {t('select-file')}
                        </Button>
                    </Upload>
                </Form.Item>

                {fileName && (
                    <Form.Item style={{ marginBottom: 24, display: 'flex', justifyContent: 'center' }}>
                        <Typography.Text type='secondary'>{fileName}</Typography.Text>
                    </Form.Item>
                )}
            </Form>
        </Modal>
    );
};

export default ImportModal;
