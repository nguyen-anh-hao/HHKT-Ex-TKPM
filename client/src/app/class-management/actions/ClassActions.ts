import { message } from 'antd';
import { Class } from '../../../interfaces/ClassResponse';

export const addClass = (classes: Class[], newClass: Class): Class[] => {
    if (classes.some(cls => cls.classCode === newClass.classCode)) {
        message.error('Lớp học đã tồn tại!');
        return classes;
    }

    return [...classes, newClass];
};

export const updateClass = (classes: Class[], updatedClass: Class): Class[] => {
    return classes.map(cls =>
        cls.classId === updatedClass.classId ? { ...cls, ...updatedClass } : cls
    );
};

export const deleteClass = (classes: Class[], classId: number): Class[] => {
    return classes.filter(cls => cls.classId !== classId);
};
