import { Course } from '@/interfaces/course/Course';
import { CreateCourseRequest } from '@/interfaces/course/CreateCourseRequest';
import { UpdateCourseRequest } from '@/interfaces/course/UpdateCourseRequest';
import { ApiSuccessResponse } from '@/interfaces/ApiResponse';
import api from './api';

import { translateArrayResponse, translateRequest, translateResponse, translateArrayRequest } from '@/libs/utils/translate-helper';

export const getCourses = async () => {
    try {
        const response = await api.get<ApiSuccessResponse<Course[]>>(`/courses?page=0&size=50&sort=createdAt`);
        // return response.data.data as Course[];
        return await translateArrayResponse(response.data.data as Course[], 'CourseResponse');
    } catch (error) {
        throw error;
    }
};

export const postCourse = async (course: CreateCourseRequest) => { 
    try {
        const courseTranslated = await translateRequest(course, 'CreateCourseRequest');
        const response = await api.post<ApiSuccessResponse<Course>>(`/courses`, courseTranslated);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const patchCourse = async (courseId: number, course: Partial<UpdateCourseRequest>) => {
    try {
        const courseTranslated = await translateRequest(course, 'UpdateCourseRequest');
        const response = await api.patch<ApiSuccessResponse<Course>>(`/courses/${courseId}`, courseTranslated);
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
