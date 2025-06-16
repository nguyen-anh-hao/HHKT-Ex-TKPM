import { useMutation, useQueryClient } from '@tanstack/react-query';
import { postCourse, patchCourse, deleteCourse } from '@/libs/api/courseApi';
import { Course } from '@/interfaces/course/Course';
import { COURSES_QUERY_KEY } from './useCourseQuery';

export const useCreateCourse = () => {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: (course: Course) => postCourse(course),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [COURSES_QUERY_KEY] });
    },
  });
};

export const useUpdateCourse = () => {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: (course: Course) => patchCourse(course.courseId, course),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [COURSES_QUERY_KEY] });
    },
  });
};

export const useDeleteCourse = () => {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: (courseId: number) => deleteCourse(courseId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [COURSES_QUERY_KEY] });
    },
  });
};
