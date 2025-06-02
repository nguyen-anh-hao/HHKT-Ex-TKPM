import { useQuery } from '@tanstack/react-query';
import { fetchRegistrations } from '@/libs/services/registerService'; 
import { RegisterResponse } from '@/interfaces/register/RegisterResponse'; 

export const useRegistrations = () => { 
    return useQuery<RegisterResponse[], Error>({
        queryKey: ['registrations'], 
        queryFn: fetchRegistrations, 
        staleTime: 5 * 60 * 1000,
        retry: 1,
    });
};
