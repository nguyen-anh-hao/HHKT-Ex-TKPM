import { getStudents, postStudent, patchStudent, deleteStudent } from '@/libs/api/studentApi';
import { Student } from '../../interfaces/Student';
import { cleanData } from '../utils/cleanData';
import { transformStudentToPostRequest, transformStudentToPatchRequest, transformGetResponseToStudent } from '../utils/studentTransform';
import { StudentResponse } from '../../interfaces/StudentResponse';

export const fetchStudents = async () => {
    try {
        const students = await getStudents();
        return students.map(transformGetResponseToStudent);
    } catch (error) {
        throw error;
    }
};

export const createStudent = async (value: Student) => {
    const requestData = cleanData(transformStudentToPostRequest(value)) as Partial<StudentResponse>;
    try {
        const { data } = await postStudent(requestData);
        return data;
    } catch (error) {
        throw error;
    }
};

export const updateStudent = async (value: Student) => {
    const requestData = await cleanData(transformStudentToPatchRequest(value));
    try {
        const { data } = await patchStudent(value.studentId, requestData);
        return data;
    } catch (error) {
        throw error;
    }
};

export const removeStudent = async (studentId: string) => {
    try {
        return await deleteStudent(studentId);
    } catch (error) {
        throw error;
    }
};
