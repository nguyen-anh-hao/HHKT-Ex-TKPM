import { useMutation, useQueryClient, UseMutationOptions } from '@tanstack/react-query';
import * as studentService from '@/libs/services/studentService';
import { Student } from '@/interfaces/student/Student';
import { STUDENTS_QUERY_KEY, STUDENT_DETAIL_QUERY_KEY } from './useStudents';
import { StudentResponse } from '@/interfaces/student/StudentResponse';

type StudentError = Error & {
  response?: {
    data?: {
      message?: string;
      errors?: Array<{ defaultMessage: string }>;
    }
  }
};

/**
 * Hook for creating a new student
 * @param options - Additional mutation options
 */
export const useCreateStudent = (
  options?: UseMutationOptions<StudentResponse, StudentError, Student>
) => {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: studentService.createStudent,
    onSuccess: () => {
      // Invalidate the students list query to trigger a refetch
      queryClient.invalidateQueries({ queryKey: [STUDENTS_QUERY_KEY] });
    },
    ...options,
  });
};

/**
 * Hook for updating an existing student
 * @param options - Additional mutation options
 */
export const useUpdateStudent = (
  options?: UseMutationOptions<StudentResponse, StudentError, Student>
) => {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: studentService.updateStudent,
    onSuccess: (_, variables) => {
      // Invalidate both the list and the specific student detail
      queryClient.invalidateQueries({ queryKey: [STUDENTS_QUERY_KEY] });
      queryClient.invalidateQueries({ 
        queryKey: [STUDENT_DETAIL_QUERY_KEY, variables.studentId] 
      });
    },
    ...options,
  });
};

/**
 * Hook for deleting a student
 * @param options - Additional mutation options
 */
export const useDeleteStudent = (
  options?: UseMutationOptions<void, StudentError, string>
) => {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: studentService.removeStudent,
    onSuccess: (_, studentId) => {
      // Invalidate the students list
      queryClient.invalidateQueries({ queryKey: [STUDENTS_QUERY_KEY] });
      
      // Remove the specific student from the cache
      queryClient.removeQueries({ 
        queryKey: [STUDENT_DETAIL_QUERY_KEY, studentId] 
      });
    },
    ...options,
  });
};

/**
 * Formats error message from API response
 * @param error - Error object from API call
 * @returns Formatted error message string
 */
export const formatApiError = (error: StudentError): string => {
  if (!error.response?.data) {
    return error.message;
  }
  
  const { data } = error.response;
  
  if (data.errors && Array.isArray(data.errors)) {
    return data.errors.map(err => err.defaultMessage).join(', ');
  }
  
  return data.message || 'Unknown error occurred';
};