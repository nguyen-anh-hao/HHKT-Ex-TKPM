import { RegisterResponse } from '@/interfaces/register/RegisterResponse';
import { RegisterRequest } from '@/interfaces/register/CreateRegisterRequest'; // Fixed: Use RegisterRequest instead of CreateRegisterRequest
import { ApiSuccessResponse } from '@/interfaces/ApiResponse';
import { translateArrayResponse, translateRequest, translateResponse } from '@/libs/utils/translate-helper';
import api from './api';

/**
 * Normalized structure for all paginated register responses
 */
interface ApiPaginatedRegisterResponse {
  content: RegisterResponse[];
  totalElements: number;
  number: number;
  size: number;
  totalPages: number;
  numberOfElements: number;
}

/**
 * Gets a list of registrations with pagination
 */
export const getRegistrations = async (page = 0, size = 10, sort = 'id,asc'): Promise<ApiPaginatedRegisterResponse> => {
  try {
    const response = await api.get<ApiSuccessResponse<any>>(`/class-registrations`, {
      params: { page, size, sort }
    });
    
    const responseData = response.data.data;
    const paginationInfo = response.data.paginationInfo;
    
    if (Array.isArray(responseData) && paginationInfo) {
      const registrations = await translateArrayResponse(responseData as RegisterResponse[], 'RegisterResponse');
      
      return {
        content: registrations,
        totalElements: paginationInfo.totalItems,
        number: page,
        size: size,
        totalPages: paginationInfo.totalPages,
        numberOfElements: registrations.length
      };
    }
    
    if (responseData && typeof responseData === 'object' && Array.isArray(responseData.content)) {
      const translatedContent = await translateArrayResponse(
        responseData.content as RegisterResponse[], 
        'RegisterResponse'
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
    console.error('Error fetching registrations:', error);
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
 * Searches for registrations with pagination
 */
export const searchRegistrations = async (query: string, page = 0, size = 10): Promise<ApiPaginatedRegisterResponse> => {
  try {
    const response = await api.get<ApiSuccessResponse<any>>(`/class-registrations/search`, {
      params: { 
        keyword: query,
        page,
        size 
      },
    });
    
    const responseData = response.data.data;
    const paginationInfo = response.data.paginationInfo;
    
    if (Array.isArray(responseData) && paginationInfo) {
      const registrations = await translateArrayResponse(responseData as RegisterResponse[], 'RegisterResponse');
      
      return {
        content: registrations,
        totalElements: paginationInfo.totalItems,
        number: page,
        size: size,
        totalPages: paginationInfo.totalPages,
        numberOfElements: registrations.length
      };
    }
    
    if (responseData && typeof responseData === 'object' && Array.isArray(responseData.content)) {
      const translatedContent = await translateArrayResponse(
        responseData.content as RegisterResponse[], 
        'RegisterResponse'
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
    console.error('Error searching registrations:', error);
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
 * Gets all registrations (for services that need all data - legacy support)
 */
export const getAllRegistrations = async (): Promise<RegisterResponse[]> => {
  try {
    const response = await api.get<ApiSuccessResponse<any>>(`/class-registrations`, {
      params: { page: 0, size: 1000, sort: 'id,asc' }
    });
    
    const responseData = response.data.data;
    
    if (Array.isArray(responseData)) {
      return await translateArrayResponse(responseData as RegisterResponse[], 'RegisterResponse');
    }
    
    if (responseData && typeof responseData === 'object' && Array.isArray(responseData.content)) {
      return await translateArrayResponse(responseData.content as RegisterResponse[], 'RegisterResponse');
    }
    
    return [];
  } catch (error) {
    console.error('Error fetching all registrations:', error);
    return [];
  }
};

// ...existing CRUD operations...
export const postRegister = async (registerData: any) => {
    try {
        const request = await translateRequest(registerData, 'RegisterRequest'); // Fixed: Use RegisterRequest
        const response = await api.post<ApiSuccessResponse<RegisterResponse>>(`/class-registrations`, request);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const patchRegister = async (registerId: number, registerData: any) => {
    try {
        const request = await translateRequest(registerData, 'RegisterRequest'); // Fixed: Use RegisterRequest for updates too
        const response = await api.patch<ApiSuccessResponse<RegisterResponse>>(`/class-registrations/${registerId}`, request);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const deleteRegister = async (registerId: number) => {
    try {
        const response = await api.delete<ApiSuccessResponse<void>>(`/class-registrations/${registerId}`);
        return response.data;
    } catch (error) {
        throw error;
    }
};
