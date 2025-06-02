export interface RegisterRequest {
    studentId: string;
    studentName: string;
    classId: string;
    courseName: string;
    status: 'REGISTERED' | 'CANCELLED' | 'COMPLETED';
    grade?: number;
}
