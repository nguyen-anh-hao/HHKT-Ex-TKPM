import { getRegistrations, searchRegistrations, getAllRegistrations, postRegister, patchRegister, deleteRegister } from '@/libs/api/registerApi';
import { RegisterResponse } from '@/interfaces/register/RegisterResponse';
import { PaginatedResponse } from '@/interfaces/common/PaginatedResponse';

/**
 * Fetches registrations with pagination support
 */
export const fetchRegistrations = async (
  page: number = 0, 
  pageSize: number = 10,
  sortField: string = 'id',
  sortOrder: string = 'asc'
): Promise<PaginatedResponse<RegisterResponse>> => {
  try {
    const response = await getRegistrations(page, pageSize, `${sortField},${sortOrder}`);
    
    return {
      data: response.content || [],
      pagination: {
        total: response.totalElements || 0,
        page: response.number || page,
        pageSize: response.size || pageSize,
        totalPages: response.totalPages || 0
      }
    };
  } catch (error) {
    console.error('Error fetching registrations:', error);
    return {
      data: [],
      pagination: {
        total: 0,
        page,
        pageSize,
        totalPages: 0
      }
    };
  }
};

/**
 * Searches for registrations by keyword with pagination
 */
export const searchRegistrationsService = async (
  keyword: string,
  page: number = 0,
  pageSize: number = 10
): Promise<PaginatedResponse<RegisterResponse>> => {
  try {
    const response = await searchRegistrations(keyword, page, pageSize);
    
    return {
      data: response.content || [],
      pagination: {
        total: response.totalElements || 0,
        page: response.number || page,
        pageSize: response.size || pageSize,
        totalPages: response.totalPages || 0
      }
    };
  } catch (error) {
    console.error('Error searching registrations:', error);
    return {
      data: [],
      pagination: {
        total: 0,
        page,
        pageSize,
        totalPages: 0
      }
    };
  }
};

/**
 * Fetches all registrations (legacy support)
 */
export const fetchAllRegistrations = async (): Promise<RegisterResponse[]> => {
  try {
    return await getAllRegistrations();
  } catch (error) {
    console.error('Error fetching all registrations:', error);
    return [];
  }
};

// Re-export other functions for backward compatibility
export { postRegister as createRegister, patchRegister as updateRegister, deleteRegister };
