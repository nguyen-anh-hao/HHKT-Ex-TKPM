'use client';

import { useEffect, useState } from "react";
import { Button } from "antd";
import { PlusOutlined } from "@ant-design/icons";
import StudentTable from "./StudentTable";
import StudentModal from "./StudentModal";
import { Student } from "@/interfaces/Student";
import { updateStudent as updateStudentState, addStudent as addStudentState, deleteStudent as deleteStudentState } from "../actions/StudentActions";
import { useCreateStudent, useDeleteStudent, useUpdateStudent } from "@/libs/hooks/useStudentMutation";
import useReferenceStore from "@/libs/stores/referenceStore";

export default function Home({ initialStudents }: { initialStudents: Student[] }) {
    const [students, setStudents] = useState<Student[]>(initialStudents);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [selectedStudent, setSelectedStudent] = useState<Student | null>(null);

    const { mutate: createStudentAPI } = useCreateStudent();
    const { mutate: updateStudentAPI } = useUpdateStudent();
    const { mutate: deleteStudentAPI } = useDeleteStudent();

    const fetchReference = useReferenceStore((state) => state.fetchReference);
    
    useEffect(() => {
        fetchReference();
    }, [fetchReference]);

    const handleAddOrUpdateStudent = (values: Student) => {
        if (selectedStudent) {
            setStudents(updateStudentState(students, values));
            updateStudentAPI(values);
        } else {
            setStudents(addStudentState(students, values));
            createStudentAPI(values);
        }
    };

    const handleDeleteStudent = (studentId: string) => {
        setStudents(deleteStudentState(students, studentId));
        deleteStudentAPI(studentId);
    };

    return (
        <div>
            <h1>Quản lý sinh viên</h1>
            <Button type="primary" icon={<PlusOutlined />} onClick={() => { setSelectedStudent(null); setIsModalVisible(true); }}>
                Thêm sinh viên
            </Button>
            <StudentTable
                students={students}
                openModal={setIsModalVisible}
                onEdit={setSelectedStudent}
                onDelete={handleDeleteStudent}
            />
            <StudentModal
                visible={isModalVisible}
                onCancel={() => { setIsModalVisible(false); setSelectedStudent(null); }}
                onSubmit={handleAddOrUpdateStudent}
                student={selectedStudent || undefined}
            />
        </div>
    );
}

// Ý tưởng: Khi Home được Mount thì ngay lập tức gọi fetchReference để lấy dữ liệu về
// và lưu vào store. Sau đó, khi cần sử dụng dữ liệu này trong các component khác, chỉ cần gọi useReferenceStore để lấy dữ liệu từ store.
// Điều này giúp giảm thiểu số lần gọi API và tăng hiệu suất của ứng dụng.
// Bên cạnh đó, việc sử dụng Zustand giúp quản lý trạng thái toàn cục một cách dễ dàng và hiệu quả hơn.
// Tuy nhiên, store chỉ nên dùng khi mà không thể dùng useQuery và fetch được.

// Get API sẽ 2 kiểu, một là trực tiếp thông qua fetch, hai là thông qua useQuery.
// Ưu điểm của useQuery là có thể theo dõi được trạng thái loading, error, success của API. Còn fetch thì không.
// Vì vậy, nếu làm việc với UI thì nên sử dụng useQuery.
// Còn nếu làm việc với các tác vụ không liên quan đến UI thì có thể sử dụng fetch.

// API -> Services (Transform) -> useQuery hooks / fetch -> Component (optional: useStore = fetch) -> Page