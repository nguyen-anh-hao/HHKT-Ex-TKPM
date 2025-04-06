import { Button, Form, Modal, Upload, Typography } from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import { message } from 'antd';
import { useState } from 'react';
import type { RcFile, UploadRequestOption } from 'rc-upload/lib/interface';
import { uploadFile } from '@/libs/services/fileService';

const BASE_URL = 'http://localhost:9000/api';

interface ImportModalProps {
    visible: boolean;
    onCancel: () => void;
}

const ImportModal = ({ visible, onCancel }: ImportModalProps) => {
    const [fileName, setFileName] = useState<string>('');
    const [uploading, setUploading] = useState(false);

    const handleUpload = async (options: UploadRequestOption) => {
        const { file, onSuccess, onError } = options;
        const realFile = file as RcFile;
        setFileName(realFile.name);

        try {
            setUploading(true);
            setFileName('');
            const result = await uploadFile(realFile);
            if (result.status !== 200) {
                message.error(`Tệp ${realFile.name} tải lên thất bại: ${result}`);
                return;
            }
            message.success(`Tệp ${realFile.name} tải lên thành công.`);
            onSuccess?.(result, new XMLHttpRequest());
            onCancel();
        } catch (error: any) {
            const errorMessage = error.response?.data?.message || 'Đã xảy ra lỗi khi tải lên.';
            message.error(`Tệp ${realFile.name} tải lên thất bại: ${errorMessage}`);
            onError?.(new Error(errorMessage));
        } finally {
            setUploading(false);
        }
    };

    return (
        <Modal
            title='Nhập dữ liệu sinh viên'
            open={visible}
            onCancel={onCancel}
            footer={null}
        >
            <Form layout='vertical'>
                <Form.Item style={{ marginTop: 32, marginBottom: 24, display: 'flex', justifyContent: 'center' }}>
                    <Upload
                        customRequest={handleUpload}
                        showUploadList={false}
                        beforeUpload={(file) => {
                            const isValidType = file.type === 'application/json';
                            if (!isValidType) {
                                message.error('Chỉ cho phép tải lên file .json');
                            }
                            return isValidType;
                        }}
                    >
                        <Button icon={<UploadOutlined />} loading={uploading}>
                            Chọn tệp để tải lên
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
