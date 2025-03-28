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
    const permanentAddressString = permanentAddress?.houseNumberStreetName ? `${permanentAddress.houseNumberStreetName}, ${permanentAddress.wardCommune}, ${permanentAddress.district}, ${permanentAddress.cityProvince}, ${permanentAddress.country}` : "";
    const temporaryAddressString = temporaryAddress?.houseNumberStreetName ? `${temporaryAddress.houseNumberStreetName}, ${temporaryAddress.wardCommune}, ${temporaryAddress.district}, ${temporaryAddress.cityProvince}, ${temporaryAddress.country}` : "";

    return {
        ...response,
        permanentAddress: permanentAddressString,
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
    const permanentAddress: Address | null = request.permanentAddress !== '' ? {
        addressType: "Thường Trú",
        houseNumberStreetName: request.permanentAddress?.split(", ")[0] || "",
        wardCommune: request.permanentAddress?.split(", ")[1] || "",
        district: request.permanentAddress?.split(", ")[2] || "",
        cityProvince: request.permanentAddress?.split(", ")[3] || "",
        country: request.permanentAddress?.split(", ")[4] || "",
    } : null;

    const temporaryAddress: Address | null = request.temporaryAddress !== '' ? {
        addressType: "Tạm Trú",
        houseNumberStreetName: request.temporaryAddress?.split(", ")[0] || "",
        wardCommune: request.temporaryAddress?.split(", ")[1] || "",
        district: request.temporaryAddress?.split(", ")[2] || "",
        cityProvince: request.temporaryAddress?.split(", ")[3] || "",
        country: request.temporaryAddress?.split(", ")[4] || "",
    } : null;

    const addresses: Address[] = [permanentAddress, temporaryAddress].filter((address): address is Address => address !== null && address !== undefined);

    const documents: Identity[] = request.documentType ? [
        {
            documentType: request?.documentType || "",
            documentNumber: request?.documentNumber || "",
            issuedDate: moment(request.issuedDate)?.format("YYYY-MM-DD") || "",
            expiredDate: moment(request.expiredDate)?.format("YYYY-MM-DD") || "",
            issuedBy: request?.issuedBy || "",
            issuedCountry: request?.issuedCountry || "",
            hasChip: request?.hasChip || false,
            note: request?.note || "",
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