import { Course } from '@/interfaces/course/Course';
import { CreateCourseRequest } from '@/interfaces/course/CreateCourseRequest';
import { UpdateCourseRequest } from '@/interfaces/course/UpdateCourseRequest';
import { ApiSuccessResponse } from '@/interfaces/ApiResponse';
import api from './api';

export const getCourses = async () => {
    try {
        const response = await api.get<ApiSuccessResponse<Course[]>>(`/courses?page=0&size=50&sort=createdAt`);
        return response.data.data as Course[];
    } catch (error) {
        throw error;
    }
};

export const postCourse = async (course: CreateCourseRequest) => { 
    try {
        const response = await api.post<ApiSuccessResponse<Course>>(`/courses`, course);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const patchCourse = async (courseId: number, course: Partial<UpdateCourseRequest>) => {
    try {
        const response = await api.patch<ApiSuccessResponse<Course>>(`/courses/${courseId}`, course);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const deleteCourse = async (courseId: number) => {
    try {
        const response = await api.delete<ApiSuccessResponse<void>>(`/courses/${courseId}`);
        return response.data;
    } catch (error) {
        throw error;
    }
};
