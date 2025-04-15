import { getStatusRules, postStatusRule, putStatusRule, deleteStatusRule } from "../api/statusRuleApi";

export const fetchStatusRules = async () => {
    try {
        const response = await getStatusRules();
        return await response.data;
    } catch (error) {
        throw error;
    }
}

export const createStatusRule = async (currentStatusId: number, allowedTransitionId: number) => {
    try {
        return await postStatusRule(currentStatusId, allowedTransitionId);
    } catch (error) {
        throw error;
    }
}

export const updateStatusRule = async (id: number, currentStatusId: number, allowedTransitionId: number) => {
    try {
        return await putStatusRule(id, currentStatusId, allowedTransitionId);
    } catch (error) {
        throw error;
    }
}

export const removeStatusRule = async (id: number) => {
    try {
        return await deleteStatusRule(id);
    } catch (error) {
        throw error;
    }
}