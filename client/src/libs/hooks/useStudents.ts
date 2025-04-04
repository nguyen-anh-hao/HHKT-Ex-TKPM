import { useQuery } from "@tanstack/react-query";
import * as studentApi from "@/libs/services/studentService";

export const useStudents = () => {
    const { data, error, isLoading } = useQuery({
        queryKey: ["students"],
        queryFn: studentApi.fetchStudents,
    });
    
    return { data, error, isLoading };
};
