import { studentSchema } from '../../../libs/validators/studentValidator';
import { message } from 'antd';
import { Student } from '../../../interfaces/Student';

export const addStudent = (students: Student[], newStudent: Student) => {
    if (students.some(student => student.studentId === newStudent.studentId)) {
        message.error('MSSV này đã tồn tại!');
        return students;
    }
    return [...students, newStudent];
};

export const updateStudent = (students: Student[], updatedStudent: Student) => {
    return students.map(student => student.studentId === updatedStudent.studentId ? updatedStudent : student);
};

export const deleteStudent = (students: Student[], mssv: string) => {
    return students.filter(student => student.studentId !== mssv);
};
