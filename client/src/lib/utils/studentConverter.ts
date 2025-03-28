import moment from "moment";
import { Student } from "@/interfaces/student.interface";
import { Address } from "@/interfaces/address.interface";
import { Identity } from "@/interfaces/identify.interface";
import { StudentGetResponse, StudentPostRequest, StudentPutRequest } from "@/interfaces/api.interface";

export const convertGetResponseToStudent = (response: StudentGetResponse): Student => {
    const permanentAddress = response.addresses.find((address: Address) => address.addressType === "Thường Trú");
    const temporaryAddress = response.addresses.find((address: Address) => address.addressType === "Tạm Trú");
    const CMND = response.documents.find((doc: any) => doc.documentType === "CMND");
    const CCCD = response.documents.find((doc: any) => doc.documentType === "CCCD");
    const passport = response.documents.find((doc: any) => doc.documentType === "Hộ chiếu");
    const permanentAddressString = permanentAddress ? `${permanentAddress.houseNumberStreetName}, ${permanentAddress.wardCommune}, ${permanentAddress.district}, ${permanentAddress.cityProvince}, ${permanentAddress.country}` : "";
    const temporaryAddressString = temporaryAddress ? `${temporaryAddress.houseNumberStreetName}, ${temporaryAddress.wardCommune}, ${temporaryAddress.district}, ${temporaryAddress.cityProvince}, ${temporaryAddress.country}` : "";

    return {
        ...response,
        permenantAddress: permanentAddressString,
        temporaryAddress: temporaryAddressString,
        documentType: CMND ? "CMND" : CCCD ? "CCCD" : passport ? "Hộ chiếu" : "",
        documentNumber: CMND ? CMND.documentNumber : CCCD ? CCCD.documentNumber : passport ? passport.documentNumber : "",
        issuedDate: CMND ? CMND.issuedDate : CCCD ? CCCD.issuedDate : passport ? passport.issuedDate : "",
        expiredDate: CMND ? CMND.expiredDate : CCCD ? CCCD.expiredDate : passport ? passport.expiredDate : "",
        issuedBy: CMND ? CMND.issuedBy : CCCD ? CCCD.issuedBy : passport ? passport.issuedBy : "",
        issuedCountry: passport ? passport.issuedCountry : "",
        hasChip: CCCD ? CCCD.hasChip : false,
        note: passport ? passport.note : "",
    };
};

export const convertStudentToPostRequest = (request: Student): StudentPostRequest => {
    const permanentAddress: Address | null = request.permenantAddress !== '' ? {
        addressType: "Thường Trú",
        houseNumberStreetName: request.permenantAddress?.split(", ")[0] || "",
        wardCommune: request.permenantAddress?.split(", ")[1] || "",
        district: request.permenantAddress?.split(", ")[2] || "",
        cityProvince: request.permenantAddress?.split(", ")[3] || "",
        country: request.permenantAddress?.split(", ")[4] || "",
    } : null;

    const temporaryAddress: Address | null = request.temporaryAddress !== '' ? {
        addressType: "Tạm Trú",
        houseNumberStreetName: request.temporaryAddress?.split(", ")[0] || "",
        wardCommune: request.temporaryAddress?.split(", ")[1] || "",
        district: request.temporaryAddress?.split(", ")[2] || "",
        cityProvince: request.temporaryAddress?.split(", ")[3] || "",
        country: request.temporaryAddress?.split(", ")[4] || "",
    } : null;

    const addresses: Address[] = [permanentAddress, temporaryAddress].filter((address): address is Address => address !== null && address.houseNumberStreetName !== '');

    const documents: Identity[] = request.documentType ? [
        {
            documentType: request.documentType,
            documentNumber: request.documentNumber,
            issuedDate: request.issuedDate,
            expiredDate: request.expiredDate,
            issuedBy: request.issuedBy,
            issuedCountry: request.issuedCountry,
            hasChip: request.hasChip,
            note: request.note,
        },
    ] : [];

    return {
        studentId: request.studentId,
        fullName: request.fullName,
        dob: moment(request.dob).format("YYYY-MM-DD"),
        gender: request.gender,
        facultyId: 1,
        intake: request.intake,
        programId: 1,
        email: request.email,
        phone: request.phone,
        studentStatusId: 1,
        nationality: request.nationality,
        addresses,
        documents,
    }
}

export const convertStudentToPutRequest = (request: Student): StudentPutRequest => {
    return convertStudentToPostRequest(request) as StudentPutRequest;
};

// const convertStudentToPostRequest = (student: Student): StudentPostRequest => {
//     return {
//         mssv: student.studentId,
//         hoTen: student.fullName,
//         ngaySinh: moment(student.dob, "YYYY-MM-DD").toArray().slice(0, 3), // Chuyển thành [YYYY, MM, DD]
//         gioiTinh: student.gender,
//         khoaId: 4, // Giả định giá trị, bạn có thể thay đổi dựa trên logic thực tế
//         khoaHoc: student.intake,
//         chuongTrinhId: 2, // Giả định
//         email: student.email,
//         soDienThoai: student.phone,
//         tinhTrangId: 2, // Giả định
//         quocTich: student.nationality,
//         diaChis: [
//             {
//                 loaiDiaChi: "Thường trú",
//                 soNhaTenDuong: student.permanentAddress.split(", ")[0] || "",
//                 phuongXa: student.permanentAddress.split(", ")[1] || "",
//                 quanHuyen: student.permanentAddress.split(", ")[2] || "",
//                 tinhThanhPho: student.permanentAddress.split(", ")[3] || "",
//                 quocGia: student.permanentAddress.split(", ")[4] || "",
//             },
//             {
//                 loaiDiaChi: "Tạm trú",
//                 soNhaTenDuong: student.temporaryAddress.split(", ")[0] || "",
//                 phuongXa: student.temporaryAddress.split(", ")[1] || "",
//                 quanHuyen: student.temporaryAddress.split(", ")[2] || "",
//                 tinhThanhPho: student.temporaryAddress.split(", ")[3] || "",
//                 quocGia: student.temporaryAddress.split(", ")[4] || "",
//             },
//         ],
//         giayTos: [
//             {
//                 loaiGiayTo: student.documentType,
//                 soGiayTo: student.documentNumber,
//                 ngayCap: moment(student.issuedDate, "YYYY-MM-DD").toArray().slice(0, 3),
//                 ngayHetHan: moment(student.expiredDate, "YYYY-MM-DD").toArray().slice(0, 3),
//                 noiCap: student.issuedBy,
//                 quocGiaCap: student.issuedCountry,
//                 ghiChu: "",
//                 coGanChip: student.hasChip,
//             },
//         ],
//     };
// };

// export {
//     convertGetResponseToStudent,
//     convertStudentToPostRequest,
// };