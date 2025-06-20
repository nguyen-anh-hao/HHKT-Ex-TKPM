import api from './api';
import { Semester } from '@/interfaces/semester/Semester';
import { CreateSemesterRequest } from '@/interfaces/semester/CreateSemesterRequest';
import { UpdateSemesterRequest } from '@/interfaces/semester/UpdateSemesterRequest';
import { PaginatedResponse } from '@/interfaces/common/PaginatedResponse';
import { ApiSuccessResponse } from '@/interfaces/ApiResponse';
import { SemesterResponse } from '@/interfaces/semester/SemesterResponse';

interface ApiPaginatedSemesterResponse {
  content: Semester[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
  numberOfElements: number;
}

/**
 * Get semesters with pagination
 */
export const getSemesters = async (page: number = 0, size: number = 10, sort: string = 'academicYear,desc') => {
  try {
    const response = await api.get<ApiSuccessResponse<any>>('/semesters', { 
      params: { page, size, sort }
    });

    const responseData = response.data.data;
    const paginationInfo = response.data.paginationInfo;
    const semesters = responseData as Semester[];    

    return {
      content: semesters || [],
      paginationInfo: paginationInfo
    }
  } catch (error) {
    console.error('Error fetching semesters:', error);
    return {
      content: [],
      totalElements: 0,
      totalPages: 0,
      number: page,
      size: size,
      numberOfElements: 0
    };
  }
};

/**
 * Get all semesters (no pagination)
 */
export const getAllSemesters = async (): Promise<Semester[]> => {
  try {
    const response = await api.get<ApiSuccessResponse<Semester[]>>('/semesters', {
      params: { page: 0, size: 1000, sort: 'academicYear,desc' }
    });
    return response.data.data || [];
  } catch (error) {
    console.error('Error fetching all semesters:', error);
    return [];
  }
};

/**
 * Get semester by ID
 */
export const getSemesterById = async (id: number): Promise<Semester> => {
  try {
    const response = await api.get<ApiSuccessResponse<Semester>>(`/semesters/${id}`);
    if (!response.data.data) {
      throw new Error(`Semester with ID ${id} not found`);
    }
    return response.data.data;
  } catch (error) {
    console.error(`Error fetching semester ${id}:`, error);
    throw error;
  }
};

/**
 * Create a new semester
 */
export const createSemester = async (semester: CreateSemesterRequest): Promise<Semester> => {
  try {
    const response = await api.post<ApiSuccessResponse<Semester>>('/semesters', semester);
    if (!response.data.data) {
      throw new Error('Failed to create semester');
    }
    return response.data.data;
  } catch (error) {
    console.error('Error creating semester:', error);
    throw error;
  }
};

/**
 * Update an existing semester
 */
export const updateSemester = async (id: number, semester: Partial<UpdateSemesterRequest>): Promise<Semester> => {
  if (!id) {
    throw new Error("Semester ID cannot be null");
  }
  
  try {
    const response = await api.put<ApiSuccessResponse<Semester>>(`/semesters/${id}`, semester);
    if (!response.data.data) {
      throw new Error(`Failed to update semester with ID ${id}`);
    }
    return response.data.data;
  } catch (error) {
    console.error(`Error updating semester ${id}:`, error);
    throw error;
  }
};

/**
 * Delete a semester
 */
export const deleteSemester = async (id: number): Promise<void> => {
  try {
    await api.delete(`/semesters/${id}`);
  } catch (error) {
    console.error(`Error deleting semester ${id}:`, error);
    throw error;
  }
};

/**
 * Get current active semester
 */
export const getCurrentSemester = async (): Promise<Semester> => {
  try {
    const response = await api.get<ApiSuccessResponse<Semester>>('/semesters/current');
    if (!response.data.data) {
      throw new Error('No active semester found');
    }
    return response.data.data;
  } catch (error) {
    console.error('Error fetching current semester:', error);
    throw error;
  }
};

/**
 * Get upcoming semesters
 */
export const getUpcomingSemesters = async (): Promise<Semester[]> => {
  try {
    const response = await api.get<ApiSuccessResponse<Semester[]>>('/semesters/upcoming');
    return response.data.data || [];
  } catch (error) {
    console.error('Error fetching upcoming semesters:', error);
    return [];
  }
};