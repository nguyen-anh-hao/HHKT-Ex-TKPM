import { create } from 'zustand';
import { createReferenceData, deleteReferenceData, updateReferenceData, fetchReferenceData } from '../api/referenceDataApi';

// Định nghĩa kiểu dữ liệu cho store
type Option = { value: string; label: string };

type ReferenceDataStore = {
    facultyOptions: Option[];
    programOptions: Option[];
    studentStatusOptions: Option[];

    // Các phương thức
    addFaculty: (faculty: string) => void;
    addProgram: (program: string) => void;
    addStudentStatus: (studentStatus: string) => void;

    deleteFaculty: (faculty: string) => void;
    deleteProgram: (program: string) => void;
    deleteStudentStatus: (studentStatus: string) => void;

    updateFaculty: (oldFaculty: string, newFaculty: string) => void;
    updateProgram: (oldProgram: string, newProgram: string) => void;
    updateStudentStatus: (oldStudentStatus: string, newStudentStatus: string) => void;

    fetchReferenceData: () => Promise<void>;
};

// Khởi tạo Zustand store
const useReferenceDataStore = create<ReferenceDataStore>((set) => ({
    facultyOptions: [],
    programOptions: [],
    studentStatusOptions: [],

    fetchReferenceData: async () => {
        try {
            const facultyOptions = await fetchReferenceData("faculties");
            const programOptions = await fetchReferenceData("programs");
            const studentStatusOptions = await fetchReferenceData("student-statuses");

            console.log("Fetched faculties:", facultyOptions);
            console.log("Fetched programs:", programOptions);
            console.log("Fetched statuses:", studentStatusOptions);

            set({
                facultyOptions: facultyOptions.map((f: any) => ({ value: f.facultyName, label: f.id })),
                programOptions: programOptions.map((p: any) => ({ value: p.programName, label: p.id })),
                studentStatusOptions: studentStatusOptions.map((s: any) => ({ value: s.studentStatusName, label: s.id })),
            });
        } catch (error) {
            console.error("Failed to fetch reference data:", error);
        }
    },

    addFaculty: (faculty: string) => {
        createReferenceData("faculties", { facultyName: faculty }).then((response) => {
            set((state) => ({
                facultyOptions: [...state.facultyOptions, { value: faculty, label: response.data.id }],
            }));
        }).catch((error) => {
            console.error("Failed to add faculty:", error);
        });
    },

    addProgram: (program: string) => {
        createReferenceData("programs", { programName: program }).then((response) => {
            set((state) => ({
                programOptions: [...state.programOptions, { value: program, label: response.data.id }],
            }));
        }).catch((error) => {
            console.error("Failed to add program:", error);
        });
    },

    addStudentStatus: (studentStatus: string) => {
        createReferenceData("student-statuses", { studentStatusName: studentStatus }).then((response) => {
            set((state) => ({
                studentStatusOptions: [...state.studentStatusOptions, { value: studentStatus, label: response.data.id }],
            }));
        }).catch((error) => {
            console.error("Failed to add student status:", error);
        });
    },

    deleteFaculty: (faculty: string) => set((state) => {
        const facultyId = state.facultyOptions.find((f) => f.value === faculty)?.label;
        if (facultyId) {
            deleteReferenceData("faculties", facultyId);
        } else {
            console.error("Faculty ID not found for deletion.");
        }
        return {
            facultyOptions: state.facultyOptions.filter((f) => f.value !== faculty),
        };
    }),

    deleteProgram: (program: string) => set((state) => {
        const programId = state.programOptions.find((p) => p.value === program)?.label;
        if (programId) {
            deleteReferenceData("programs", programId);
        } else {
            console.error("Program ID not found for deletion.");
        }
        return {
            programOptions: state.programOptions.filter((p) => p.value !== program),
        };
    }),

    deleteStudentStatus: (studentStatus: string) => set((state) => {
        const studentStatusId = state.studentStatusOptions.find((s) => s.value === studentStatus)?.label;
        if (studentStatusId) {
            deleteReferenceData("student-statuses", studentStatusId);
        } else {
            console.error("Student Status ID not found for deletion.");
        }
        return {
            studentStatusOptions: state.studentStatusOptions.filter((s) => s.value !== studentStatus),
        };
    }),

    updateFaculty: (oldFaculty: string, newFaculty: string) => set((state) => {
        const oldFacultyId = state.facultyOptions.find((f) => f.value === oldFaculty)?.label;
        if (oldFacultyId) {
            updateReferenceData("faculties", oldFacultyId, { facultyName: newFaculty });
        } else {
            console.error("Faculty ID not found for update.");
        }
        return {    
            facultyOptions: state.facultyOptions.map((f) =>
                f.value === oldFaculty ? { ...f, value: newFaculty, label: f.label } : f
            )
        };
    }),

    updateProgram: (oldProgram: string, newProgram: string) => set((state) => {
        const oldProgramId = state.programOptions.find((p) => p.value === oldProgram)?.label;
        if (oldProgramId) {
            updateReferenceData("programs", oldProgramId, { programName: newProgram });
        } else {
            console.error("Program ID not found for update.");
        }
        return {
            programOptions: state.programOptions.map((p) =>
                p.value === oldProgram ? { ...p, value: newProgram, label: p.label } : p
            )
        };
    }),

    updateStudentStatus: (oldStudentStatus: string, newStudentStatus: string) => set((state) => {
        const oldStudentStatusId = state.studentStatusOptions.find((s) => s.value === oldStudentStatus)?.label;
        if (oldStudentStatusId) {
            updateReferenceData("student-statuses", oldStudentStatusId, { studentStatusName: newStudentStatus });
        } else {
            console.error("Student Status ID not found for update.");
        }
        return {
            studentStatusOptions: state.studentStatusOptions.map((s) =>
                s.value === oldStudentStatus ? { ...s, value: newStudentStatus, label: s.label } : s
            )
        };
    }),
}));

export default useReferenceDataStore;