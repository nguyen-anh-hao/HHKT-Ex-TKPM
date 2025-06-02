import api from './api';

const BASE_URL = 'http://localhost:9000/api';

export const getTranscript = async (studentId: string) => {
    try {
        return `${BASE_URL}/students/${studentId}/transcript`;
    } catch (error) {
        throw error;
    }
}