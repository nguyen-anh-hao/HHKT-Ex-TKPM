import { RegisterRequest } from '@/interfaces/CreateRegisterRequest';
import { RegisterResponse } from '@/interfaces/RegisterResponse';
import api from './api';

export const getRegistrations = async () => {
    try {
        const response = await api.get(`/class-registrations?page=0&size=50`);
        return response.data.data as RegisterResponse[];
    } catch (error) {
        throw error;
    }
};

export const getRegistrationById = async (id: number) => {
    try {
        const response = await api.get(`/class-registrations/${id}`);
        return response.data.data as RegisterResponse;
    } catch (error) {
        throw error;
    }
};

export const postRegistration = async (data: RegisterRequest) => {
    try {
        const response = await api.post(`/class-registrations`, data);
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
        const response = await api.patch(`/class-registrations/${id}`, data);
        return response.data.data as RegisterResponse;
    } catch (error) {
        throw error;
    }
};
