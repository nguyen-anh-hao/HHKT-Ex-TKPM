import { StudentResponse } from '../../interfaces/StudentResponse';
import { CreateStudentRequest } from '../../interfaces/CreateStudentRequest';
import { UpdateStudentRequest } from '../../interfaces/UpdateStudentRequest';

import api from './api';

export const getStudentById = async (studentId: string) => {
    try {
        const response = await api.get(`/students/${studentId}`);
        return response.data.data as StudentResponse;
    } catch (error) {
        throw error;
    }
}

export const getStudents = async () => {
    try {
        const response = await api.get(`/students?page=0&size=50&sort=studentId`);
        return response.data.data as StudentResponse[];
    } catch (error) {
        throw error;
    }
}

export const postStudent = async (student: Partial<CreateStudentRequest>) => {
    try {
        const response = await api.post(`/students`, student);
        return response.data;
    } catch (error) {
        throw error;
    }
}

export const patchStudent = async (studentId: string, student: Partial<UpdateStudentRequest>) => {
    try { 
        const response = await api.patch(`/students/${studentId}`, student);
        return response.data;
    } catch (error) {
        throw error;
    }
}

export const deleteStudent = async (studentId: string) => {
    try {
        await api.delete(`/students/${studentId}`);
    } catch (error) {
        throw error;
    }
}