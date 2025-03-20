// app/dataValidation.ts

import { z } from 'zod';

// Kiểm tra email hợp lệ    
const emailSchema = z.string().email("Email không hợp lệ");

// Kiểm tra số điện thoại hợp lệ (chỉ ví dụ: số điện thoại Việt Nam)
const phoneSchema = z.string().regex(/^(\+84|0)[0-9]{9,10}$/, "Số điện thoại không hợp lệ");

// Kiểm tra các khoa hợp lệ
const facultySchema = z.enum(["Khoa Luật", "Khoa Tiếng Anh thương mại", "Khoa Tiếng Nhật", "Khoa Tiếng Pháp"]);

// Kiểm tra các tình trạng sinh viên hợp lệ
const studentStatusSchema = z.enum(["Đang học", "Đã tốt nghiệp", "Đã thôi học", "Tạm dừng học"]);

// Kiểm tra thông tin sinh viên
export const studentSchema = z.object({
    // mssv: z.string().min(5, "Mã số sinh viên phải có ít nhất 5 ký tự"),
    fullName: z.string().min(3, "Họ tên phải có ít nhất 3 ký tự"),
    // dob: z.string().min(10, "Ngày tháng năm sinh không hợp lệ"),
    gender: z.enum(["Nam", "Nữ"]),
    faculty: facultySchema,
    // class: z.string(),
    // program: z.string(),
    // address: z.string(),
    email: emailSchema,
    phone: phoneSchema,
    status: studentStatusSchema,
});
