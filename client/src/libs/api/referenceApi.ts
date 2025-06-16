import api from './api';
import { translateArrayResponse, translateRequest, translateResponse, translateArrayRequest } from '@/libs/utils/translate-helper';

export const getReference = async (type: string) => {
    try {
        const response = await api.get(`/${type}`);
        const translatedData = await translateArrayResponse(response.data.data, 'ReferenceResponse');
        return translatedData;
        // return await translateArrayResponse(response.data, 'ReferenceResponse');
    } catch (error) {
        throw error;
    }
};

export const postReference = async (type: string, value: any) => {
    const payloadMap: Record<string, string> = {
        'faculties': 'facultyName',
        'programs': 'programName',
        'student-statuses': 'studentStatusName',
        'email-domains': 'domain',
        'lecturers': 'lecturerName',
        'courses': 'courseName',
    };

    const key = payloadMap[type];
    const payload = key ? { [key]: value } : {};

    try {
        const payloadTranslated = await translateRequest(payload, 'CreateReferenceRequest');
        const response = await api.post(`/${type}`, payloadTranslated);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const putReference = async (type: string, id: number, value: any) => {
    const payloadMap: Record<string, string> = {
        'faculties': 'facultyName',
        'programs': 'programName',
        'student-statuses': 'studentStatusName',
        'email-domains': 'domain',
        'lecturers': 'lecturerName',
        'courses': 'courseName',
    };

    const key = payloadMap[type];
    const payload = key ? { [key]: value } : {};

    try {
        const payloadTranslated = await translateRequest(payload, 'UpdateReferenceRequest');
        const response = await api.put(`/${type}/${id}`, payloadTranslated);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const deleteReference = async (type: string, id: number) => {
    try {
        const response = await api.delete(`/${type}/${id}`);
        return response.data;
    } catch (error) {
        throw error;
    }
};
