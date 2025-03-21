import moment from "moment";
import { Student, StudentGetResponse, StudentPostRequest } from "@/interfaces/student";

const convertGetResponseToStudent = (response: StudentGetResponse): Student => {
    return {
        studentId: response.mssv,
        fullName: response.hoTen,
        dob: moment(response.ngaySinh).format("YYYY-MM-DD"),
        gender: response.gioiTinh,
        faculty: response.khoa,
        intake: response.khoaHoc,
        program: response.chuongTrinh,
        status: response.tinhTrang,
        nationality: response.quocTich,
        email: response.email,
        phone: response.soDienThoai,
        permanentAddress: response.diaChis.length > 0
            ? `${response.diaChis[0].soNhaTenDuong}, ${response.diaChis[0].phuongXa}, ${response.diaChis[0].quanHuyen}, ${response.diaChis[0].tinhThanhPho}, ${response.diaChis[0].quocGia}`
            : "",
        temporaryAddress: response.diaChis.length > 1
            ? `${response.diaChis[1].soNhaTenDuong}, ${response.diaChis[1].phuongXa}, ${response.diaChis[1].quanHuyen}, ${response.diaChis[1].tinhThanhPho}, ${response.diaChis[1].quocGia}`
            : "",
        documentType: response.giayTos.length > 0 ? response.giayTos[0].loaiGiayTo : "",
        documentNumber: response.giayTos.length > 0 ? response.giayTos[0].soGiayTo : "",
        issuedDate: response.giayTos.length > 0 ? moment(response.giayTos[0].ngayCap).format("YYYY-MM-DD") : "",
        expiredDate: response.giayTos.length > 0 ? moment(response.giayTos[0].ngayHetHan).format("YYYY-MM-DD") : "",
        issuedBy: response.giayTos.length > 0 ? response.giayTos[0].noiCap : "",
        issuedCountry: response.giayTos.length > 0 ? response.giayTos[0].quocGiaCap : "",
        hasChip: response.giayTos.length > 0 ? response.giayTos[0].coGanChip : false,
    };
};

const convertStudentToPostRequest = (student: Student): StudentPostRequest => {
    return {
        mssv: student.studentId,
        hoTen: student.fullName,
        ngaySinh: moment(student.dob, "YYYY-MM-DD").toArray().slice(0, 3), // Chuyển thành [YYYY, MM, DD]
        gioiTinh: student.gender,
        khoaId: 4, // Giả định giá trị, bạn có thể thay đổi dựa trên logic thực tế
        khoaHoc: student.intake,
        chuongTrinhId: 2, // Giả định
        email: student.email,
        soDienThoai: student.phone,
        tinhTrangId: 2, // Giả định
        quocTich: student.nationality,
        diaChis: [
            {
                loaiDiaChi: "Thường trú",
                soNhaTenDuong: student.permanentAddress.split(", ")[0] || "",
                phuongXa: student.permanentAddress.split(", ")[1] || "",
                quanHuyen: student.permanentAddress.split(", ")[2] || "",
                tinhThanhPho: student.permanentAddress.split(", ")[3] || "",
                quocGia: student.permanentAddress.split(", ")[4] || "",
            },
            {
                loaiDiaChi: "Tạm trú",
                soNhaTenDuong: student.temporaryAddress.split(", ")[0] || "",
                phuongXa: student.temporaryAddress.split(", ")[1] || "",
                quanHuyen: student.temporaryAddress.split(", ")[2] || "",
                tinhThanhPho: student.temporaryAddress.split(", ")[3] || "",
                quocGia: student.temporaryAddress.split(", ")[4] || "",
            },
        ],
        giayTos: [
            {
                loaiGiayTo: student.documentType,
                soGiayTo: student.documentNumber,
                ngayCap: moment(student.issuedDate, "YYYY-MM-DD").toArray().slice(0, 3),
                ngayHetHan: moment(student.expiredDate, "YYYY-MM-DD").toArray().slice(0, 3),
                noiCap: student.issuedBy,
                quocGiaCap: student.issuedCountry,
                ghiChu: "",
                coGanChip: student.hasChip,
            },
        ],
    };
};

export {
    convertGetResponseToStudent,
    convertStudentToPostRequest,
};