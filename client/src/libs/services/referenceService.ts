import { getReference, postReference, putReference, deleteReference } from '../api/referenceApi';

type ReferenceType = 'faculties' | 'programs' | 'student-statuses' | 'email-domains' | 'lecturers' | 'courses'; 


export const fetchReference = async (type: ReferenceType) => {
    try {
         const data = await getReference(`${type}?size=1000`);
        return data;
    } catch (error) {
        throw error;
    }
};


export const createReference = async ({ type, value }: { type: ReferenceType, value: string }) => {
    try {
        const { data } = await postReference(type, value);
        return data;
    } catch (error) {
        throw error;
    }
};


export const updateReference = async ({ type, value, id }: { type: ReferenceType, value: string, id: number }) => {
    try {
        const { data } = await putReference(type, id, value);
        return data;
    } catch (error) {
        throw error;
    }
};


export const removeReference = async ({ type, id }: { type: ReferenceType, id: number }) => {
    try {
        const { data } = await deleteReference(type, id);
        return data;
    } catch (error) {
        throw error;
    }
};
