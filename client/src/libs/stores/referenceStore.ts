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
};

const useReferenceStore = create<ReferenceStore>((set) => ({
    faculties: [],
    programs: [],
    studentStatuses: [],

    fetchReference: async () => {
        try {
            const faculties = await fetchReference('faculties');
            const programs = await fetchReference('programs');
            const studentStatuses = await fetchReference('student-statuses');
            set({ faculties, programs, studentStatuses });
        } catch (error) {
            // console.error('Failed to fetch reference data:', error);
            return error;
        }
    },
}));

export default useReferenceStore;