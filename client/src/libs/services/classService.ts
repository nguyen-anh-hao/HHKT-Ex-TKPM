import { Class } from '@/interfaces/ClassResponse';
import {
    getClasses,
    postClass,

} from '@/libs/api/classApi';
import { cleanData } from '@/libs/utils/cleanData';

export const fetchClasses = async (): Promise<Class[]> => {
    try {
        return await getClasses(); // Không cần map transform nữa
    } catch (error) {
        throw error;
    }
};

export const createClass = async (value: Class) => {
    const requestData = cleanData(value); // Dùng object Class trực tiếp
    try {
        const data = await postClass(requestData);
        return data;
    } catch (error) {
        throw error;
    }
};


