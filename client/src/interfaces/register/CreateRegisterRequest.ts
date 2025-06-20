export interface RegisterRequest {
    studentId: string;
    studentName: string;
    classId: number;
    courseName: string;
    status: 'REGISTERED' | 'CANCELLED' | 'COMPLETED';
    grade?: number;
}
