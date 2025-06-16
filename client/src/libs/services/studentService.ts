import { getStudentById, getStudents, postStudent, patchStudent, deleteStudent } from '@/libs/api/studentApi';
import { searchStudents as apiSearchStudents } from '@/libs/api/studentApi';
import { Student } from '@/interfaces/student/Student';
import { cleanData } from '../utils/cleanData';
import { transformStudentToPostRequest, transformStudentToPatchRequest, transformGetResponseToStudent } from '../utils/transform/studentTransform';
import { StudentResponse } from '@/interfaces/student/StudentResponse';
import { handleApiError } from '../utils/errorUtils';

/**
 * Fetches a student by ID
 * @param studentId - The ID of the student to fetch
 */
export const fetchStudentById = async (studentId: string): Promise<Student> => {
    try {
        const response = await getStudentById(studentId);
        return transformGetResponseToStudent(response);
    } catch (error) {
        return handleApiError(error, `fetchStudentById(${studentId})`);
    }
};

/**
 * Fetches all students
 */
export const fetchStudents = async (): Promise<Student[]> => {
    try {
        const students = await getStudents();
        return students.map(transformGetResponseToStudent);
    } catch (error) {
        console.error('Error fetching students:', error);
        throw error;
    }
};

/**
 * Creates a new student
 * @param student - The student data to create
 */
export const createStudent = async (student: Student): Promise<StudentResponse> => {
    try {
        const requestData = cleanData(transformStudentToPostRequest(student));
        const { data } = await postStudent(requestData);
        if (!data) {
            throw new Error('Failed to create student: response data is null');
        }
        return data;
    } catch (error) {
        console.error('Error creating student:', error);
        throw error;
    }
};

/**
 * Updates an existing student
 * @param student - The student data to update
 */
export const updateStudent = async (student: Student): Promise<StudentResponse> => {
    try {
        const requestData = cleanData(transformStudentToPatchRequest(student));
        console.log('Request data for updating student:', requestData);
        const { data } = await patchStudent(student.studentId, requestData);
        if (!data) {
            throw new Error('Failed to update student: response data is null');
        }
        return data;
    } catch (error) {
        console.error(`Error updating student ${student.studentId}:`, error);
        throw error;
    }
};

/**
 * Removes a student
 * @param studentId - The ID of the student to remove
 */
export const removeStudent = async (studentId: string): Promise<void> => {
    try {
        await deleteStudent(studentId);
    } catch (error) {
        console.error(`Error deleting student ${studentId}:`, error);
        throw error;
    }
};

/**
 * Searches for students by keyword
 * @param keyword - The keyword to search for
 * @returns A promise that resolves to an array of students matching the keyword
 */
export const searchStudents = async (keyword: string): Promise<Student[]> => {
    try {
        // Option 1: Use the API's search function (uncomment to use)
        const students = await apiSearchStudents(keyword);
        return students.map(transformGetResponseToStudent);
        
        // Option 2: Filter locally (current implementation)
        // const students = await getStudents(0, 50); // Adjust page and size as needed
        // return students
        //     .map(transformGetResponseToStudent)
        //     .filter(student => student.fullName.toLowerCase().includes(keyword.toLowerCase()));
    } catch (error) {
        console.error('Error searching students:', error);
        throw error;
    }
}