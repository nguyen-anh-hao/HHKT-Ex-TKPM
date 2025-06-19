import { Course } from "@/interfaces/course/Course";
import { CreateCourseRequest } from "@/interfaces/course/CreateCourseRequest";
import { UpdateCourseRequest } from "@/interfaces/course/UpdateCourseRequest";
import useReferenceStore from '@/libs/stores/referenceStore';

export const transformCourseToGetResponse = (response: any) => {
    return {
        courseId: response.courseId,
        courseCode: response.courseCode,
        courseName: response.courseName,
        credits: response.credits,
        faculty: response.facultyId, // Map facultyId to faculty for UI
        description: response.description,
        prerequisiteCourseId: response.prerequisiteCourseId,
        isActive: response.isActive,
    }
}

export const transformCourseToPostRequest = (request: Course): CreateCourseRequest => {
    // faculty is facultyId
    return {
        courseCode: request.courseCode,
        courseName: request.courseName,
        credits: request.credits,
        facultyId: request.facultyId, // Use facultyId directly
        description: request.description || null,
        prerequisiteCourseId: request.prerequisiteCourseId || null,
        isActive: request.isActive,
    };
}

export const transformCourseToPatchRequest = (request: Course): Partial<UpdateCourseRequest> => {
    return {
        courseId: request.courseId,
        courseCode: request.courseCode,
        courseName: request.courseName,
        credits: request.credits,
        facultyId: request.facultyId, // Use facultyId directly
        description: request.description || null,
        prerequisiteCourseId: request.prerequisiteCourseId || null,
        isActive: request.isActive,
    };
}