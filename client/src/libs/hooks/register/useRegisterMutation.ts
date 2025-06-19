import { useMutation, useQueryClient } from '@tanstack/react-query';
import { postRegister, patchRegister, deleteRegister } from '@/libs/api/registerApi';
import { RegisterResponse } from '@/interfaces/register/RegisterResponse';
import { REGISTRATIONS_QUERY_KEY } from './useRegisterQuery';
import { useRegistrations } from './useRegisterQuery';

/**
 * Hook for creating a new registration
 */
export const useCreateRegister = () => {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: (registerData: RegisterResponse) => postRegister(registerData),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [REGISTRATIONS_QUERY_KEY] });
    },
  });
};

/**
 * Hook for updating an existing registration
 */
export const useUpdateRegister = () => {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: ({ id, value }: { id: number; value: RegisterResponse }) => patchRegister(id, value),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [REGISTRATIONS_QUERY_KEY] });
    },
  });
};

/**
 * Hook for deleting a registration
 */
export const useDeleteRegister = () => {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: (registerId: number) => deleteRegister(registerId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [REGISTRATIONS_QUERY_KEY] });
    },
  });
};

// Alias for backward compatibility with existing code
export const useFetchRegistrations = useRegistrations;
