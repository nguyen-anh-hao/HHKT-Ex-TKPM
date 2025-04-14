import { Course } from '@/interfaces/CourseResponse';
import { CreateCourseRequest } from '@/interfaces/CreateCourseRequest';
import { UpdateCourseRequest } from '@/interfaces/UpdateCourseRequest';
import api from './api';

export const getCourses = async () => {
    try {
        const response = await api.get(`/courses?page=0&size=50`);
        return response.data.data as Course[];
    } catch (error) {
        throw error;
    }
};

export const postCourse = async (course: Partial<CreateCourseRequest>) => {
    try {
        const response = await api.post(`/courses`, course);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const patchCourse = async (courseId: number, course: Partial<UpdateCourseRequest>) => {
    try {
        const response = await api.patch(`/courses/${courseId}`, course);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const deleteCourse = async (courseId: number) => {
    try {
        await api.delete(`/courses/${courseId}`);
    } catch (error) {
        throw error;
    }
};
