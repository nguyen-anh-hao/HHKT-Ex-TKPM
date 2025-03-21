"use client";

import { useState } from "react";
import { Button, Input, Space, Typography } from "antd";
import useReferenceDataStore from "../../lib/stores/referenceDataStore";

const { Title } = Typography;

const FacultyPage = () => {
    const { facultyOptions } = useReferenceDataStore() as { facultyOptions: { value: string; label: string }[] };
    const { programOptions } = useReferenceDataStore() as { programOptions: { value: string; label: string }[] };
    const { studentStatusOptions } = useReferenceDataStore() as { studentStatusOptions: { value: string; label: string }[] };

    const { addFaculty } = useReferenceDataStore() as { addFaculty: (faculty: string) => void };
    const { addProgram } = useReferenceDataStore() as { addProgram: (program: string) => void };
    const { addStudentStatus } = useReferenceDataStore() as { addStudentStatus: (studentStatus: string) => void };

    const { deleteFaculty } = useReferenceDataStore() as { deleteFaculty: (faculty: string) => void };
    const { deleteProgram } = useReferenceDataStore() as { deleteProgram: (program: string) => void };
    const { deleteStudentStatus } = useReferenceDataStore() as { deleteStudentStatus: (studentStatus: string) => void };

    const { updateFaculty } = useReferenceDataStore() as { updateFaculty: (oldFaculty: string, newFaculty: string) => void };
    const { updateProgram } = useReferenceDataStore() as { updateProgram: (oldProgram: string, newProgram: string) => void };
    const { updateStudentStatus } = useReferenceDataStore() as { updateStudentStatus: (oldStudentStatus: string, newStudentStatus: string) => void };

    const [faculties, setFaculties] = useState<string[]>(facultyOptions.map((option) => option.value));
    const [programs, setPrograms] = useState<string[]>(programOptions.map((option) => option.value));
    const [statuses, setStatuses] = useState<string[]>(studentStatusOptions.map((option) => option.value));

    const handleAdd = (setState: any) => {
        setState((prev: any) => [...prev, ""]);
        if (setState === setFaculties) addFaculty("");
        if (setState === setPrograms) addProgram("");
        if (setState === setStatuses) addStudentStatus("");
    };

    const handleChange = (setState: any, index: number, value: string) => {
        setState((prev: any[]) => prev.map((item, i) => (i === index ? value : item)));
        if (setState === setFaculties) updateFaculty(faculties[index], value);
        if (setState === setPrograms) updateProgram(programs[index], value);
        if (setState === setStatuses) updateStudentStatus(statuses[index], value);
    };

    const handleDelete = (setState: any, index: number) => {
        setState((prev: any[]) => prev.filter((_, i) => i !== index));
        if (setState === setFaculties) deleteFaculty(faculties[index]);
        if (setState === setPrograms) deleteProgram(programs[index]);
        if (setState === setStatuses) deleteStudentStatus(statuses[index]);
    };

    return (
        <div style={{ display: "flex", justifyContent: "center", gap: "40px", padding: "20px", background: "#f8f6f2" }}>
            <div style={{ textAlign: "center" }}>
                <Title level={3}>Khoa</Title>
                {faculties.map((faculty, index) => (
                    <Space key={index} style={{ display: "flex", marginBottom: "8px" }}>
                        <Input value={faculty} onChange={(e) => handleChange(setFaculties, index, e.target.value)} />
                        <Button danger onClick={() => handleDelete(setFaculties, index)}>Xóa</Button>
                    </Space>
                ))}
                <Button type="dashed" onClick={() => handleAdd(setFaculties)}>Thêm Khoa</Button>
            </div>

            <div style={{ textAlign: "center" }}>
                <Title level={3}>Chương trình</Title>
                {programs.map((program, index) => (
                    <Space key={index} style={{ display: "flex", marginBottom: "8px" }}>
                        <Input value={program} onChange={(e) => handleChange(setPrograms, index, e.target.value)} />
                        <Button danger onClick={() => handleDelete(setPrograms, index)}>Xóa</Button>
                    </Space>
                ))}
                <Button type="dashed" onClick={() => handleAdd(setPrograms)}>Thêm Chương trình</Button>
            </div>


            <div style={{ textAlign: "center" }}>
                <Title level={3}>Trạng thái</Title>
                {statuses.map((status, index) => (
                    <Space key={index} style={{ display: "flex", marginBottom: "8px" }}>
                        <Input value={status} onChange={(e) => handleChange(setStatuses, index, e.target.value)} />
                        <Button danger onClick={() => handleDelete(setStatuses, index)}>Xóa</Button>
                    </Space>
                ))}
                <Button type="dashed" onClick={() => handleAdd(setStatuses)}>Thêm Trạng thái</Button>
            </div>

            <div style={{ position: "absolute", bottom: "20px", textAlign: "center", width: "100%" }}>
                <Button type="primary" size="large">Lưu thay đổi</Button>
            </div>
        </div>
    );
};

export default FacultyPage;
