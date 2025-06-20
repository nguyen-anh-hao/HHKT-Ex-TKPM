import { useQuery, UseQueryOptions } from '@tanstack/react-query';
import { fetchSemesters, fetchSemesterById } from '@/libs/services/semesterService';
import { Semester } from '@/interfaces/semester/Semester';
import { PaginatedResponse } from '@/interfaces/common/PaginatedResponse';

// Query keys
export const SEMESTERS_QUERY_KEY = 'semesters';
export const SEMESTER_DETAIL_QUERY_KEY = 'semester-detail';

/**
 * Match structure from useStudents.ts
 */
export interface PaginationParams {
  page: number;
  pageSize: number;
  sortField?: string;
  sortOrder?: string;
}

/**
 * Hook to fetch a single semester by ID
 */
export const useSemester = (
  semesterId: number,
  options?: Partial<UseQueryOptions<Semester, Error>>
) => {
  return useQuery<Semester, Error>({
    queryKey: [SEMESTER_DETAIL_QUERY_KEY, semesterId],
    queryFn: () => fetchSemesterById(semesterId),
    enabled: !!semesterId,
    staleTime: 5 * 60 * 1000, // 5 minutes
    ...options,
  });
};

/**
 * Hook for fetching semesters with pagination
 */
export const useSemesters = (
  {
    page = 0,
    pageSize = 10,
    sortField = 'academicYear',
    sortOrder = 'desc'
  }: PaginationParams,
  options?: Partial<UseQueryOptions<PaginatedResponse<Semester>, Error>>
) => {
  return useQuery<PaginatedResponse<Semester>, Error>({
    queryKey: [SEMESTERS_QUERY_KEY, page, pageSize, sortField, sortOrder],
    queryFn: () => fetchSemesters(page, pageSize, `${sortField},${sortOrder}`),
    placeholderData: (previousData) => previousData,
    retry: 1,
    ...options
  });
};