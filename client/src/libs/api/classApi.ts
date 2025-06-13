import { Class } from '@/interfaces/class/Class';
import { CreateClassRequest } from '@/interfaces/class/CreateClassRequest';
import { UpdateClassRequest } from '@/interfaces/class/UpdateClassRequest';
import { ApiSuccessResponse } from '@/interfaces/ApiResponse';
import api from './api';

export const getClasses = async () => {
    try {
        const response = await api.get<ApiSuccessResponse<Class[]>>(`/classes?page=0&size=50&sort=createdAt`);
        return response.data.data as Class[];
    } catch (error) {
        throw error;
    }
};

export const postClass = async (classData: Partial<CreateClassRequest>) => {
    try {
        const response = await api.post<ApiSuccessResponse<Class>>(`/classes`, classData);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const patchClass = async (classId: number, classData: Partial<UpdateClassRequest>) => {
    try {
        const response = await api.patch<ApiSuccessResponse<Class>>(`/classes/${classId}`, classData);
        return response.data;
    } catch (error) {
        throw error;
    }
};
