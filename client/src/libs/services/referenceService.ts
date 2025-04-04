import { getReference, postReference, putReference, deleteReference } from "../api/referenceApi";

type ReferenceType = "faculties" | "programs" | "student-statuses";

export const fetchReference = async (type: ReferenceType) => {
    try {
        const data = await getReference(type);
        return data.data;
    } catch (error) {
        console.error(`Error fetching ${type}:`, error);
        throw error;
    }
};

export const createReference = async (type: ReferenceType, values: any) => {
    let payload: any = {};
    if (type === "faculties") {
        payload = { facultyName: values.facultyName };
    } else if (type === "programs") {
        payload = { programName: values.programName };
    } else if (type === "student-statuses") {
        payload = { studentStatusName: values.studentStatusName };
    }

    const { data } = await postReference(type, payload);
    return data;
};

export const updateReference = async (type: ReferenceType, id: string, values: any) => {
    const { data } = await putReference(type, id, values);
    return data;
};

export const removeReference = async (type: ReferenceType, id: string) => {
    return await deleteReference(type, id);
};