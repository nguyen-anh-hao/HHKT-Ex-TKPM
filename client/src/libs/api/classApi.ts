import { Class } from '@/interfaces/ClassResponse';
import { CreateClassRequest } from '@/interfaces/CreateClassRequest';
import { UpdateClassRequest } from '@/interfaces/UpdateClassRequest';
import api from './api';

export const getClasses = async () => {
    try {
        const response = await api.get(`/classes?page=0&size=50&sort=createdAt`);
        return response.data.data as Class[];
    } catch (error) {
        throw error;
    }
};

export const postClass = async (classData: Partial<CreateClassRequest>) => {
    try {
        const response = await api.post(`/classes`, classData);
        return response.data;
    } catch (error) {
        throw error;
    }
};

