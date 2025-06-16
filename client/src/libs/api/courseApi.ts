import { Course } from '@/interfaces/course/Course';
import { CreateCourseRequest } from '@/interfaces/course/CreateCourseRequest';
import { UpdateCourseRequest } from '@/interfaces/course/UpdateCourseRequest';
import { ApiSuccessResponse } from '@/interfaces/ApiResponse';
import api from './api';
import { translateArrayResponse, translateRequest, translateResponse } from '@/libs/utils/translate-helper';

/**
 * Normalized structure for all paginated course responses
 */
interface ApiPaginatedCourseResponse {
  content: Course[];
  totalElements: number;
  number: number;
  size: number;
  totalPages: number;
  numberOfElements: number;
}

/**
 * Gets a list of courses with pagination
 */
export const getCourses = async (page = 0, size = 10, sort = 'courseCode,asc'): Promise<ApiPaginatedCourseResponse> => {
  try {
    const response = await api.get<ApiSuccessResponse<any>>(`/courses`, {
      params: { page, size, sort }
    });
    
    const responseData = response.data.data;
    const paginationInfo = response.data.paginationInfo;
    
    if (Array.isArray(responseData) && paginationInfo) {
      // Standard API format with paginationInfo
      const courses = await translateArrayResponse(responseData as Course[], 'CourseResponse');
      
      return {
        content: courses,
        totalElements: paginationInfo.totalItems,
        number: page,
        size: size,
        totalPages: paginationInfo.totalPages,
        numberOfElements: courses.length
      };
    }
    
    if (responseData && typeof responseData === 'object' && Array.isArray(responseData.content)) {
      // Spring Boot pagination format
      const translatedContent = await translateArrayResponse(
        responseData.content as Course[], 
        'CourseResponse'
      );
      return {
        ...responseData,
        content: translatedContent
      };
    }
    
    // Fallback for unexpected format
    return {
      content: [],
      totalElements: 0,
      number: page,
      size: size,
      totalPages: 0,
      numberOfElements: 0
    };
  } catch (error) {
    console.error('Error fetching courses:', error);
    return {
      content: [],
      totalElements: 0,
      number: page,
      size: size,
      totalPages: 0,
      numberOfElements: 0
    };
  }
};

/**
 * Searches for courses with pagination
 */
export const searchCourses = async (query: string, page = 0, size = 10): Promise<ApiPaginatedCourseResponse> => {
  try {
    const response = await api.get<ApiSuccessResponse<any>>(`/courses/search`, {
      params: { 
        keyword: query,
        page,
        size 
      },
    });
    
    const responseData = response.data.data;
    const paginationInfo = response.data.paginationInfo;
    
    if (Array.isArray(responseData) && paginationInfo) {
      const courses = await translateArrayResponse(responseData as Course[], 'CourseResponse');
      
      return {
        content: courses,
        totalElements: paginationInfo.totalItems,
        number: page,
        size: size,
        totalPages: paginationInfo.totalPages,
        numberOfElements: courses.length
      };
    }
    
    if (responseData && typeof responseData === 'object' && Array.isArray(responseData.content)) {
      const translatedContent = await translateArrayResponse(
        responseData.content as Course[], 
        'CourseResponse'
      );
      return {
        ...responseData,
        content: translatedContent
      };
    }
    
    // Fallback for unexpected format
    return {
      content: [],
      totalElements: 0,
      number: page,
      size: size,
      totalPages: 0,
      numberOfElements: 0
    };
  } catch (error) {
    console.error('Error searching courses:', error);
    return {
      content: [],
      totalElements: 0,
      number: page,
      size: size,
      totalPages: 0,
      numberOfElements: 0
    };
  }
};

export const postCourse = async (course: CreateCourseRequest) => { 
    try {
        const courseTranslated = await translateRequest(course, 'CreateCourseRequest');
        const response = await api.post<ApiSuccessResponse<Course>>(`/courses`, courseTranslated);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const patchCourse = async (courseId: number, course: Partial<UpdateCourseRequest>) => {
    try {
        const courseTranslated = await translateRequest(course, 'UpdateCourseRequest');
        const response = await api.patch<ApiSuccessResponse<Course>>(`/courses/${courseId}`, courseTranslated);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const deleteCourse = async (courseId: number) => {
    try {
        const response = await api.delete<ApiSuccessResponse<void>>(`/courses/${courseId}`);
        return response.data;
    } catch (error) {
        throw error;
    }
};

/**
 * Gets all courses (for transcript service - legacy support)
 */
export const getAllCourses = async (): Promise<Course[]> => {
  try {
    // Get a large page size to fetch all courses at once
    const response = await api.get<ApiSuccessResponse<any>>(`/courses`, {
      params: { page: 0, size: 1000, sort: 'courseCode,asc' }
    });
    
    const responseData = response.data.data;
    const paginationInfo = response.data.paginationInfo;
    
    if (Array.isArray(responseData)) {
      return await translateArrayResponse(responseData as Course[], 'CourseResponse');
    }
    
    if (responseData && typeof responseData === 'object' && Array.isArray(responseData.content)) {
      return await translateArrayResponse(responseData.content as Course[], 'CourseResponse');
    }
    
    return [];
  } catch (error) {
    console.error('Error fetching all courses:', error);
    return [];
  }
};
