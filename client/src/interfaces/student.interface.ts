// import { Address } from './address.interface';
// import { Identity } from './identify.interface';

// export interface Student {
//     studentId: string;
//     fullName: string;
//     dob: string;
//     gender: string;
//     faculty: string;
//     intake: string;
//     program: string;
//     email: string;
//     phone: string;
//     studentStatus: string;
//     nationality: string;
//     addresses: Address[];
//     documents: Identity[];
//     createdAt: string;
//     updatedAt: string;
//     createdBy: string;
//     updatedBy: string;
// }

import { StudentGetResponse } from "./api.interface";
import { Identity } from "./identify.interface";

export interface Student extends StudentGetResponse, Identity {
    permanentAddress: string;
    temporaryAddress: string;
}