import { useQuery } from '@tanstack/react-query';
import { fetchClasses, searchClassesService } from '@/libs/services/classService';
import { Class } from '@/interfaces/class/Class';
import { PaginatedResponse } from '@/interfaces/common/PaginatedResponse';

// Query keys
export const CLASSES_QUERY_KEY = 'classes';
export const CLASSES_SEARCH_QUERY_KEY = 'classes-search';

export interface ClassPaginationParams {
  page: number;
  pageSize: number;
  sortField?: string;
  sortOrder?: string;
}

/**
 * Hook for fetching classes with pagination
 */
export const useClasses = (
  {
    page = 0,
    pageSize = 10,
    sortField = 'classCode',
    sortOrder = 'asc'
  }: ClassPaginationParams = { page: 0, pageSize: 10 },
  options?: Partial<any>
) => {
  return useQuery<PaginatedResponse<Class>, Error>({
    queryKey: [CLASSES_QUERY_KEY, page, pageSize, sortField, sortOrder],
    queryFn: () => fetchClasses(page, pageSize, sortField, sortOrder),
    placeholderData: (previousData) => previousData,
    retry: 1,
    ...options
  });
};

/**
 * Hook to search classes with pagination
 */
export const useSearchClasses = (
  query: string,
  page = 0,
  pageSize = 10,
  options?: Partial<any>
) => {
  return useQuery<PaginatedResponse<Class>, Error>({
    queryKey: [CLASSES_SEARCH_QUERY_KEY, query, page, pageSize],
    queryFn: () => searchClassesService(query, page, pageSize),
    enabled: query.trim().length > 0,
    placeholderData: (previousData) => previousData,
    retry: 1,
    ...options
  });
};
