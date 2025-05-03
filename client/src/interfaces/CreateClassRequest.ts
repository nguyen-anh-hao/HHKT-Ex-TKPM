export interface CreateClassRequest {
    classCode: string;
    courseId: number;
    semesterId: number;
    lecturerId?: number | null;
    maxStudents: number;
    schedule: string;
    room: string;
    createdBy?: string;
}
