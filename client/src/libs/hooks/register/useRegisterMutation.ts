import { useMutation, useQuery } from '@tanstack/react-query';
import * as registerService from '@/libs/services/registerService';
import { RegisterResponse } from '@/interfaces/register/RegisterResponse';
import { RegisterRequest } from '@/interfaces/register/CreateRegisterRequest';


export const useCreateRegister = () => {
    const { mutate, error, isPending, isSuccess } = useMutation<RegisterResponse, Error, RegisterRequest>({
        mutationFn: registerService.createRegistration,
    });
    return { mutate, error, isPending, isSuccess };
};

export const useUpdateRegister = () => {
    const { mutate, error, isPending, isSuccess } = useMutation<RegisterResponse, Error, { id: number; value: Partial<RegisterRequest> }>({
        mutationFn: ({ id, value }) => registerService.updateRegistration(id, value),
    });
    return { mutate, error, isPending, isSuccess };
};


export const useFetchRegistrations = () => {
    const { data, error, isLoading } = useQuery<RegisterResponse[], Error>({
        queryKey: ['registrations'],
        queryFn: registerService.fetchRegistrations,
    });
    return { data, error, isLoading };
};


export const useFetchRegistrationById = (id: number) => {
    const { data, error, isLoading } = useQuery<RegisterResponse, Error>({
        queryKey: ['registration', id],
        queryFn: () => registerService.fetchRegistrationById(id),
    });
    return { data, error, isLoading };
};
