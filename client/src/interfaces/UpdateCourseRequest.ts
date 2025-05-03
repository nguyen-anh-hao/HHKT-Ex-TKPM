export interface UpdateCourseRequest {
    courseId: number;
    courseCode?: string;
    courseName?: string;
    credits?: number;
    facultyId?: number | null;
    description?: string | null;
    prerequisiteCourseId?: number | null;
    isActive?: boolean;
}
