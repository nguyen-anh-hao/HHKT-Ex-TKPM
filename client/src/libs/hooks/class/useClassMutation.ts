import { useMutation, useQueryClient } from '@tanstack/react-query';
import { postClass, patchClass, deleteClass } from '@/libs/api/classApi';
import { Class } from '@/interfaces/class/Class';
import { CLASSES_QUERY_KEY } from './useClassQuery';

/**
 * Hook for creating a new class
 */
export const useCreateClass = () => {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: (classData: Class) => postClass(classData),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [CLASSES_QUERY_KEY] });
    },
  });
};

/**
 * Hook for updating an existing class
 */
export const useUpdateClass = () => {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: (classData: Class) => patchClass(classData.id, classData),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [CLASSES_QUERY_KEY] });
    },
  });
};

/**
 * Hook for deleting a class
 */
export const useDeleteClass = () => {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: (classId: number) => deleteClass(classId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [CLASSES_QUERY_KEY] });
    },
  });
};

