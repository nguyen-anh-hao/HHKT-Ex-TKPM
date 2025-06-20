import { getStudentById, getStudents, postStudent, patchStudent, deleteStudent } from '@/libs/api/studentApi';
import { searchStudents as apiSearchStudents } from '@/libs/api/studentApi';
import { Student } from '@/interfaces/student/Student';
import { cleanData } from '../utils/cleanData';
import { transformStudentToPostRequest, transformStudentToPatchRequest, transformGetResponseToStudent } from '../utils/transform/studentTransform';
import { StudentResponse } from '@/interfaces/student/StudentResponse';
import { handleApiError } from '../utils/errorUtils';
import { PaginatedResponse } from '@/interfaces/common/PaginatedResponse';

/**
 * Fetches a student by ID
 * @param studentId - The ID of the student to fetch
 */
export const fetchStudentById = async (studentId: string): Promise<Student> => {
    try {
        const response = await getStudentById(studentId);
        return transformGetResponseToStudent(response);
    } catch (error) {
        return handleApiError(error, `fetchStudentById(${studentId})`);
    }
};

/**
 * Fetches students with pagination support
 */
export const fetchStudents = async (
  page: number = 0, 
  pageSize: number = 10,
  sortField: string = 'studentId',
  sortOrder: string = 'asc'
): Promise<PaginatedResponse<Student>> => {
  try {
    const response = await getStudents(page, pageSize, `${sortField},${sortOrder}`);
    const students = (response.content || []).map(transformGetResponseToStudent);
    
    return {
      data: students,
      pagination: {
        total: response.totalElements || 0,
        page: response.number || page,
        pageSize: response.size || pageSize,
        totalPages: response.totalPages || 0
      }
    };
  } catch (error) {
    console.error('Error fetching students:', error);
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
 * Creates a new student
 * @param student - The student data to create
 */
export const createStudent = async (student: Student): Promise<StudentResponse> => {
    try {
        const requestData = cleanData(transformStudentToPostRequest(student));
        const { data } = await postStudent(requestData);
        if (!data) {
            throw new Error('Failed to create student: response data is null');
        }
        return data;
    } catch (error) {
        console.error('Error creating student:', error);
        throw error;
    }
};

/**
 * Updates an existing student
 * @param student - The student data to update
 */
export const updateStudent = async (student: Student): Promise<StudentResponse> => {
    try {
        const requestData = cleanData(transformStudentToPatchRequest(student));
        console.log('Request data for updating student:', requestData);
        const { data } = await patchStudent(student.studentId, requestData);
        if (!data) {
            throw new Error('Failed to update student: response data is null');
        }
        return data;
    } catch (error) {
        console.error(`Error updating student ${student.studentId}:`, error);
        throw error;
    }
};

/**
 * Removes a student
 * @param studentId - The ID of the student to remove
 */
export const removeStudent = async (studentId: string): Promise<void> => {
    try {
        await deleteStudent(studentId);
    } catch (error) {
        console.error(`Error deleting student ${studentId}:`, error);
        throw error;
    }
};

/**
 * Searches for students by keyword with pagination
 */
export const searchStudents = async (
  keyword: string,
  page: number = 0,
  pageSize: number = 10
): Promise<PaginatedResponse<Student>> => {
  try {
    const response = await apiSearchStudents(keyword, page, pageSize);
    const students = (response.content || []).map(transformGetResponseToStudent);
    
    return {
      data: students,
      pagination: {
        total: response.totalElements || 0,
        page: response.number || page,
        pageSize: response.size || pageSize,
        totalPages: response.totalPages || 0
      }
    };
  } catch (error) {
    console.error('Error searching students:', error);
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