import { RegisterRequest } from '@/interfaces/register/CreateRegisterRequest';
import { RegisterResponse } from '@/interfaces/register/RegisterResponse';
import { ApiSuccessResponse } from '@/interfaces/ApiResponse';
import api from './api';

export const getRegistrations = async () => {
    try {
        const response = await api.get<ApiSuccessResponse<RegisterResponse[]>>(`/class-registrations?page=0&size=50&sort=createdAt`);
        return response.data.data as RegisterResponse[];
    } catch (error) {
        throw error;
    }
};

export const getRegistrationById = async (id: number) => {
    try {
        const response = await api.get<ApiSuccessResponse<RegisterResponse>>(`/class-registrations/${id}`);
        return response.data.data as RegisterResponse;
    } catch (error) {
        throw error;
    }
};

export const postRegistration = async (data: RegisterRequest) => {
    try {
        const response = await api.post<ApiSuccessResponse<RegisterResponse>>(`/class-registrations`, data);
        return response.data.data as RegisterResponse;
    } catch (error) {
        throw error;
    }
};

export const patchRegistration = async (
    id: number,
    data: Partial<RegisterRequest>
) => {
    try {
        const response = await api.patch<ApiSuccessResponse<RegisterResponse>>(`/class-registrations/${id}`, data);
        return response.data.data as RegisterResponse;
    } catch (error) {
        throw error;
    }
};
