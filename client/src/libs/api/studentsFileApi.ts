import api from './api';

const BASE_URL = 'http://localhost:9000/api';

export const getFile = async (fileName: string, page: number, size: number) => {
    try {
        const type = fileName.split('.').pop() || 'json';
        const fileNameWithoutExt = fileName.split('.').slice(0, -1).join('.');
        return `${BASE_URL}/file-transfer/export?type=${type}&fileName=${fileNameWithoutExt}&page=${page}&size=${size}`;
    } catch (error) {
        throw error;
    }
}

export const postFile = async (file: File) => {
    const ext = file.name.split('.').pop() || 'json';
    const formData = new FormData();
    formData.append('file', file);

    try {
        const response = await api.post(`/file-transfer/import?type=${ext}`,
            formData,
            {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            }
        );

        return response;
    } catch (error) {
        throw error;
    }
}