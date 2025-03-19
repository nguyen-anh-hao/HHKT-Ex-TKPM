// components/StudentSchema.ts
import { z } from "zod";

export const studentSchema = z.object({
    mssv: z.string().min(1, { message: "Mã số sinh viên không được để trống!" }),
    fullName: z.string().min(1, { message: "Họ tên không được để trống!" }),
    dob: z.string().min(1, { message: "Ngày sinh không được để trống!" }),
    gender: z.string().min(1, { message: "Giới tính không được để trống!" }),
    faculty: z.string().min(1, { message: "Khoa không được để trống!" }),
    course: z.string().min(1, { message: "Khóa không được để trống!" }),
    program: z.string().min(1, { message: "Chương trình không được để trống!" }),
    permanentAddress: z.string().optional(),
    temporaryAddress: z.string().optional(),
    mailAddress: z.string().optional(),
    idCard: z.object({
        type: z.string(),
        number: z.string(),
        issuedDate: z.string(),
        expiredDate: z.string(),
        issuedBy: z.string(),
    }),
    nationality: z.string().min(1, { message: "Quốc tịch không được để trống!" }),
    email: z.string().email({ message: "Email không hợp lệ!" }),
    phone: z.string().min(1, { message: "Số điện thoại không được để trống!" }),
    status: z.string().min(1, { message: "Tình trạng không được để trống!" }),
});
