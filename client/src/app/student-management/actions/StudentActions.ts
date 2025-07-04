import { message } from 'antd';
import { Student } from '../../../interfaces/student/Student';

export const addStudent = (students: Student[], newStudent: Student) => {
    return [...students, newStudent];
};

export const updateStudent = (students: Student[], updatedStudent: Student) => {
    return students.map(student => student.studentId === updatedStudent.studentId ? updatedStudent : student);
};

export const deleteStudent = (students: Student[], mssv: string) => {
    return students.filter(student => student.studentId !== mssv);
};
