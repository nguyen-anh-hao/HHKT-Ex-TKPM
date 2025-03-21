import { create } from 'zustand';

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
};

// Khởi tạo Zustand store
const useReferenceDataStore = create<ReferenceDataStore>((set) => ({
    facultyOptions: [
        { value: "Khoa Tiếng Pháp", label: "Khoa Tiếng Pháp" },
        { value: "Khoa Tiếng Anh thương mại", label: "Khoa Tiếng Anh thương mại" },
        { value: "Khoa Tiếng Nhật", label: "Khoa Tiếng Nhật" },
        { value: "Khoa Luật", label: "Khoa Luật" },
    ],
    programOptions: [
        { value: "Đại trà", label: "Đại trà" },
        { value: "Chất lượng cao", label: "Chất lượng cao" },
        { value: "Cử nhân tài năng", label: "Cử nhân tài năng" },
    ],
    studentStatusOptions: [
        { value: "Đang học", label: "Đang học" },
        { value: "Đã tốt nghiệp", label: "Đã tốt nghiệp" },
        { value: "Đã thôi học", label: "Đã thôi học" },
        { value: "Tạm dừng học", label: "Tạm dừng học" },
    ],

    // Thêm một faculty mới
    addFaculty: (faculty: string) => set((state) => ({
        facultyOptions: [...state.facultyOptions, { value: faculty, label: faculty }]
    })),

    // Thêm một program mới
    addProgram: (program: string) => set((state) => ({
        programOptions: [...state.programOptions, { value: program, label: program }]
    })),

    // Thêm một student status mới
    addStudentStatus: (studentStatus: string) => set((state) => ({
        studentStatusOptions: [...state.studentStatusOptions, { value: studentStatus, label: studentStatus }]
    })),

    // Xóa một faculty
    deleteFaculty: (faculty: string) => set((state) => ({
        facultyOptions: state.facultyOptions.filter((f) => f.value !== faculty)
    })),

    // Xóa một program
    deleteProgram: (program: string) => set((state) => ({
        programOptions: state.programOptions.filter((p) => p.value !== program)
    })),

    // Xóa một student status
    deleteStudentStatus: (studentStatus: string) => set((state) => ({
        studentStatusOptions: state.studentStatusOptions.filter((s) => s.value !== studentStatus)
    })),

    // Cập nhật faculty
    updateFaculty: (oldFaculty: string, newFaculty: string) => set((state) => ({
        facultyOptions: state.facultyOptions.map((f) =>
            f.value === oldFaculty ? { ...f, value: newFaculty, label: newFaculty } : f
        )
    })),

    // Cập nhật program
    updateProgram: (oldProgram: string, newProgram: string) => set((state) => ({
        programOptions: state.programOptions.map((p) =>
            p.value === oldProgram ? { ...p, value: newProgram, label: newProgram } : p
        )
    })),

    // Cập nhật student status
    updateStudentStatus: (oldStudentStatus: string, newStudentStatus: string) => set((state) => ({
        studentStatusOptions: state.studentStatusOptions.map((s) =>
            s.value === oldStudentStatus ? { ...s, value: newStudentStatus, label: newStudentStatus } : s
        )
    })),
}));

export default useReferenceDataStore;