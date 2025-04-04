import { getStudents, postStudent, patchStudent, deleteStudent } from "@/libs/api/studentApi";
import { Student } from "../../interfaces/Student";
import { cleanData } from "../utils/cleanData";
import { transformStudentToPostRequest, transformStudentToPatchRequest, transformGetResponseToStudent } from "../utils/studentTransform";
import { StudentResponse } from '../../interfaces/StudentResponse';

export const fetchStudents = async () => {
    try {
        const students = await getStudents();
        return students.map(transformGetResponseToStudent);
    } catch (error) {
        console.error("Error fetching students:", error);
        throw error;
    }
};

export const createStudent = async (values: Student) => {
    const requestData = cleanData(transformStudentToPostRequest(values)) as Partial<StudentResponse>;
    try {
        const { data } = await postStudent(requestData);
        return data;
    } catch (error) {
        console.error("Error creating student:", error);
        throw error;
    }
};

export const updateStudent = async (values: Student) => {
    const requestData = await cleanData(transformStudentToPatchRequest(values));
    try {
        const { data } = await patchStudent(values.studentId, requestData);
        return data;
    } catch (error) {
        console.error("Error updating student:", error);
        throw error;
    }
};

export const removeStudent = async (studentId: string) => {
    try {
        return await deleteStudent(studentId);
    } catch (error) {
        console.error("Error deleting student:", error);
        throw error;
    }
};
