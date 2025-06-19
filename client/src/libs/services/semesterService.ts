import * as semesterApi from '@/libs/api/semesterApi';
import { Semester } from '@/interfaces/semester/Semester';
import { CreateSemesterRequest } from '@/interfaces/semester/CreateSemesterRequest';
import { UpdateSemesterRequest } from '@/interfaces/semester/UpdateSemesterRequest';
import { PaginatedResponse } from '@/interfaces/common/PaginatedResponse';

/**
 * Fetch semesters with pagination and sorting options
 */
export const fetchSemesters = async (
  page: number = 0,
  pageSize: number = 10,
  sort: string = 'academicYear,desc',
): Promise<PaginatedResponse<Semester>> => {
  try {
    const response = await semesterApi.getSemesters(page, pageSize, sort);
    const semesters = response.content || [];
    const paginationInfo = response.paginationInfo;

    return {
      data: semesters,
      pagination: {
        total: paginationInfo?.totalItems || 0,
        page: paginationInfo?.page || page,
        pageSize: paginationInfo?.limit || pageSize,
        totalPages: paginationInfo?.totalPages || 0
      }
    };

  } catch (error) {
    console.error('Error fetching semesters:', error);
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
 * Fetch all semesters without pagination
 */
export const fetchAllSemesters = async (): Promise<Semester[]> => {
  return await semesterApi.getAllSemesters();
};

/**
 * Fetch a semester by ID
 */
export const fetchSemesterById = async (id: number): Promise<Semester> => {
  if (!id) {
    throw new Error('Semester ID is required');
  }
  return await semesterApi.getSemesterById(id);
};

/**
 * Create a new semester
 */
export const createSemester = async (data: CreateSemesterRequest): Promise<Semester> => {
  // Validate required fields
  if (!data.semester || !data.startDate || !data.endDate || !data.academicYear || !data.lastCancelDate) {
    throw new Error('Missing required semester fields');
  }
  
  return await semesterApi.createSemester(data);
};

/**
 * Update an existing semester
 */
export const updateSemester = async (
  id: number,
  data: Partial<UpdateSemesterRequest>
): Promise<Semester> => {
  if (!id) {
    throw new Error('Semester ID is required');
  }
  
  return await semesterApi.updateSemester(id, data);
};

/**
 * Remove a semester by ID
 */
export const removeSemester = async (id: number): Promise<void> => {
  if (!id) {
    throw new Error('Semester ID is required');
  }
  
  return await semesterApi.deleteSemester(id);
};

/**
 * Get the current active semester
 */
export const fetchCurrentSemester = async (): Promise<Semester> => {
  return await semesterApi.getCurrentSemester();
};

/**
 * Get upcoming semesters
 */
export const fetchUpcomingSemesters = async (): Promise<Semester[]> => {
  return await semesterApi.getUpcomingSemesters();
};

/**
 * Check if semester dates are valid (start date before end date, etc.)
 */
export const validateSemesterDates = (
  startDate: string, 
  endDate: string, 
  lastCancelDate: string
): boolean => {
  const start = new Date(startDate);
  const end = new Date(endDate);
  const lastCancel = new Date(lastCancelDate);
  
  return start < lastCancel && lastCancel < end && start < end;
};