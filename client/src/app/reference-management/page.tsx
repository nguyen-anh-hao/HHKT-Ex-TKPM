"use client";

import { useState, useEffect } from "react";
import { Button, Input, Space, Typography } from "antd";
import { useFaculties, usePrograms, useStudentStatuses } from "@/libs/hooks/useReferences";

import { createReference, updateReference, removeReference } from "@/libs/services/referenceService";

const { Title } = Typography;

const FacultyPage = () => {
    const [faculties, setFaculties] = useState<string[]>([]);
    const [programs, setPrograms] = useState<string[]>([]);
    const [statuses, setStatuses] = useState<string[]>([]);

    const facultyOptions = useFaculties().data || [];
    const programOptions = usePrograms().data || [];
    const studentStatusOptions = useStudentStatuses().data || [];

    useEffect(() => {
        setFaculties(facultyOptions.map((option: any) => option.value));
        setPrograms(programOptions.map((option: any) => option.value));
        setStatuses(studentStatusOptions.map((option: any) => option.value));
    }, [facultyOptions, programOptions, studentStatusOptions]);

    type ReferenceType = "faculties" | "programs" | "student-statuses";

    const addFunction = (value: string, type: ReferenceType) => {
        // createReference(type, value);
    };

    const updateFunction = (oldValue: string, newValue: string, type: ReferenceType) => {
        const id = type === "faculties" ? faculties.find((item) => item === oldValue) : type === "programs" ? programs.find((item) => item === oldValue) : statuses.find((item) => item === oldValue);
        if (id) {
            // updateReference(type, id, newValue);
        }
    };

    const deleteFunction = (value: string, type: ReferenceType) => {
        const id = type === "faculties" ? faculties.find((item) => item === value) : type === "programs" ? programs.find((item) => item === value) : statuses.find((item) => item === value);
        if (id) {
            // removeReference(type, id);
        }
    };

    const handleAdd = (setState: React.Dispatch<React.SetStateAction<string[]>>, type: ReferenceType) => {
        setState((prev) => [...prev, "Chưa có tên"]);
        addFunction("Chưa có tên", type);
    };

    const handleChange = (
        setState: React.Dispatch<React.SetStateAction<string[]>>,
        index: number,
        value: string,
        // updateFunction: (oldValue: string, newValue: string) => void,
        currentState: string[],
        type: ReferenceType
    ) => {
        const oldValue = currentState[index];
        setState((prev) => prev.map((item, i) => (i === index ? value : item)));
        updateFunction(oldValue, value, type);
    };

    const handleDelete = (
        setState: React.Dispatch<React.SetStateAction<string[]>>,
        index: number,
        // deleteFunction: (value: string) => void,
        currentState: string[],
        type: ReferenceType
    ) => {
        const valueToDelete = currentState[index];
        setState((prev) => prev.filter((_, i) => i !== index));
        deleteFunction(valueToDelete, type);
    };

    return (
        <div style={{ display: "flex", justifyContent: "center", gap: "40px", padding: "20px", background: "#f8f6f2" }}>
            <div style={{ textAlign: "center" }}>
                <Title level={3}>Khoa</Title>
                {faculties.map((faculty, index) => (
                    <Space key={index} style={{ display: "flex", marginBottom: "8px" }}>
                        <Input
                            value={faculty}
                            onChange={(e) => handleChange(setFaculties, index, e.target.value, faculties, "faculties")}
                        />
                        <Button danger onClick={() => handleDelete(setFaculties, index, faculties, "faculties")}>
                            Xóa
                        </Button>
                    </Space>
                ))}
                <Button type="dashed" onClick={() => handleAdd(setFaculties, "faculties")}>
                    Thêm Khoa
                </Button>
            </div>

            <div style={{ textAlign: "center" }}>
                <Title level={3}>Chương trình</Title>
                {programs.map((program, index) => (
                    <Space key={index} style={{ display: "flex", marginBottom: "8px" }}>
                        <Input
                            value={program}
                            onChange={(e) => handleChange(setPrograms, index, e.target.value, programs, "programs")}
                        />
                        <Button danger onClick={() => handleDelete(setPrograms, index, programs, "programs")}>
                            Xóa
                        </Button>
                    </Space>
                ))}
                <Button type="dashed" onClick={() => handleAdd(setPrograms, "programs")}>
                    Thêm Chương trình
                </Button>
            </div>

            <div style={{ textAlign: "center" }}>
                <Title level={3}>Trạng thái</Title>
                {statuses.map((status, index) => (
                    <Space key={index} style={{ display: "flex", marginBottom: "8px" }}>
                        <Input
                            value={status}
                            onChange={(e) => handleChange(setStatuses, index, e.target.value, statuses, "student-statuses")}
                        />
                        <Button danger onClick={() => handleDelete(setStatuses, index, statuses, "student-statuses")}>
                            Xóa
                        </Button>
                    </Space>
                ))}
                <Button type="dashed" onClick={() => handleAdd(setStatuses, "student-statuses")}>
                    Thêm Trạng thái
                </Button>
            </div>

            {/* <div style={{ position: "absolute", bottom: "20px", textAlign: "center", width: "100%" }}>
                <Button type="primary" size="large">
                    Lưu thay đổi
                </Button>
            </div> */}
        </div>
    );
};

export default FacultyPage;