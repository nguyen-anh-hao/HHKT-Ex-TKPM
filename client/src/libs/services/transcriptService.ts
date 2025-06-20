import { Transcript } from '@/interfaces/Transcript';
import { CourseResponse } from '@/interfaces/course/CourseResponse';
import { ClassResponse } from '@/interfaces/class/ClassResponse';
import { RegisterResponse } from '@/interfaces/register/RegisterResponse';
import { SemesterResponse } from '@/interfaces/semester/SemesterResponse';

import { getAllCourses } from '../api/courseApi'; // Changed from getCourses to getAllCourses
import { getAllClasses } from '../api/classApi';
import { getAllRegistrations } from '../api/registerApi';
import { getAllSemesters } from '../api/semesterApi';

export const fetchTranscript = async () => {
    try {
        const courses: CourseResponse[] = await getAllCourses();
        const classes: ClassResponse[] = await getAllClasses();
        const semesters: SemesterResponse[] = await getAllSemesters();
        const registrations: RegisterResponse[] = await getAllRegistrations();

        return registrations.map((registration) => {
            const classInfo = classes.find((c) => c.id === registration.classId) as ClassResponse | undefined;
            const course = courses.find((c) => c.courseId === classInfo?.courseId) as CourseResponse | undefined;
            const semester = semesters.find((s) => s.id === classInfo?.semesterId) as SemesterResponse | undefined;

            if (!course || !classInfo) {
                throw new Error(`Course or class not found for registration ID: ${registration.id}`);
            }
            return {
                studentId: registration.studentId,
                classId: registration.classId,
                semester: semester?.semester || '',
                academicYear: semester?.academicYear || '',
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
