import axios from "axios";

const BASE_URL = "http://localhost:9000/api";

export const getReference = async (type: string) => {
    try {
        const response = await axios.get(`${BASE_URL}/${type}`);
        return response.data;
    } catch (error) {
        console.error(`Error fetching ${type}:`, error);
        throw error;
    }
};

export const postReference = async (type: string, values: any) => {
    let payload: any = {};
    if (type === "faculties") {
        payload = { facultyName: values.facultyName };
    } else if (type === "programs") {
        payload = { programName: values.programName };
    } else if (type === "student-statuses") {
        payload = { studentStatusName: values.studentStatusName };
    }

    const response = await axios.post(`${BASE_URL}/${type}`, payload);
    return response.data;
};

export const putReference = async (type: string, id: string, values: any) => {
    const response = await axios.put(`${BASE_URL}/${type}/${id}`, values);
    return response.data;
};

export const deleteReference = async (type: string, id: string) => {
    const response = await axios.delete(`${BASE_URL}/${type}/${id}`);
    return response.data;
};