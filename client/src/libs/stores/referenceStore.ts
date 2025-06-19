import { create } from 'zustand';
import { fetchReference } from '../services/referenceService';

// Update types to match the actual API response format
type Faculty = { id: number; facultyName: string; };
type Program = { id: number; programName: string; };
type StudentStatus = { id: number; studentStatusName: string; };

type ReferenceStore = {
    faculties: Faculty[];
    programs: Program[];
    studentStatuses: StudentStatus[];
    fetchReference: any;
};

const useReferenceStore = create<ReferenceStore>((set) => ({
    faculties: [],
    programs: [],
    studentStatuses: [],

    fetchReference: async () => {
        try {
            const faculties = (await fetchReference('faculties')) as Faculty[];
            const programs = (await fetchReference('programs')) as Program[];
            const studentStatuses = (await fetchReference('student-statuses')) as StudentStatus[];
            set({ faculties, programs, studentStatuses });
        } catch (error) {
            // console.error('Failed to fetch reference data:', error);
            return error;
        }
    },
}));

export default useReferenceStore;