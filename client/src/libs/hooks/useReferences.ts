import { useQuery } from '@tanstack/react-query';
import { fetchReference } from '@/libs/services/referenceService';

export const useFaculties = () => {
    const { data, error, isLoading } = useQuery({
        queryKey: ['faculties'],
        queryFn: async () => {
            const data = await fetchReference('faculties');
            return data.map((option: any) => ({
                key: option.id,
                value: option.facultyName,
                label: option.facultyName,
            }));
        },
    });

    return { data, error, isLoading };
}

export const usePrograms = () => {
    const { data, error, isLoading } = useQuery({
        queryKey: ['programs'],
        queryFn: async () => {
            const data = await fetchReference('programs');
            return data.map((option: any) => ({
                key: option.id,
                value: option.programName,
                label: option.programName,
            }));
        },
    });

    return { data, error, isLoading };
}

export const useStudentStatuses = () => {
    const { data, error, isLoading } = useQuery({
        queryKey: ['student-statuses'],
        queryFn: async () => {
            const data = await fetchReference('student-statuses');
            return data.map((option: any) => ({
                key: option.id,
                value: option.studentStatusName,
                label: option.studentStatusName,
            }));
        },
    });

    return { data, error, isLoading };
}

export const useEmailDomains = () => {
    const { data, error, isLoading } = useQuery({
        queryKey: ['email-domains'],
        queryFn: async () => {
            const data = await fetchReference('email-domains');
            return data.map((option: any) => ({
                key: option.id,
                value: option.domain,
            }));
        },
    });

    return { data, error, isLoading };
}