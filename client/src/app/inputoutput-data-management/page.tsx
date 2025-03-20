"use client";

import { useState } from "react";
import { Button, Typography, Upload, message } from "antd";
import { UploadOutlined, DownloadOutlined } from "@ant-design/icons";

const { Title } = Typography;

const ImportExportPage = () => {
    const [csvFile, setCsvFile] = useState<File | null>(null);
    const [jsonFile, setJsonFile] = useState<File | null>(null);
    const [data, setData] = useState<any[]>([]);

    const handleCsvUpload = (file: File) => {
        setCsvFile(file);
        const reader = new FileReader();
        reader.onload = (e) => {
            const text = e.target?.result as string;
            const rows = text.split("\n").map(row => row.split(","));
            setData(rows);
            message.success("Tải lên CSV thành công!");
        };
        reader.readAsText(file);
        return false; 
    };

    const handleJsonUpload = (file: File) => {
        setJsonFile(file);
        const reader = new FileReader();
        reader.onload = (e) => {
            const json = JSON.parse(e.target?.result as string);
            setData(json);
            message.success("Tải lên JSON thành công!");
        };
        reader.readAsText(file);
        return false;
    };

    const exportCsv = () => {
        const csvContent = data.map(row => row.join(",")).join("\n");
        const blob = new Blob([csvContent], { type: "text/csv" });
        const link = document.createElement("a");
        link.href = URL.createObjectURL(blob);
        link.download = "data.csv";
        link.click();
    };

    const exportJson = () => {
        const blob = new Blob([JSON.stringify(data, null, 2)], { type: "application/json" });
        const link = document.createElement("a");
        link.href = URL.createObjectURL(blob);
        link.download = "data.json";
        link.click();
    };

    return (
        <div style={{ textAlign: "center", padding: "20px", background: "#f8f6f2" }}>
            <Title level={2}>Nhập xuất dữ liệu</Title>

            {}
            <Title level={3}>Nhập</Title>
            <div style={{ display: "flex", justifyContent: "center", gap: "20px", marginBottom: "20px" }}>
                <Upload beforeUpload={handleCsvUpload} showUploadList={false}>
                    <Button icon={<UploadOutlined />}>Chọn file CSV</Button>
                </Upload>
                <Upload beforeUpload={handleJsonUpload} showUploadList={false}>
                    <Button icon={<UploadOutlined />}>Chọn file JSON</Button>
                </Upload>
            </div>
            <p>{csvFile ? `Đã chọn: ${csvFile.name}` : "Chưa chọn file CSV."}</p>
            <p>{jsonFile ? `Đã chọn: ${jsonFile.name}` : "Chưa chọn file JSON."}</p>

            {}
            <Title level={3}>Xuất</Title>
            <div style={{ display: "flex", justifyContent: "center", gap: "20px" }}>
                <Button icon={<DownloadOutlined />} onClick={exportCsv}>Tải về CSV</Button>
                <Button icon={<DownloadOutlined />} onClick={exportJson}>Tải về JSON</Button>
            </div>
        </div>
    );
};

export default ImportExportPage;
