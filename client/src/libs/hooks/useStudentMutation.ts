
import { useMutation } from "@tanstack/react-query";
import * as studentApi from "@/libs/services/studentService";

export const useCreateStudent = () => {
    const { mutate, error, isPending } = useMutation({
        mutationFn: studentApi.createStudent,
    });
    return { mutate, error, isPending };
}

export const useUpdateStudent = () => {
    const { mutate, error, isPending } = useMutation({
        mutationFn: studentApi.updateStudent,
    });
    return { mutate, error, isPending };
}

export const useDeleteStudent = () => {
    const { mutate, error, isPending } = useMutation({
        mutationFn: studentApi.removeStudent,
    });
    return { mutate, error, isPending };
}