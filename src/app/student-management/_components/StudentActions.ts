// components/StudentActions.ts
import { studentSchema } from "./StudentSchema";
import { message } from "antd";

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

export const addStudent = (students: any[], newStudent: any) => {
    if (students.some(student => student.mssv === newStudent.mssv)) {
        message.error("MSSV này đã tồn tại!");
        return students;
    }
    message.success("Thêm sinh viên thành công!");
    return [...students, newStudent];
};

export const updateStudent = (students: any[], updatedStudent: any) => {
    message.success("Cập nhật thông tin sinh viên!");
    return students.map(student => student.mssv === updatedStudent.mssv ? updatedStudent : student);
};

export const deleteStudent = (students: any[], mssv: string) => {
    message.success("Xóa sinh viên thành công!");
    return students.filter(student => student.mssv !== mssv);
};
