export interface Student {
    studentId: string;
    fullName: string;
    dob: string;
    gender: string;
    faculty: string;
    intake: string;
    program: string;
    status: string;
    permanentAddress: string;
    temporaryAddress: string;
    email: string;
    phone: string;
    documentType: string;
    documentNumber: string;
    issuedDate: string;
    expiredDate: string;
    issuedBy: string;
    issuedCountry: string;
    hasChip: boolean;
    nationality: string;
}

export const defaultStudent: Student = {
    studentId: '',
    fullName: '',
    dob: '',
    gender: '',
    faculty: '',
    intake: '',
    program: '',
    status: '',
    permanentAddress: '',
    temporaryAddress: '',
    email: '',
    phone: '',
    documentType: '',
    documentNumber: '',
    issuedDate: '',
    expiredDate: '',
    issuedBy: '',
    issuedCountry: '',
    hasChip: false,
    nationality: ''
};