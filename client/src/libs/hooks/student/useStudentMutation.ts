import { useMutation } from '@tanstack/react-query';
import * as studentService from '@/libs/services/studentService';

export const useCreateStudent = () => {
    const { mutate, error, isPending } = useMutation({
        mutationFn: studentService.createStudent,
    });
    return { mutate, error, isPending };
}

export const useUpdateStudent = () => {
    const { mutate, error, isPending } = useMutation({
        mutationFn: studentService.updateStudent,
    });
    return { mutate, error, isPending };
}

export const useDeleteStudent = () => {
    const { mutate, error, isPending } = useMutation({
        mutationFn: studentService.removeStudent,
    });
    return { mutate, error, isPending };
}