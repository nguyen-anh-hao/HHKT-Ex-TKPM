export interface ClassResponse {
    id: number;
    classCode: string;
    courseId: number;
    courseCode?: string;
    courseName?: string;
    semesterId: number;
    semesterName?: string;
    academicYear?: string;
    semester?: number;
    lecturerId?: number | null;
    lecturerName?: string | null;
    maxStudents: number;
    schedule: string;
    room: string;
    isActive?: boolean;
    createdAt: string;
    updatedAt: string;
    deleted: boolean;
    createdBy: string;
    updatedBy: string;
}
