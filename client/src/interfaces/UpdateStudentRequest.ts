import { Address } from './Address';
import { Identity } from './Identify';

export interface UpdateStudentRequest {
    studentId: string;
    fullName: string;
    dob: string;
    gender: string;
    facultyId: number;
    intake: string;
    programId: number;
    email: string;
    phone: string;
    studentStatusId: number;
    nationality: string;
    addresses: Address[];
    documents: Identity[];
}