import { useQuery, UseQueryOptions } from '@tanstack/react-query';
import { fetchStudentById, fetchStudents, searchStudents } from '@/libs/services/studentService';
import { Student } from '@/interfaces/student/Student';
import { PaginatedResponse } from '@/interfaces/common/PaginatedResponse';

// Query keys
export const STUDENTS_QUERY_KEY = 'students';
export const STUDENT_DETAIL_QUERY_KEY = 'student-detail';
export const STUDENTS_SEARCH_QUERY_KEY = 'students-search';

export interface PaginationParams {
  page: number;
  pageSize: number;
  sortField?: string;
  sortOrder?: string;
}

/**
 * Hook to fetch a single student by ID
 */
export const useStudent = (
  studentId: string, 
  options?: Partial<UseQueryOptions<Student, Error>>
) => {
  return useQuery<Student, Error>({
    queryKey: [STUDENT_DETAIL_QUERY_KEY, studentId],
    queryFn: () => fetchStudentById(studentId),
    enabled: !!studentId,
    staleTime: 5 * 60 * 1000, // 5 minutes
    ...options,
  });
};

/**
 * Hook for fetching students with pagination
 */
export const useStudents = (
  {
    page = 0,
    pageSize = 10,
    sortField = 'studentId',
    sortOrder = 'asc'
  }: PaginationParams,
  options?: Partial<UseQueryOptions<PaginatedResponse<Student>, Error>>
) => {
  return useQuery<PaginatedResponse<Student>, Error>({
    queryKey: [STUDENTS_QUERY_KEY, page, pageSize, sortField, sortOrder],
    queryFn: () => fetchStudents(page, pageSize, sortField, sortOrder),
    placeholderData: (previousData) => previousData,
    retry: 1,
    ...options
  });
};

/**
 * Hook to search students with pagination
 */
export const useSearchStudents = (
  query: string,
  page = 0,
  pageSize = 10,
  options?: Partial<UseQueryOptions<PaginatedResponse<Student>, Error>>
) => {
  return useQuery<PaginatedResponse<Student>, Error>({
    queryKey: [STUDENTS_SEARCH_QUERY_KEY, query, page, pageSize],
    queryFn: () => searchStudents(query, page, pageSize),
    enabled: query.trim().length > 0,
    placeholderData: (previousData) => previousData,
    retry: 1,
    ...options
  });
};