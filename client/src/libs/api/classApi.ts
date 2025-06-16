import { ClassResponse } from '@/interfaces/class/ClassResponse';
import { CreateClassRequest } from '@/interfaces/class/CreateClassRequest';
import { UpdateClassRequest } from '@/interfaces/class/UpdateClassRequest';
import { ApiSuccessResponse } from '@/interfaces/ApiResponse';
import { translateArrayResponse, translateRequest, translateResponse } from '@/libs/utils/translate-helper';
import api from './api';

/**
 * Normalized structure for all paginated class responses
 */
interface ApiPaginatedClassResponse {
  content: ClassResponse[];
  totalElements: number;
  number: number;
  size: number;
  totalPages: number;
  numberOfElements: number;
}

/**
 * Gets a list of classes with pagination
 */
export const getClasses = async (page = 0, size = 10, sort = 'classCode,asc'): Promise<ApiPaginatedClassResponse> => {
  try {
    const response = await api.get<ApiSuccessResponse<any>>(`/classes`, {
      params: { page, size, sort }
    });
    
    const responseData = response.data.data;
    const paginationInfo = response.data.paginationInfo;
    
    if (Array.isArray(responseData) && paginationInfo) {
      const classes = await translateArrayResponse(responseData as ClassResponse[], 'ClassResponse');
      
      return {
        content: classes,
        totalElements: paginationInfo.totalItems,
        number: page,
        size: size,
        totalPages: paginationInfo.totalPages,
        numberOfElements: classes.length
      };
    }
    
    if (responseData && typeof responseData === 'object' && Array.isArray(responseData.content)) {
      const translatedContent = await translateArrayResponse(
        responseData.content as ClassResponse[], 
        'ClassResponse'
      );
      return {
        ...responseData,
        content: translatedContent
      };
    }
    
    return {
      content: [],
      totalElements: 0,
      number: page,
      size: size,
      totalPages: 0,
      numberOfElements: 0
    };
  } catch (error) {
    console.error('Error fetching classes:', error);
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
 * Searches for classes with pagination
 */
export const searchClasses = async (query: string, page = 0, size = 10): Promise<ApiPaginatedClassResponse> => {
  try {
    const response = await api.get<ApiSuccessResponse<any>>(`/classes/search`, {
      params: { 
        keyword: query,
        page,
        size 
      },
    });
    
    const responseData = response.data.data;
    const paginationInfo = response.data.paginationInfo;
    
    if (Array.isArray(responseData) && paginationInfo) {
      const classes = await translateArrayResponse(responseData as ClassResponse[], 'ClassResponse');
      
      return {
        content: classes,
        totalElements: paginationInfo.totalItems,
        number: page,
        size: size,
        totalPages: paginationInfo.totalPages,
        numberOfElements: classes.length
      };
    }
    
    if (responseData && typeof responseData === 'object' && Array.isArray(responseData.content)) {
      const translatedContent = await translateArrayResponse(
        responseData.content as ClassResponse[], 
        'ClassResponse'
      );
      return {
        ...responseData,
        content: translatedContent
      };
    }
    
    return {
      content: [],
      totalElements: 0,
      number: page,
      size: size,
      totalPages: 0,
      numberOfElements: 0
    };
  } catch (error) {
    console.error('Error searching classes:', error);
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
 * Gets all classes (for services that need all data - legacy support)
 */
export const getAllClasses = async (): Promise<ClassResponse[]> => {
  try {
    const response = await api.get<ApiSuccessResponse<any>>(`/classes`, {
      params: { page: 0, size: 1000, sort: 'classCode,asc' }
    });
    
    const responseData = response.data.data;
    
    if (Array.isArray(responseData)) {
      return await translateArrayResponse(responseData as ClassResponse[], 'ClassResponse');
    }
    
    if (responseData && typeof responseData === 'object' && Array.isArray(responseData.content)) {
      return await translateArrayResponse(responseData.content as ClassResponse[], 'ClassResponse');
    }
    
    return [];
  } catch (error) {
    console.error('Error fetching all classes:', error);
    return [];
  }
};

export const postClass = async (classData: any) => {
    try {
        const request = await translateRequest(classData, 'CreateClassRequest');
        const response = await api.post<ApiSuccessResponse<ClassResponse>>(`/classes`, request);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const patchClass = async (classId: number, classData: any) => {
    try {
        const request = await translateRequest(classData, 'UpdateClassRequest');
        const response = await api.patch<ApiSuccessResponse<ClassResponse>>(`/classes/${classId}`, request);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const deleteClass = async (classId: number) => {
    try {
        const response = await api.delete<ApiSuccessResponse<void>>(`/classes/${classId}`);
        return response.data;
    } catch (error) {
        throw error;
    }
};
