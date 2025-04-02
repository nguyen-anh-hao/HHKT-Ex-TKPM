import { Address } from './address.interface';
import { Identity } from './identify.interface';

export interface StudentGetResponse {
    studentId: string;
    fullName: string;
    dob: string;
    gender: string;
    faculty: string;
    intake: string;
    program: string;
    email: string;
    phone: string;
    studentStatus: string;
    nationality: string;
    addresses: Address[];
    documents: Identity[];
    createdAt: string;
    updatedAt: string;
    createdBy: string;
    updatedBy: string;
}

export interface StudentPostRequest {
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

export interface StudentPutRequest extends StudentPostRequest {}