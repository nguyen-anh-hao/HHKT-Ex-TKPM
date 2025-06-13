import { Class } from '@/interfaces/class/Class';
import { ClassResponse } from '@/interfaces/class/ClassResponse';
import { getClasses, postClass } from '@/libs/api/classApi';
import { cleanData } from '@/libs/utils/cleanData';

export const fetchClasses = async (): Promise<ClassResponse[]> => {
    try {
        return await getClasses();
    } catch (error) {
        throw error;
    }
};

export const createClass = async (value: Class) => {
    const requestData = cleanData(value);
    try {
        const data = await postClass(requestData);
        return data;
    } catch (error) {
        throw error;
    }
};


