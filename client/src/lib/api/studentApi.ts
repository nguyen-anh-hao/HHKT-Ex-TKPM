import axios from "axios";
import { Student } from "../../interfaces/student.interface";
import { cleanData } from "../utils/cleanData";
import { convertStudentToPostRequest, convertStudentToPutRequest } from "../utils/studentConverter";

const BASE_URL = "http://localhost:9000/api";

export const fetchStudents = async () => {
    const { data } = await axios.get(`${BASE_URL}/students?page=0&size=50`);
    return data;
};

export const createStudent = async (values: Student) => {
    const requestData = cleanData(convertStudentToPostRequest(values));
    console.log("request data", requestData);
    const { data } = await axios.post(`${BASE_URL}/students`, requestData);
    return data;
};

export const updateStudent = async (values: Student) => {
    const requestData = cleanData(convertStudentToPutRequest(values));
    const { data } = await axios.put(`${BASE_URL}/students/${values.studentId}`, requestData);
    return data;
};

export const deleteStudent = async (studentId: string) => {
    await axios.delete(`${BASE_URL}/students/${studentId}`);
};
