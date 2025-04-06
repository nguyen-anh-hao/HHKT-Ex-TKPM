import { getFile, postFile } from '../api/fileApi';

export const uploadFile = async (file: File) => {
    try {
        const response = await postFile(file);
        return response;
    } catch (error) {
        throw error;
    }
}
export const downloadFile = async (fileName: string, page: number, size: number) => {
    try {
        const response = await getFile(fileName, page, size);
        return response;
    } catch (error) {
        throw error;
    }
}