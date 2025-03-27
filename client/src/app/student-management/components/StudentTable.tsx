import { Table, Button, Popconfirm, Input } from "antd";
import { EditOutlined, DeleteOutlined } from "@ant-design/icons";
import { Student } from "../../../interfaces/student/state.interface";
import moment from "moment";
import { useState } from "react";

const StudentTable = ({ students, onEdit, onDelete, openModal }: any) => {
    const [searchText, setSearchText] = useState("");

    // Lọc sinh viên dựa trên MSSV hoặc Họ tên
    const filteredStudents = students.filter((student: Student) =>
        student.studentId.toLowerCase().includes(searchText.toLowerCase()) ||
        student.fullName.toLowerCase().includes(searchText.toLowerCase())
    );

    const columns = [
        { title: "MSSV", dataIndex: "studentId" },
        { title: "Họ tên", dataIndex: "fullName" },
        { title: "Ngày sinh", dataIndex: "dob", render: (dob: string) => moment(dob).format("YYYY-MM-DD") },
        { title: "Giới tính", dataIndex: "gender" },
        {
            title: "Khoa",
            dataIndex: "faculty",
            filters: [
                { text: "Khoa Luật", value: "Khoa Luật" },
                { text: "Khoa Tiếng Anh thương mại", value: "Khoa Tiếng Anh thương mại" },
                { text: "Khoa Tiếng Nhật", value: "Khoa Tiếng Nhật" },
                { text: "Khoa Tiếng Pháp", value: "Khoa Tiếng Pháp" },
            ],
            onFilter: (value: any, record: Student) => record.faculty === value as string,
        },
        { title: "Khóa", dataIndex: "intake" },
        { title: "Tình trạng", dataIndex: "studentStatus" },
        {
            title: "Hành động",
            render: (_: any, record: Student) => (
                <>
                    <Button
                        style={{ marginRight: 8 }}
                        icon={<EditOutlined />}
                        onClick={() => {
                            onEdit(record);
                            openModal(true);
                        }}
                    >
                        Sửa
                    </Button>
                    <Popconfirm
                        title="Bạn có chắc chắn muốn xóa sinh viên này?"
                        onConfirm={() => onDelete(record.studentId)}
                        okText="Xóa"
                        cancelText="Hủy"
                    >
                        <Button icon={<DeleteOutlined />} danger>
                            Xóa
                        </Button>
                    </Popconfirm>
                </>
            ),
        },
    ];

    return (
        <div>
            {/* Thanh tìm kiếm */}
            <Input.Search
                placeholder="Tìm kiếm theo MSSV hoặc Họ tên"
                allowClear
                onChange={(e) => setSearchText(e.target.value)}
                style={{ 
                    marginBottom: 16,
                    width: 300,
                    float: 'right' 
                }}
            />
            {/* Bảng sinh viên */}
            <Table columns={columns} dataSource={filteredStudents} rowKey="studentId" />
        </div>
    );
};

export default StudentTable;