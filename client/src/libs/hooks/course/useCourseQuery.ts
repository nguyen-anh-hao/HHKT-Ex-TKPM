import { useQuery } from '@tanstack/react-query';
import { fetchCourses, searchCoursesService } from '@/libs/services/courseService';
import { Course } from '@/interfaces/course/Course';
import { PaginatedResponse } from '@/interfaces/common/PaginatedResponse';

// Query keys
export const COURSES_QUERY_KEY = 'courses';
export const COURSES_SEARCH_QUERY_KEY = 'courses-search';

export interface CoursePaginationParams {
  page: number;
  pageSize: number;
  sortField?: string;
  sortOrder?: string;
}

/**
 * Hook for fetching courses with pagination
 */
export const useCourses = (
  {
    page = 0,
    pageSize = 10,
    sortField = 'courseCode',
    sortOrder = 'asc'
  }: CoursePaginationParams = { page: 0, pageSize: 10 },
  options?: Partial<any>
) => {
  return useQuery<PaginatedResponse<Course>, Error>({
    queryKey: [COURSES_QUERY_KEY, page, pageSize, sortField, sortOrder],
    queryFn: () => fetchCourses(page, pageSize, sortField, sortOrder),
    placeholderData: (previousData) => previousData,
    retry: 1,
    ...options
  });
};

/**
 * Hook to search courses with pagination
 */
export const useSearchCourses = (
  query: string,
  page = 0,
  pageSize = 10,
  options?: Partial<any>
) => {
  return useQuery<PaginatedResponse<Course>, Error>({
    queryKey: [COURSES_SEARCH_QUERY_KEY, query, page, pageSize],
    queryFn: () => searchCoursesService(query, page, pageSize),
    enabled: query.trim().length > 0,
    placeholderData: (previousData) => previousData,
    retry: 1,
    ...options
  });
};
