import axios from "axios";

const BASE_URL = "http://localhost:9000/api";

export const fetchReferenceData = async (type: string) => {
    const { data } = await axios.post(`${BASE_URL}/${type}`);
    return data;
};

// GET(type), PUT(type, id), DELETE(type, id)