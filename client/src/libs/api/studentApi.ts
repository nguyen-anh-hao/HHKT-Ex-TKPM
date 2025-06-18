import { StudentResponse } from '../../interfaces/student/StudentResponse';
import { CreateStudentRequest } from '../../interfaces/student/CreateStudentRequest';
import { UpdateStudentRequest } from '../../interfaces/student/UpdateStudentRequest';
import { ApiSuccessResponse } from '@/interfaces/ApiResponse';

import api from './api';

export const getStudentById = async (studentId: string) => {
    try {
        const response = await api.get<ApiSuccessResponse<StudentResponse>>(`/students/${studentId}`);
        return response.data.data as StudentResponse;
    } catch (error) {
        throw error;
    }
}

export const getStudents = async () => {
    try {
        const response = await api.get<ApiSuccessResponse<StudentResponse[]>>(`/students?page=0&size=50&sort=createdAt`);
        return response.data.data as StudentResponse[];
    } catch (error) {
        throw error;
    }
}

export const postStudent = async (student: Partial<CreateStudentRequest>) => {
    try {
        const response = await api.post<ApiSuccessResponse<StudentResponse>>(`/students`, student);
        return response.data;
    } catch (error) {
        throw error;
    }
}

export const patchStudent = async (studentId: string, student: Partial<UpdateStudentRequest>) => {
    try { 
        const response = await api.patch<ApiSuccessResponse<StudentResponse>>(`/students/${studentId}`, student);
        return response.data;
    } catch (error) {
        throw error;
    }
}

export const deleteStudent = async (studentId: string) => {
    try {
        const response = await api.delete<ApiSuccessResponse<void>>(`/students/${studentId}`);
        return response.data;
    } catch (error) {
        throw error;
    }
}