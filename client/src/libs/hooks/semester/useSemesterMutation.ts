import { useMutation, useQueryClient, UseMutationOptions } from '@tanstack/react-query';
import { createSemester, updateSemester, removeSemester } from '@/libs/services/semesterService';
import { Semester } from '@/interfaces/semester/Semester';
import { CreateSemesterRequest } from '@/interfaces/semester/CreateSemesterRequest';
import { UpdateSemesterRequest } from '@/interfaces/semester/UpdateSemesterRequest';
import { SEMESTERS_QUERY_KEY, SEMESTER_DETAIL_QUERY_KEY } from './useSemesters';

type SemesterError = Error & {
  response?: {
    data?: {
      message?: string;
      errors?: Array<{ defaultMessage: string }>;
    }
  }
};

/**
 * Hook for creating a new semester
 */
export const useCreateSemester = (
  options?: UseMutationOptions<Semester, SemesterError, CreateSemesterRequest>
) => {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: createSemester,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [SEMESTERS_QUERY_KEY] });
    },
    ...options,
  });
};

/**
 * Hook for updating an existing semester
 */
export const useUpdateSemester = (
  options?: UseMutationOptions<Semester, SemesterError, { id: number, data: Partial<UpdateSemesterRequest> }>
) => {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: ({ id, data }) => updateSemester(id, data),
    onSuccess: (_, variables) => {
      queryClient.invalidateQueries({ queryKey: [SEMESTERS_QUERY_KEY] });
      queryClient.invalidateQueries({ queryKey: [SEMESTER_DETAIL_QUERY_KEY, variables.id] });
    },
    ...options,
  });
};

/**
 * Hook for deleting a semester
 */
export const useDeleteSemester = (
  options?: UseMutationOptions<void, SemesterError, number>
) => {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: removeSemester,
    onSuccess: (_, semesterId) => {
      queryClient.invalidateQueries({ queryKey: [SEMESTERS_QUERY_KEY] });
      queryClient.removeQueries({ queryKey: [SEMESTER_DETAIL_QUERY_KEY, semesterId] });
    },
    ...options,
  });
};

/**
 * Combined hooks for semester mutations
 */
export const useSemesterMutations = () => {
  const { mutate: addSemester, isPending: isCreating } = useCreateSemester();
  const { mutate: editSemester, isPending: isUpdating } = useUpdateSemester();
  const { mutate: deleteSemester, isPending: isDeleting } = useDeleteSemester();
  
  const isLoading = isCreating || isUpdating || isDeleting;
  
  return {
    addSemester,
    editSemester: (id: number, data: Partial<UpdateSemesterRequest>) => editSemester({ id, data }),
    deleteSemester,
    isLoading
  };
};
