import { Transcript } from '@/interfaces/Transcript';
import { CourseResponse } from '@/interfaces/course/CourseResponse';
import { ClassResponse } from '@/interfaces/class/ClassResponse';
import { RegisterResponse } from '@/interfaces/register/RegisterResponse';

import { getAllCourses } from '../api/courseApi'; // Changed from getCourses to getAllCourses
import { getAllClasses } from '../api/classApi';
import { getAllRegistrations } from '../api/registerApi';

export const fetchTranscript = async () => {
    try {
        const courses: CourseResponse[] = await getAllCourses(); // Now returns Course[] instead of paginated response
        const classes: ClassResponse[] = await getAllClasses();
        const registrations: RegisterResponse[] = await getAllRegistrations();

        return registrations.map((registration) => {
            const classInfo = classes.find((c) => c.id === registration.classId) as ClassResponse | undefined;
            const course = courses.find((c) => c.courseId === classInfo?.courseId) as CourseResponse | undefined;

            if (!course || !classInfo) {
                throw new Error(`Course or class not found for registration ID: ${registration.id}`);
            }
            return {
                studentId: registration.studentId,
                classId: registration.classId,
                semester: classInfo.semesterName || '',
                academicYear: classInfo?.academicYear || '',
                courseCode: course.courseCode,
                courseName: course.courseName,
                classCode: classInfo?.classCode,
                credits: course.credits,
                grade: registration.grade
            } as Transcript;
        });
    } catch (error) {
        throw error;
    }
};
