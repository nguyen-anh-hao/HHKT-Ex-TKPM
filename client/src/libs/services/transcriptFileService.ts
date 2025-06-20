import { getTranscript } from "../api/transcriptFileApi";

export const downloadTranscript = async (studentId: string) => {
    try {
        const response = await getTranscript(studentId);
        return response;
    } catch (error) {
        throw error;
    }
};