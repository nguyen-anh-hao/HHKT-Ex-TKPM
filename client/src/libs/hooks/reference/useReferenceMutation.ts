import { useMutation } from '@tanstack/react-query';
import * as referenceService from '@/libs/services/referenceService';
import { message } from 'antd';

export const useCreateReference = () => {
    const { mutate, error, isPending } = useMutation({
        mutationKey: ['createReference'],
        mutationFn: referenceService.createReference,
    });
    return { mutate, error, isPending };
}

export const useUpdateReference = () => {
    const { mutate, error, isPending } = useMutation({
        mutationKey: ['updateReference'],
        mutationFn: referenceService.updateReference,
    });
    return { mutate, error, isPending };
}

export const useDeleteReference = () => {
    const { mutate, error, isPending } = useMutation({
        mutationKey: ['deleteReference'],
        mutationFn: referenceService.removeReference,
    });
    return { mutate, error, isPending };
}