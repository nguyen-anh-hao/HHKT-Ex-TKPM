export interface CreateCourseRequest {
  courseCode: string;
  courseName: string;
  credits: number;
  facultyId: number;
  description?: string | null;
  prerequisiteCourseId?: number | null;
  isActive: boolean;
}