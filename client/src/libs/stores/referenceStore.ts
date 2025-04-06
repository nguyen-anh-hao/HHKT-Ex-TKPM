// import { createReference, removeReference as deleteReference, updateReference, fetchReference } from '../services/referenceService';
import { create } from 'zustand';
import { fetchReference } from '../services/referenceService';

type Faculty= { id: number; name: string; };
type Program= { id: number; name: string; };
type StudentStatus= { id: number; name: string; };

type ReferenceStore = {
    faculties: Faculty[];
    programs: Program[];
    studentStatuses: StudentStatus[];
    fetchReference: any;

    // addFaculty: (faculty: string) => void;
    // addProgram: (program: string) => void;
    // addStudentStatus: (studentStatus: string) => void;

    // deleteFaculty: (faculty: string) => void;
    // deleteProgram: (program: string) => void;
    // deleteStudentStatus: (studentStatus: string) => void;

    // updateFaculty: (oldFaculty: string, newFaculty: string) => void;
    // updateProgram: (oldProgram: string, newProgram: string) => void;
    // updateStudentStatus: (oldStudentStatus: string, newStudentStatus: string) => void;
};

const useReferenceStore = create<ReferenceStore>((set) => ({
    faculties: [],
    programs: [],
    studentStatuses: [],

    fetchReference: async () => {
        try {
            const faculties = await fetchReference("faculties");
            const programs = await fetchReference("programs");
            const studentStatuses = await fetchReference("student-statuses");
            set({ faculties, programs, studentStatuses });
        } catch (error) {
            console.error("Failed to fetch reference data:", error);
        }
    },

    // addFaculty: (faculty: string) => {
    //     createReference("faculties", { facultyName: faculty }).then((response) => {
    //         set((state) => ({
    //             facultyOptions: [...state.facultyOptions, { key: response.data.id, value: faculty, label: faculty }],
    //         }));
    //     }).catch((error) => {
    //         console.error("Failed to add faculty:", error);
    //     });
    // },

    // addProgram: (program: string) => {
    //     createReference("programs", { programName: program }).then((response) => {
    //         set((state) => ({
    //             programOptions: [...state.programOptions, { value: program, label: program, key: response.data.id }],
    //         }));
    //     }).catch((error) => {
    //         console.error("Failed to add program:", error);
    //     });
    // },

    // addStudentStatus: (studentStatus: string) => {
    //     createReference("student-statuses", { studentStatusName: studentStatus }).then((response) => {
    //         set((state) => ({
    //             studentStatusOptions: [...state.studentStatusOptions, { value: studentStatus, label: studentStatus, key: response.data.id }],
    //         }));
    //     }).catch((error) => {
    //         console.error("Failed to add student status:", error);
    //     });
    // },

    // deleteFaculty: (faculty: string) => set((state) => {
    //     const facultyId = state.facultyOptions.find((f) => f.value === faculty)?.label;
    //     if (facultyId) {
    //         deleteReference("faculties", facultyId);
    //     } else {
    //         console.error("Faculty ID not found for deletion.");
    //     }
    //     return {
    //         facultyOptions: state.facultyOptions.filter((f) => f.value !== faculty),
    //     };
    // }),

    // deleteProgram: (program: string) => set((state) => {
    //     const programId = state.programOptions.find((p) => p.value === program)?.label;
    //     if (programId) {
    //         deleteReference("programs", programId);
    //     } else {
    //         console.error("Program ID not found for deletion.");
    //     }
    //     return {
    //         programOptions: state.programOptions.filter((p) => p.value !== program),
    //     };
    // }),

    // deleteStudentStatus: (studentStatus: string) => set((state) => {
    //     const studentStatusId = state.studentStatusOptions.find((s) => s.value === studentStatus)?.label;
    //     if (studentStatusId) {
    //         deleteReference("student-statuses", studentStatusId);
    //     } else {
    //         console.error("Student Status ID not found for deletion.");
    //     }
    //     return {
    //         studentStatusOptions: state.studentStatusOptions.filter((s) => s.value !== studentStatus),
    //     };
    // }),

    // updateFaculty: (oldFaculty: string, newFaculty: string) => set((state) => {
    //     const oldFacultyId = state.facultyOptions.find((f) => f.value === oldFaculty)?.label;
    //     if (oldFacultyId) {
    //         updateReference("faculties", oldFacultyId, { facultyName: newFaculty });
    //     } else {
    //         console.error("Faculty ID not found for update.");
    //     }
    //     return {    
    //         facultyOptions: state.facultyOptions.map((f) =>
    //             f.value === oldFaculty ? { ...f, value: newFaculty, label: f.label } : f
    //         )
    //     };
    // }),

    // updateProgram: (oldProgram: string, newProgram: string) => set((state) => {
    //     const oldProgramId = state.programOptions.find((p) => p.value === oldProgram)?.label;
    //     if (oldProgramId) {
    //         updateReference("programs", oldProgramId, { programName: newProgram });
    //     } else {
    //         console.error("Program ID not found for update.");
    //     }
    //     return {
    //         programOptions: state.programOptions.map((p) =>
    //             p.value === oldProgram ? { ...p, value: newProgram, label: p.label } : p
    //         )
    //     };
    // }),

    // updateStudentStatus: (oldStudentStatus: string, newStudentStatus: string) => set((state) => {
    //     const oldStudentStatusId = state.studentStatusOptions.find((s) => s.value === oldStudentStatus)?.label;
    //     if (oldStudentStatusId) {
    //         updateReference("student-statuses", oldStudentStatusId, { studentStatusName: newStudentStatus });
    //     } else {
    //         console.error("Student Status ID not found for update.");
    //     }
    //     return {
    //         studentStatusOptions: state.studentStatusOptions.map((s) =>
    //             s.value === oldStudentStatus ? { ...s, value: newStudentStatus, label: s.label } : s
    //         )
    //     };
    // }),

}));

export default useReferenceStore;