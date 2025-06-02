import { useMutation } from '@tanstack/react-query';
import { createStatusRule, updateStatusRule, removeStatusRule } from '@/libs/services/statusRuleService';

export const useCreateStatusRule = () => {
    const { mutate, error, isPending } = useMutation({
        mutationKey: ['createStatusRule'],
        mutationFn: ({ currentStatusId, allowedTransitionId } : { currentStatusId: number, allowedTransitionId : number}) => createStatusRule(currentStatusId, allowedTransitionId),
    });
    return { mutate, error, isPending };
}

export const useUpdateStatusRule = () => {
    const { mutate, error, isPending } = useMutation({
        mutationKey: ['updateStatusRule'],
        mutationFn: ({ id, currentStatusId, allowedTransitionId } : { id: number, currentStatusId: number, allowedTransitionId : number }) => updateStatusRule(id, currentStatusId, allowedTransitionId),
    });
    return { mutate, error, isPending };
}

export const useDeleteStatusRule = () => {
    const { mutate, error, isPending } = useMutation({
        mutationKey: ['deleteStatusRule'],
        mutationFn: removeStatusRule,
    });
    return { mutate, error, isPending };
}