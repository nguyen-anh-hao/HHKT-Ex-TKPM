import { useQuery } from '@tanstack/react-query';
import { fetchReference } from '@/libs/services/referenceService';

export const useFaculties = () => {
    const { data, error, isLoading } = useQuery({
        queryKey: ['faculties'],
        queryFn: async () => {
            const data = await fetchReference('faculties');
            return data.map((option: any) => ({
                key: option.id,
                value: option.id, // Use id as value consistently
                label: option.facultyName,
                // Include the original name for reference
                facultyName: option.facultyName
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
                value: option.id, // Use id as value consistently
                label: option.programName,
                // Include the original name for reference
                programName: option.programName
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
                value: option.id, // Use id as value consistently
                label: option.studentStatusName,
                // Include the original name for reference
                studentStatusName: option.studentStatusName
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
                value: option.id, // Use id as value consistently
                label: option.domain,
                domain: option.domain
            }));
        },
    });

    return { data, error, isLoading };
}

export const useLecturers = () => {
    const { data, error, isLoading } = useQuery({
        queryKey: ['lecturers'],
        queryFn: async () => {
            const data = await fetchReference('lecturers');
            return data.map((option: any) => ({
                key: option.id,
                value: option.id, // Use id as value consistently
                label: option.fullName,
                fullName: option.fullName
            }));
        },
    });
    
    return { data, error, isLoading };
};

export const useCourses = () => {
    const { data, error, isLoading } = useQuery({
        queryKey: ['courses'],
        queryFn: async () => {
            const data = await fetchReference('courses');
            return data.map((option: any) => ({
                key: option.courseId,
                value: option.courseId, // Use courseId as value consistently
                label: `${option.courseCode} - ${option.courseName}`,
                courseCode: option.courseCode,
                courseName: option.courseName
            }));
        },
    });

    return { data, error, isLoading };
};
