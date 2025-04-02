import axios from "axios";

const BASE_URL = "http://localhost:9000/api";

export const fetchReferenceData = async (type: string) => {
    try {
        const { data } = await axios.get(`${BASE_URL}/${type}`);
        return data.data; // Ensure the API response contains a `data` field
    } catch (error) {
        console.error(`Error fetching ${type}:`, error);
        throw error; // Re-throw the error to handle it in the calling function
    }
};

export const createReferenceData = async (type: string, values: any) => {
    let payload: any = {};
    if (type === "faculties") {
        payload = { facultyName: values.facultyName };
    } else if (type === "programs") {
        payload = { programName: values.programName };
    } else if (type === "student-statuses") {
        payload = { studentStatusName: values.studentStatusName };
    }

    const { data } = await axios.post(`${BASE_URL}/${type}`, payload);
    return data;
};

export const updateReferenceData = async (type: string, id: string, values: any) => {
    const { data } = await axios.put(`${BASE_URL}/${type}/${id}`, values);
    return data;
};

export const deleteReferenceData = async (type: string, id: string) => {
    await axios.delete(`${BASE_URL}/${type}/${id}`);
};

// GET(type), PUT(type, id), DELETE(type, id)