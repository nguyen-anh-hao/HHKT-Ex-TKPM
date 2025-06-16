import { StudentResponse } from '../../interfaces/student/StudentResponse';
import { CreateStudentRequest } from '../../interfaces/student/CreateStudentRequest';
import { UpdateStudentRequest } from '../../interfaces/student/UpdateStudentRequest';
import { ApiSuccessResponse } from '@/interfaces/ApiResponse';
import { translateArrayResponse, translateRequest, translateResponse, translateArrayRequest } from '@/libs/utils/translate-helper';

import api from './api';

export const getStudentById = async (studentId: string) => {
    try {
        const response = await api.get<ApiSuccessResponse<StudentResponse>>(`/students/${studentId}`);
        return await translateResponse(response.data.data as StudentResponse, 'StudentResponse');
    } catch (error) {
        throw error;
    }
}

export const getStudents = async () => {
    try {
        const response = await api.get<ApiSuccessResponse<StudentResponse[]>>(`/students?page=0&size=50&sort=studentId`);
        return await translateArrayResponse(response.data.data as StudentResponse[], 'StudentResponse');
    } catch (error) {
        throw error;
    }
}

export const postStudent = async (student: Partial<CreateStudentRequest>) => {
    try {
        const request = await translateRequest(student as CreateStudentRequest, 'CreateStudentRequest');
        const response = await api.post<ApiSuccessResponse<StudentResponse>>(`/students`, request);
        return response.data;
    } catch (error) {
        throw error;
    }
}

export const patchStudent = async (studentId: string, student: Partial<UpdateStudentRequest>) => {
    try { 
        const request = await translateRequest(student as UpdateStudentRequest, 'UpdateStudentRequest');
        const response = await api.patch<ApiSuccessResponse<StudentResponse>>(`/students/${studentId}`, request);
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