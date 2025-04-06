// components/StudentActions.ts
import { studentSchema } from "../../../libs/validators/studentValidator";
import { message } from "antd";
import { Student } from "../../../interfaces/Student";

export const validateStudent = (values: any) => {
    try {
        return studentSchema.parse(values);
    } catch (error) {
        if (error instanceof Error) {
            message.error(error.message);
        }
        return null;
    }
};

export const addStudent = (students: Student[], newStudent: Student) => {
    if (students.some(student => student.studentId === newStudent.studentId)) {
        message.error("MSSV này đã tồn tại!");
        return students;
    }
    message.success("Thêm sinh viên thành công!");
    return [...students, newStudent];
};

export const updateStudent = (students: Student[], updatedStudent: Student) => {
    message.success("Cập nhật thông tin sinh viên!");
    return students.map(student => student.studentId === updatedStudent.studentId ? updatedStudent : student);
};

export const deleteStudent = (students: Student[], mssv: string) => {
    message.success("Xóa sinh viên thành công!");
    return students.filter(student => student.studentId !== mssv);
};
