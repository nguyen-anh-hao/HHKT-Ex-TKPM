import { Course } from "@/interfaces/Course";
import { CreateCourseRequest } from "@/interfaces/CreateCourseRequest";
import { UpdateCourseRequest } from "@/interfaces/UpdateCourseRequest";
import useReferenceStore from '@/libs/stores/referenceStore';

export const transformCourseToGetResponse = (response: any) => {
    return {
        courseId: response.courseId,
        courseCode: response.courseCode,
        courseName: response.courseName,
        credits: response.credits,
        faculty: response.facultyName,
        description: response.description,
        prerequisiteCourseId: response.prerequisiteCourseId,
        isActive: response.isActive,
    }
}

export const transformCourseToPostRequest = (request: Course): CreateCourseRequest => {
    const { faculties } = useReferenceStore.getState();
    const faculty = faculties.find((faculty: any) => faculty.facultyName === request.faculty);
    if (!faculty) {
        throw new Error(`Faculty not found for name: ${request.faculty}`);
    }
    const facultyId = faculty.id;
    return {
        courseCode: request.courseCode,
        courseName: request.courseName,
        credits: request.credits,
        facultyId: facultyId,
        description: request.description || null,
        prerequisiteCourseId: request.prerequisiteCourseId || null,
        isActive: request.isActive,
    };
}

export const transformCourseToPatchRequest = (request: Course): Partial<UpdateCourseRequest> => {
    const { faculties } = useReferenceStore.getState();
    const faculty = faculties.find((faculty: any) => faculty.facultyName === request.faculty);
    const facultyId = faculty ? faculty.id : 1;

    return {
        courseId: request.courseId,
        courseCode: request.courseCode,
        courseName: request.courseName,
        credits: request.credits,
        facultyId: facultyId,
        description: request.description || null,
        prerequisiteCourseId: request.prerequisiteCourseId || null,
        isActive: request.isActive,
    };
}