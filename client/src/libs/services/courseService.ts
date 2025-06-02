import { Course } from '@/interfaces/Course';
import {
    getCourses,
    postCourse,
    patchCourse,
    deleteCourse
} from '@/libs/api/courseApi';
import { cleanData } from '@/libs/utils/cleanData';
import { transformCourseToPostRequest } from '@/libs/utils/courseTransform';
import { transformCourseToGetResponse } from '@/libs/utils/courseTransform';

export const fetchCourses = async () => {
    try {
        const response = await getCourses();
        return response.map(transformCourseToGetResponse);
    } catch (error) {
        throw error;
    }
};

export const createCourse = async (value: Course) => {
    const requestData = transformCourseToPostRequest(value); // Dùng object Course trực tiếp
    try {
        const data = await postCourse(requestData);
        return data;
    } catch (error) {
        throw error;
    }
};

export const updateCourse = async (value: Course) => {
    const requestData = cleanData(value);
    try {
        const data = await patchCourse(value.courseId, requestData);
        return data;
    } catch (error) {
        throw error;
    }
};

export const removeCourse = async (courseId: number) => {
    try {
        return await deleteCourse(courseId);
    } catch (error) {
        throw error;
    }
};
