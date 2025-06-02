export interface CourseResponse {
    courseId: number;
    courseCode: string;
    courseName: string;
    credits: number;
    facultyId: number;
    description?: string | null;
    prerequisiteCourseId?: number | null;
    isActive: boolean;
    createdAt: string;
    updatedAt: string;
    deleted: boolean;
    createdBy: string;
    updatedBy: string;
}