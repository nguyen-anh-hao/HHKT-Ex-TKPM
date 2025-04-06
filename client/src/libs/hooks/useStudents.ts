import { useQuery } from '@tanstack/react-query';
import { fetchStudents } from '@/libs/services/studentService';

export const useStudents = () => {
    const { data, error, isLoading } = useQuery({
        queryKey: ['students'],
        queryFn: fetchStudents,
    });
    
    return { data, error, isLoading };
};
