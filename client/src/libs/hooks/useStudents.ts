import { useQuery } from '@tanstack/react-query';
import { fetchStudentById, fetchStudents } from '@/libs/services/studentService';

export const useStudent = (studentId: string) => {
    const { data, error, isLoading } = useQuery({
        queryKey: ['student', studentId],
        queryFn: () => {
            console.log('studentId', studentId);
            return fetchStudentById(studentId);
        },
        enabled: !!studentId, // tránh gọi API khi studentId chưa có
    });

    return { data, error, isLoading };
};

export const useStudents = () => {
    const { data, error, isLoading } = useQuery({
        queryKey: ['students'],
        queryFn: fetchStudents,
    });
    
    return { data, error, isLoading };
};
