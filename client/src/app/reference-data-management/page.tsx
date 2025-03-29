"use client";

import { useState, useEffect } from "react";
import { Button, Input, Space, Typography } from "antd";
import useReferenceDataStore from "../../lib/stores/referenceDataStore";

const { Title } = Typography;

const FacultyPage = () => {
    const {
        fetchReferenceData,
        facultyOptions,
        programOptions,
        studentStatusOptions,
        addFaculty,
        addProgram,
        addStudentStatus,
        deleteFaculty,
        deleteProgram,
        deleteStudentStatus,
        updateFaculty,
        updateProgram,
        updateStudentStatus,
    } = useReferenceDataStore();

    const [faculties, setFaculties] = useState<string[]>([]);
    const [programs, setPrograms] = useState<string[]>([]);
    const [statuses, setStatuses] = useState<string[]>([]);

    // Fetch reference data on component mount
    useEffect(() => {
        const fetchData = async () => {
            await fetchReferenceData();
        };
        fetchData();
    }, [fetchReferenceData]);

    // Update local state when reference data changes
    useEffect(() => {
        setFaculties(facultyOptions.map((option) => option.value));
        setPrograms(programOptions.map((option) => option.value));
        setStatuses(studentStatusOptions.map((option) => option.value));
    }, [facultyOptions, programOptions, studentStatusOptions]);

    const handleAdd = (setState: React.Dispatch<React.SetStateAction<string[]>>, addFunction: (value: string) => void) => {
        setState((prev) => [...prev, "Chưa có tên"]);
        addFunction("Chưa có tên");
    };

    const handleChange = (
        setState: React.Dispatch<React.SetStateAction<string[]>>,
        index: number,
        value: string,
        updateFunction: (oldValue: string, newValue: string) => void,
        currentState: string[]
    ) => {
        const oldValue = currentState[index];
        setState((prev) => prev.map((item, i) => (i === index ? value : item)));
        updateFunction(oldValue, value);
    };

    const handleDelete = (
        setState: React.Dispatch<React.SetStateAction<string[]>>,
        index: number,
        deleteFunction: (value: string) => void,
        currentState: string[]
    ) => {
        const valueToDelete = currentState[index];
        setState((prev) => prev.filter((_, i) => i !== index));
        deleteFunction(valueToDelete);
    };

    return (
        <div style={{ display: "flex", justifyContent: "center", gap: "40px", padding: "20px", background: "#f8f6f2" }}>
            <div style={{ textAlign: "center" }}>
                <Title level={3}>Khoa</Title>
                {faculties.map((faculty, index) => (
                    <Space key={index} style={{ display: "flex", marginBottom: "8px" }}>
                        <Input
                            value={faculty}
                            onChange={(e) => handleChange(setFaculties, index, e.target.value, updateFaculty, faculties)}
                        />
                        <Button danger onClick={() => handleDelete(setFaculties, index, deleteFaculty, faculties)}>
                            Xóa
                        </Button>
                    </Space>
                ))}
                <Button type="dashed" onClick={() => handleAdd(setFaculties, addFaculty)}>
                    Thêm Khoa
                </Button>
            </div>

            <div style={{ textAlign: "center" }}>
                <Title level={3}>Chương trình</Title>
                {programs.map((program, index) => (
                    <Space key={index} style={{ display: "flex", marginBottom: "8px" }}>
                        <Input
                            value={program}
                            onChange={(e) => handleChange(setPrograms, index, e.target.value, updateProgram, programs)}
                        />
                        <Button danger onClick={() => handleDelete(setPrograms, index, deleteProgram, programs)}>
                            Xóa
                        </Button>
                    </Space>
                ))}
                <Button type="dashed" onClick={() => handleAdd(setPrograms, addProgram)}>
                    Thêm Chương trình
                </Button>
            </div>

            <div style={{ textAlign: "center" }}>
                <Title level={3}>Trạng thái</Title>
                {statuses.map((status, index) => (
                    <Space key={index} style={{ display: "flex", marginBottom: "8px" }}>
                        <Input
                            value={status}
                            onChange={(e) => handleChange(setStatuses, index, e.target.value, updateStudentStatus, statuses)}
                        />
                        <Button danger onClick={() => handleDelete(setStatuses, index, deleteStudentStatus, statuses)}>
                            Xóa
                        </Button>
                    </Space>
                ))}
                <Button type="dashed" onClick={() => handleAdd(setStatuses, addStudentStatus)}>
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