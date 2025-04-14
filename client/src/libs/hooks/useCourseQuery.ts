import { useQuery } from '@tanstack/react-query';
import { fetchCourses } from '@/libs/services/courseService';
import { Course } from '@/interfaces/CourseResponse';

export const useCourses = () => {
    return useQuery<Course[], Error>({
        queryKey: ['courses'],
        queryFn: fetchCourses,
        staleTime: 5 * 60 * 1000,
        retry: 1,
    });
};
