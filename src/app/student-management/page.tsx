"use client"

import { useState } from "react";
import { Button } from "antd";
import { PlusOutlined } from "@ant-design/icons";
import StudentTable from "./_components/StudentTable";
import StudentModal from "./_components/StudentModal";
import { addStudent, updateStudent, deleteStudent } from "./_components/StudentActions";

const Home = () => {
    const [students, setStudents] = useState<{ mssv: string; name: string; age: number }[]>([]);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [selectedStudent, setSelectedStudent] = useState(null);

    return (
        <div>
            <h1>Quản lý sinh viên</h1>
            <Button style={{ marginBottom: 16 }} type="primary" icon={<PlusOutlined />} onClick={() => { setSelectedStudent(null); setIsModalVisible(true); }}>Thêm sinh viên</Button>
            <StudentTable students={students} openModal={setIsModalVisible} onEdit={setSelectedStudent} onDelete={(mssv: string) => setStudents(deleteStudent(students, mssv))} />
            <StudentModal 
                visible={isModalVisible} 
                onCancel={() => setIsModalVisible(false)}
                onSubmit={(values: any) => {
                    if (selectedStudent) {
                        setStudents(updateStudent(students, values));
                    }
                    else {
                        setStudents(addStudent(students, values));

                    }
                }}
                student={selectedStudent}
            />
        </div>
    );
};

export default Home;

// Chuyền dữ liệu từ component cha xuống component con thông qua props
// Chuyền dữ liệu từ component con lên component cha thông qua callback function

// Bug
// Sau khi thêm sinh viên thành công thì modal không được reset lại => resolved

