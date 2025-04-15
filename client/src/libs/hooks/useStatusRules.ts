import { useQuery } from '@tanstack/react-query';
import { fetchStatusRules } from '@/libs/services/statusRuleService';

export const useStatusRules = () => {
    const { data, error, isLoading } = useQuery({
        queryKey: ['status-rules'],
        queryFn: fetchStatusRules,
    });
    
    return { data, error, isLoading };
};
