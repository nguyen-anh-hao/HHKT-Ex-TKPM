import { z } from 'zod';
import { Student } from '@/interfaces/student/Student'

export const createStudentSchema = (students: Student[]) => {
    return z.object({
        studentId: z.string().nonempty().refine((id) => {
            return !students.some(student => student.studentId === id);
        }, {
            message: 'Mã số sinh viên đã tồn tại',
        }),
        email: z.string().email().refine((email) => {
            return !students.some(student => student.email === email);
        }, {
            message: 'Email đã tồn tại',
        }),
        phone: z.string().nonempty().refine((phone) => {
            return !students.some(student => student.phone === phone);
        }, {
            message: 'Số điện thoại đã tồn tại',
        }),
    })
};