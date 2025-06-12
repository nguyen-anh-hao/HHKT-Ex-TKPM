import { useQuery } from '@tanstack/react-query';
import { fetchCourses } from '@/libs/services/courseService';

export const useCourses = () => {
    const { data, error, isLoading } = useQuery({
        queryKey: ['courses'],
        queryFn: fetchCourses,
        staleTime: 5 * 60 * 1000,
        retry: 1,
    });

    return { data, error, isLoading };
};
