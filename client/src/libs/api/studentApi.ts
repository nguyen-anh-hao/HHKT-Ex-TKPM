import { StudentResponse } from '../../interfaces/student/StudentResponse';
import { CreateStudentRequest } from '../../interfaces/student/CreateStudentRequest';
import { UpdateStudentRequest } from '../../interfaces/student/UpdateStudentRequest';
import { ApiSuccessResponse } from '@/interfaces/ApiResponse';
import { translateArrayResponse, translateRequest, translateResponse } from '@/libs/utils/translate-helper';
import api from './api';

/**
 * Normalized structure for all paginated student responses
 */
interface ApiPaginatedStudentResponse {
  content: StudentResponse[];
  totalElements: number;
  number: number;
  size: number;
  totalPages: number;
  numberOfElements: number;
}

/**
 * Gets a list of students with pagination
 */
export const getStudents = async (page = 0, size = 10, sort = 'studentId,asc'): Promise<ApiPaginatedStudentResponse> => {
  try {
    const response = await api.get<ApiSuccessResponse<any>>(`/students`, {
      params: { page, size, sort }
    });
    
    const responseData = response.data.data;
    const paginationInfo = response.data.paginationInfo;
    
    if (Array.isArray(responseData) && paginationInfo) {
      // Standard API format with paginationInfo
      const students = await translateArrayResponse(responseData as StudentResponse[], 'StudentResponse');
      
      return {
        content: students,
        totalElements: paginationInfo.totalItems,
        number: page, // Use the page parameter since currentPage might not exist
        size: size, // Use the size parameter since pageSize might not exist
        totalPages: paginationInfo.totalPages,
        numberOfElements: students.length
      };
    }
    
    if (responseData && typeof responseData === 'object' && Array.isArray(responseData.content)) {
      // Spring Boot pagination format
      const translatedContent = await translateArrayResponse(
        responseData.content as StudentResponse[], 
        'StudentResponse'
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
    console.error('Error fetching students:', error);
    return {
      content: [],
      totalElements: 0,
      number: page,
      size: size,
      totalPages: 0,
      numberOfElements: 0
    };
  }
}

/**
 * Searches for students with pagination
 */
export const searchStudents = async (query: string, page = 0, size = 10): Promise<ApiPaginatedStudentResponse> => {
  try {
    const response = await api.get<ApiSuccessResponse<any>>(`/students/search`, {
      params: { 
        keyword: query,
        page,
        size 
      },
    });
    
    const responseData = response.data.data;
    const paginationInfo = response.data.paginationInfo;
    
    if (Array.isArray(responseData) && paginationInfo) {
      const students = await translateArrayResponse(responseData as StudentResponse[], 'StudentResponse');
      
      return {
        content: students,
        totalElements: paginationInfo.totalItems,
        number: page, // Use the page parameter since currentPage might not exist
        size: size, // Use the size parameter since pageSize might not exist
        totalPages: paginationInfo.totalPages,
        numberOfElements: students.length
      };
    }
    
    if (responseData && typeof responseData === 'object' && Array.isArray(responseData.content)) {
      const translatedContent = await translateArrayResponse(
        responseData.content as StudentResponse[], 
        'StudentResponse'
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
    console.error('Error searching students:', error);
    return {
      content: [],
      totalElements: 0,
      number: page,
      size: size,
      totalPages: 0,
      numberOfElements: 0
    };
  }
}

export const getStudentById = async (studentId: string) => {
    try {
        const response = await api.get<ApiSuccessResponse<StudentResponse>>(`/students/${studentId}`);
        return await translateResponse(response.data.data as StudentResponse, 'StudentResponse');
    } catch (error) {
        throw error;
    }
}

export const postStudent = async (student: Partial<CreateStudentRequest>) => {
    try {
        const request = await translateRequest(student as CreateStudentRequest, 'CreateStudentRequest');
        const response = await api.post<ApiSuccessResponse<StudentResponse>>(`/students`, request);
        return response.data;
    } catch (error) {
        throw error;
    }
}

export const patchStudent = async (studentId: string, student: Partial<UpdateStudentRequest>) => {
    try { 
        const request = await translateRequest(student as UpdateStudentRequest, 'UpdateStudentRequest');
        const response = await api.patch<ApiSuccessResponse<StudentResponse>>(`/students/${studentId}`, request);
        return response.data;
    } catch (error) {
        throw error;
    }
}

export const deleteStudent = async (studentId: string) => {
    try {
        const response = await api.delete<ApiSuccessResponse<void>>(`/students/${studentId}`);
        return response.data;
    } catch (error) {
        throw error;
    }
}