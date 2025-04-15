import api from "./api";

export const getStatusRules = async () => {
    try {
        const response = await api.get("/student-status-rules?page=0&size=1000");
        return response.data;
    } catch (error) {
        throw error;
    }
}

export const postStatusRule = async (currentStatusId: number, allowedTransitionId: number) => {
    try {
        const value = {
            currentStatusId,
            allowedTransitionId
        };
        const response = await api.post("/student-status-rules", value);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const putStatusRule = async (id: number, currentStatusId: number, allowedTransitionId : number) => {
    try {
        const value = {
            currentStatusId,
            allowedTransitionId
        };
        const response = await api.put(`/student-status-rules/${id}`, value);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const deleteStatusRule = async (id: number) => {
    try {
        const response = await api.delete(`/student-status-rules/${id}`);
        return response.data;
    } catch (error) {
        throw error;
    }
};