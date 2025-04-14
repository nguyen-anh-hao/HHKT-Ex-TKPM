'use client'

import CoursePage from './components/Home';
import { useCourses } from '@/libs/hooks/useCourseQuery';

export default function CourseManagementPage() {
    const { data: courses, error, isLoading } = useCourses();

    if (isLoading) return <div>Đang tải dữ liệu khóa học...</div>;
    if (error) return <div>Lỗi khi tải khóa học</div>;

    return <CoursePage initialCourses={courses || []} />;
}