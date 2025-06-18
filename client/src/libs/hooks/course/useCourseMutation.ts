import { useMutation } from '@tanstack/react-query';
import * as courseService from '@/libs/services/courseService';

export const useCreateCourse = () => {
    const { mutate, error, isPending } = useMutation({
        mutationFn: courseService.createCourse,
    });
    return { mutate, error, isPending };
};

export const useUpdateCourse = () => {
    const { mutate, error, isPending } = useMutation({
        mutationFn: courseService.updateCourse,
    });
    return { mutate, error, isPending };
};

export const useDeleteCourse = () => {
    const { mutate, error, isPending } = useMutation({
        mutationFn: courseService.removeCourse,
    });
    return { mutate, error, isPending };
};
