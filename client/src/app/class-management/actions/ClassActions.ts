import { message } from 'antd';
import { Class } from '../../../interfaces/class/Class';
import { useTranslations } from 'next-intl';

// Create a function that takes the translations function as a parameter
export const createClassActions = (t: ReturnType<typeof useTranslations>) => {
    const addClass = (classes: Class[], newClass: Class): Class[] => {
        if (classes.some(cls => cls.classCode === newClass.classCode)) {
            message.error(t('duplicate-class'));
            return classes;
        }

        return [...classes, newClass];
    };

    const updateClass = (classes: Class[], updatedClass: Class): Class[] => {
        return classes.map(cls =>
            cls.id === updatedClass.id ? { ...cls, ...updatedClass } : cls
        );
    };

    const deleteClass = (classes: Class[], classId: number): Class[] => {
        return classes.filter(cls => cls.id !== classId);
    };

    return { addClass, updateClass, deleteClass };
};

// Keep these for backward compatibility
export const addClass = (classes: Class[], newClass: Class): Class[] => {
    if (classes.some(cls => cls.classCode === newClass.classCode)) {
        message.error('Class already exists!');
        return classes;
    }

    return [...classes, newClass];
};

export const updateClass = (classes: Class[], updatedClass: Class): Class[] => {
    return classes.map(cls =>
        cls.id === updatedClass.id ? { ...cls, ...updatedClass } : cls
    );
};

export const deleteClass = (classes: Class[], classId: number): Class[] => {
    return classes.filter(cls => cls.id !== classId);
};
