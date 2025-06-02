import { StudentResponse } from './StudentResponse';
import { Identity } from './Identify';

export interface Student extends StudentResponse, Identity {
    permanentAddress: string;
    temporaryAddress: string;
}