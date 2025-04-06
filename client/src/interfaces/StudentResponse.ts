import { Address } from './Address';
import { Identity } from './Identify';

export interface StudentResponse {
    studentId: string;
    fullName: string;
    dob: string;
    gender: string;
    faculty: string;
    intake: string;
    program: string;
    email: string;
    phoneCountry: string;
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