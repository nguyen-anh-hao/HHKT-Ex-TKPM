import { getCourses, searchCourses, postCourse, patchCourse, deleteCourse } from '@/libs/api/courseApi';
import { Course } from '@/interfaces/course/Course';
import { PaginatedResponse } from '@/interfaces/common/PaginatedResponse';

/**
 * Fetches courses with pagination support
 */
export const fetchCourses = async (
  page: number = 0, 
  pageSize: number = 10,
  sortField: string = 'courseCode',
  sortOrder: string = 'asc'
): Promise<PaginatedResponse<Course>> => {
  try {
    const response = await getCourses(page, pageSize, `${sortField},${sortOrder}`);
    
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
    console.error('Error fetching courses:', error);
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
 * Searches for courses by keyword with pagination
 */
export const searchCoursesService = async (
  keyword: string,
  page: number = 0,
  pageSize: number = 10
): Promise<PaginatedResponse<Course>> => {
  try {
    const response = await searchCourses(keyword, page, pageSize);
    
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
    console.error('Error searching courses:', error);
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

// Re-export other functions for backward compatibility
export { postCourse as createCourse, patchCourse as updateCourse, deleteCourse };
