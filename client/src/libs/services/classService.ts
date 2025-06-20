import { getClasses, searchClasses, getAllClasses, postClass, patchClass, deleteClass } from '@/libs/api/classApi';
import { Class } from '@/interfaces/class/Class';
import { PaginatedResponse } from '@/interfaces/common/PaginatedResponse';

/**
 * Fetches classes with pagination support
 */
export const fetchClasses = async (
  page: number = 0, 
  pageSize: number = 10,
  sortField: string = 'classCode',
  sortOrder: string = 'asc'
): Promise<PaginatedResponse<Class>> => {
  try {
    const response = await getClasses(page, pageSize, `${sortField},${sortOrder}`);
    
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
    console.error('Error fetching classes:', error);
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
 * Searches for classes by keyword with pagination
 */
export const searchClassesService = async (
  keyword: string,
  page: number = 0,
  pageSize: number = 10
): Promise<PaginatedResponse<Class>> => {
  try {
    const response = await searchClasses(keyword, page, pageSize);
    
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
    console.error('Error searching classes:', error);
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
 * Fetches all classes (legacy support)
 */
export const fetchAllClasses = async (): Promise<Class[]> => {
  try {
    return await getAllClasses();
  } catch (error) {
    console.error('Error fetching all classes:', error);
    return [];
  }
};

// Re-export other functions for backward compatibility
export { postClass as createClass, patchClass as updateClass, deleteClass };


