import { useMutation } from '@tanstack/react-query';
import * as classService from '@/libs/services/classService'; 

export const useCreateClass = () => { 
    const { mutate, error, isPending } = useMutation({
        mutationFn: classService.createClass, 
    });
    return { mutate, error, isPending };
};

