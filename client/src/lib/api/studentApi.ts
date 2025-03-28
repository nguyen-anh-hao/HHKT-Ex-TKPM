import axios from "axios";
import { Student } from "../../interfaces/student.interface";
import { cleanData } from "../utils/cleanData";
import { convertStudentToPostRequest, convertStudentToPutRequest } from "../utils/studentConverter";

const BASE_URL = "http://localhost:9000/api";

export const fetchStudents = async () => {
    try {
        const { data } = await axios.get(`${BASE_URL}/students?page=0&size=50`);
        return data;
    } catch (error) {
        console.error("Error fetching students:", error);
        throw error;
    }
};

export const createStudent = async (values: Student) => {
    const requestData = cleanData(convertStudentToPostRequest(values));
    console.log("request data", requestData);
    try {
        const { data } = await axios.post(`${BASE_URL}/students`, requestData);
        return data;
    } catch (error) {
        console.error("Error creating student:", error);
        throw error;
    }
};

export const updateStudent = async (values: Student) => {
    const requestData = cleanData(convertStudentToPutRequest(values));
    try {
        const { data } = await axios.patch(`${BASE_URL}/students/${values.studentId}`, requestData);
        return data;
    } catch (error) {
        console.error("Error updating student:", error);
        throw error;
    }
};

export const deleteStudent = async (studentId: string) => {
    try {
        await axios.delete(`${BASE_URL}/students/${studentId}`);
    } catch (error) {
        console.error("Error deleting student:", error);
        throw error;
    }
};
