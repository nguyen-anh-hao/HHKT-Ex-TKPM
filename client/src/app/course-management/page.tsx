'use client'

import CoursePage from './components/Home';
import { useCourses } from '@/libs/hooks/useCourseQuery';

export default function CourseManagementPage() {
    const { data: courses, error, isLoading } = useCourses();

    if (isLoading) return <div>Đang tải dữ liệu khóa học...</div>;
    if (error) return <div>Lỗi khi tải khóa học</div>;

    return (
        <CoursePage
            initialCourses={
                courses?.map((course: any) => ({
                    courseId: course.courseId,
                    courseCode: course.courseCode,
                    courseName: course.courseName,
                    credits: course.credits,
                    faculty: course.faculty,
                    description: course.description,
                    prerequisiteCourseId: course.prerequisiteCourseId,
                    isActive: course.isActive,
                    facultyId: course.facultyId || null,
                    createdAt: course.createdAt || null,
                    updatedAt: course.updatedAt || null,
                    deleted: course.deleted || false,
                    createdBy: course.createdBy || null,
                    updatedBy: course.updatedBy || null,
                })) || []
            }
        />
    );
}