import moment from 'moment';
import { Student } from '@/interfaces/student/Student';
import { Address } from '@/interfaces/Address';
import { Identity } from '@/interfaces/Identify';
import { StudentResponse } from '@/interfaces/student/StudentResponse';
import { CreateStudentRequest } from '@/interfaces/student/CreateStudentRequest';
import { UpdateStudentRequest } from '@/interfaces/student/UpdateStudentRequest';
import useReferenceStore from '@/libs/stores/referenceStore';

export const transformGetResponseToStudent = (response: StudentResponse): Student => {
    const permanentAddress = response.addresses.find((address: Address) => address.addressType === 'Thường Trú' || address.addressType === 'Permanent Address');
    const temporaryAddress = response.addresses.find((address: Address) => address.addressType === 'Tạm Trú' || address.addressType === 'Temporary Address');
    const CMND = response.documents.find((doc: any) => doc.documentType === 'CMND' || doc.documentType === 'ID Card');
    const CCCD = response.documents.find((doc: any) => doc.documentType === 'CCCD' || doc.documentType === 'Citizen ID Card');
    const passport = response.documents.find((doc: any) => doc.documentType === 'Hộ chiếu' || doc.documentType === 'Passport');
    const permanentAddressString = permanentAddress?.houseNumberStreetName ? `${permanentAddress.houseNumberStreetName}, ${permanentAddress.wardCommune}, ${permanentAddress.district}, ${permanentAddress.cityProvince}, ${permanentAddress.country}` : '';
    const temporaryAddressString = temporaryAddress?.houseNumberStreetName ? `${temporaryAddress.houseNumberStreetName}, ${temporaryAddress.wardCommune}, ${temporaryAddress.district}, ${temporaryAddress.cityProvince}, ${temporaryAddress.country}` : '';

    return {
        ...response,
        permanentAddress: permanentAddressString,
        temporaryAddress: temporaryAddressString,
        documentType: CMND ? 'CMND' : CCCD ? 'CCCD' : passport ? 'Hộ chiếu' : '',
        documentNumber: CMND ? CMND.documentNumber : CCCD ? CCCD.documentNumber : passport ? passport.documentNumber : '',
        issuedDate: CMND ? CMND.issuedDate : CCCD ? CCCD.issuedDate : passport ? passport.issuedDate : '',
        expiredDate: CMND ? CMND.expiredDate : CCCD ? CCCD.expiredDate : passport ? passport.expiredDate : '',
        issuedBy: CMND ? CMND.issuedBy : CCCD ? CCCD.issuedBy : passport ? passport.issuedBy : '',
        issuedCountry: passport ? passport.issuedCountry : '',
        hasChip: CCCD ? CCCD.hasChip : false,
        note: passport ? passport.note : '',
        // permanentAddress: '',
        // temporaryAddress: '',
        // documentType: '',
        // documentNumber: '',
        // issuedDate: '',
        // expiredDate: '',
        // issuedBy: '',
        // issuedCountry: '',
        // hasChip: false,
        // note: '',
    };
};

export const transformStudentToPostRequest = (request: Student): Partial<CreateStudentRequest> => {
    const permanentAddress: Address | null = request.permanentAddress !== '' ? {
        addressType: 'Thường Trú',
        houseNumberStreetName: request.permanentAddress?.split(', ')[0] || '',
        wardCommune: request.permanentAddress?.split(', ')[1] || '',
        district: request.permanentAddress?.split(', ')[2] || '',
        cityProvince: request.permanentAddress?.split(', ')[3] || '',
        country: request.permanentAddress?.split(', ')[4] || '',
    } : null;

    const temporaryAddress: Address | null = request.temporaryAddress !== '' ? {
        addressType: 'Tạm Trú',
        houseNumberStreetName: request.temporaryAddress?.split(', ')[0] || '',
        wardCommune: request.temporaryAddress?.split(', ')[1] || '',
        district: request.temporaryAddress?.split(', ')[2] || '',
        cityProvince: request.temporaryAddress?.split(', ')[3] || '',
        country: request.temporaryAddress?.split(', ')[4] || '',
    } : null;

    const addresses: Address[] = [permanentAddress, temporaryAddress].filter((address): address is Address => address !== null && address !== undefined);

    const documents: Identity[] = request.documentType ? [
        {
            documentType: request?.documentType || '',
            documentNumber: request?.documentNumber || '',
            issuedDate: moment(request.issuedDate)?.format('YYYY-MM-DD') || '',
            expiredDate: moment(request.expiredDate)?.format('YYYY-MM-DD') || '',
            issuedBy: request?.issuedBy || '',
            issuedCountry: request?.issuedCountry || '',
            hasChip: request?.hasChip || false,
            note: request?.note || '',
        },
    ] : [];

    const { faculties, programs, studentStatuses } = useReferenceStore.getState();
    const faculty = faculties.find((faculty: any) => faculty.facultyName === request.faculty);
    const program = programs.find((program: any) => program.programName === request.program);
    const studentStatus = studentStatuses.find((status: any) => status.studentStatusName === request.studentStatus);

    const facultyId = faculty ? faculty.id : 1;
    const programId = program ? program.id : 1;
    const studentStatusId = studentStatus ? studentStatus.id : 1;

    return {
        studentId: request.studentId,
        fullName: request.fullName,
        dob: moment(request.dob).format('YYYY-MM-DD'),
        gender: request.gender,
        facultyId,
        intake: request.intake,
        programId,
        email: request.email,
        phoneCountry: request.phoneCountry,
        phone: request.phone,
        studentStatusId,
        nationality: request.nationality,
        addresses,
        documents,
    }
}

export const transformStudentToPatchRequest = (request: Student): Partial<UpdateStudentRequest> => {
    return transformStudentToPostRequest(request) as Partial<UpdateStudentRequest>;
};