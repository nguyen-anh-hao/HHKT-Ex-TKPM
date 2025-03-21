import axios from "axios";
import { Student } from "../../interfaces/student/state.interface";
import { convertStudentToPostRequest } from "../utils/studentConverter";

const BASE_URL = "http://localhost:9000/api/sinh-vien";

export const fetchStudents = async () => {
    const { data } = await axios.get(`${BASE_URL}?page=0&size=50`);
    return data;
};

export const createStudent = async (values: Student) => {
    const requestData = convertStudentToPostRequest(values);
    const { data } = await axios.post(BASE_URL, requestData);
    return data;
};

export const updateStudent = async (values: Student) => {
    await axios.put(`${BASE_URL}/${values.studentId}`, values);
};

export const deleteStudent = async (studentId: string) => {
    await axios.delete(`${BASE_URL}/${studentId}`);
};
