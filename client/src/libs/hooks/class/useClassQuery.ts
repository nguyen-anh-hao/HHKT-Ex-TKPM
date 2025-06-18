import { useQuery } from '@tanstack/react-query';
import { fetchClasses } from '@/libs/services/classService'; 
import { Class } from '@/interfaces/class/ClassResponse'; 

export const useClasses = () => { 
    return useQuery<Class[], Error>({
        queryKey: ['classes'],
        queryFn: fetchClasses, 
        staleTime: 5 * 60 * 1000,
        retry: 1,
    });
};
