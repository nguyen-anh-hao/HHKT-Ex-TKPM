"use client";

import { useState } from "react";
import { Button, Input, Space, Typography } from "antd";

const { Title } = Typography;

const FacultyPage = () => {
    const [faculties, setFaculties] = useState<string[]>(["Khoa Công nghệ thông tin", "Khoa Luật", "Khoa Tiếng Anh thương mại", "Khoa Tiếng Nhật", "Khoa Tiếng Pháp"]);
    const [programs, setPrograms] = useState<string[]>(["Cử nhân", "Kỹ sư", "Thạc sĩ", "Tiến sĩ"]);
    const [statuses, setStatuses] = useState<string[]>(["Đang học", "Đã tốt nghiệp", "Đã thôi học", "Tạm dừng học"]);

    const handleAdd = (setState: any) => {
        setState((prev: any) => [...prev, ""]);
    };

    const handleChange = (setState: any, index: number, value: string) => {
        setState((prev: any[]) => prev.map((item, i) => (i === index ? value : item)));
    };

    const handleDelete = (setState: any, index: number) => {
        setState((prev: any[]) => prev.filter((_, i) => i !== index));
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
