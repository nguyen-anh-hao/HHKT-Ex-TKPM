import { Course } from '@/interfaces/CourseResponse';
import {
  getCourses,
  postCourse,
  patchCourse,
  deleteCourse
} from '@/libs/api/courseApi';
import { cleanData } from '@/libs/utils/cleanData';

export const fetchCourses = async (): Promise<Course[]> => {
  try {
    return await getCourses(); // Không cần map transform nữa
  } catch (error) {
    throw error;
  }
};

export const createCourse = async (value: Course) => {
  const requestData = cleanData(value); // Dùng object Course trực tiếp
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
