import { message } from 'antd';
import { Course } from '../../../interfaces/CourseResponse';

export const addCourse = (courses: Course[], newCourse: Course): Course[] => {
  return [...courses, newCourse];
};


export const updateCourse = (courses: Course[], updatedCourse: Course): Course[] => {
  return courses.map(course =>
    course.courseId === updatedCourse.courseId ? updatedCourse : course
  );
};


export const deleteCourse = (courses: Course[], courseId: number): Course[] => {
  return courses.filter(course => course.courseId !== courseId);
};
