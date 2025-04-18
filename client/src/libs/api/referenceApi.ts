import api from './api';

export const getReference = async (type: string) => {
    try {
        const response = await api.get(`/${type}`);
        return response.data;
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
        const response = await api.post(`/${type}`, payload);
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
        const response = await api.put(`/${type}/${id}`, payload);
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
