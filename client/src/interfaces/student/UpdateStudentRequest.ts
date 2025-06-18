import { Address } from '@/interfaces/Address';
import { Identity } from '@/interfaces/Identify';

export interface UpdateStudentRequest {
    studentId: string;
    fullName: string;
    dob: string;
    gender: string;
    facultyId: number;
    intake: string;
    programId: number;
    email: string;
    phoneCountry: string;
    phone: string;
    studentStatusId: number;
    nationality: string;
    addresses: Address[];
    documents: Identity[];
}