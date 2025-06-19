import { useQuery } from '@tanstack/react-query';
import { fetchRegistrations, searchRegistrationsService } from '@/libs/services/registerService';
import { RegisterResponse } from '@/interfaces/register/RegisterResponse';
import { PaginatedResponse } from '@/interfaces/common/PaginatedResponse';

// Query keys
export const REGISTRATIONS_QUERY_KEY = 'registrations';
export const REGISTRATIONS_SEARCH_QUERY_KEY = 'registrations-search';

export interface RegisterPaginationParams {
  page: number;
  pageSize: number;
  sortField?: string;
  sortOrder?: string;
}

/**
 * Hook for fetching registrations with pagination
 */
export const useRegistrations = (
  {
    page = 0,
    pageSize = 10,
    sortField = 'id',
    sortOrder = 'asc'
  }: RegisterPaginationParams = { page: 0, pageSize: 10 },
  options?: Partial<any>
) => {
  return useQuery<PaginatedResponse<RegisterResponse>, Error>({
    queryKey: [REGISTRATIONS_QUERY_KEY, page, pageSize, sortField, sortOrder],
    queryFn: () => fetchRegistrations(page, pageSize, sortField, sortOrder),
    placeholderData: (previousData) => previousData,
    retry: 1,
    ...options
  });
};

/**
 * Hook to search registrations with pagination
 */
export const useSearchRegistrations = (
  query: string,
  page = 0,
  pageSize = 10,
  options?: Partial<any>
) => {
  return useQuery<PaginatedResponse<RegisterResponse>, Error>({
    queryKey: [REGISTRATIONS_SEARCH_QUERY_KEY, query, page, pageSize],
    queryFn: () => searchRegistrationsService(query, page, pageSize),
    enabled: query.trim().length > 0,
    placeholderData: (previousData) => previousData,
    retry: 1,
    ...options
  });
};
