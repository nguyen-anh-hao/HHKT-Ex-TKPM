import { useQuery, UseQueryOptions } from '@tanstack/react-query';
import { fetchStudentById, fetchStudents } from '@/libs/services/studentService';
import { Student } from '@/interfaces/student/Student';

// Query keys
export const STUDENTS_QUERY_KEY = 'students';
export const STUDENT_DETAIL_QUERY_KEY = 'student-detail';

/**
 * Hook to fetch a single student by ID
 * @param studentId - The ID of the student to fetch
 * @param options - Additional React Query options
 */
export const useStudent = (
  studentId: string, 
  options?: UseQueryOptions<Student, Error>
) => {
  return useQuery<Student, Error>({
    queryKey: [STUDENT_DETAIL_QUERY_KEY, studentId],
    queryFn: () => fetchStudentById(studentId),
    enabled: !!studentId, // Only run the query if we have a studentId
    staleTime: 5 * 60 * 1000, // 5 minutes
    ...options,
  });
};

/**
 * Hook to fetch all students
 * @param options - Additional React Query options
 */
export const useStudents = (
  options?: UseQueryOptions<Student[], Error>
) => {
  return useQuery<Student[], Error>({
    queryKey: [STUDENTS_QUERY_KEY],
    queryFn: fetchStudents,
    staleTime: 2 * 60 * 1000, // 2 minutes
    ...options,
  });
};
