import { Table, Button, Popconfirm, Input } from "antd";
import { EditOutlined, DeleteOutlined } from "@ant-design/icons";
import { Student } from "../../../interfaces/student.interface";
import moment from "moment";
import { useState } from "react";
import useReferenceDataStore from "@/lib/stores/referenceDataStore";

const StudentTable = ({ students, onEdit, onDelete, openModal }: any) => {
    const [searchText, setSearchText] = useState("");

    // Lọc sinh viên dựa trên MSSV hoặc Họ tên
    const filteredStudents = students.filter((student: Student) =>
        student.studentId.toLowerCase().includes(searchText.toLowerCase()) ||
        student.fullName.toLowerCase().includes(searchText.toLowerCase())
    );

    const { facultyOptions } = useReferenceDataStore() as {
        facultyOptions: { value: string; label: string }[];
    };

    const columns = [
        { title: "MSSV", dataIndex: "studentId" },
        { title: "Họ tên", dataIndex: "fullName" },
        { title: "Ngày sinh", dataIndex: "dob", render: (dob: string) => moment(dob).format("YYYY-MM-DD") },
        { title: "Giới tính", dataIndex: "gender" },
        {
            title: "Khoa",
            dataIndex: "faculty",
            filters: facultyOptions.map((option) => ({
                text: option.value,
                value: option.value,
            })),
            onFilter: (value: any, record: Student) => record.faculty === value as string,
        },
        { title: "Khóa", dataIndex: "intake" },
        { title: "Tình trạng", dataIndex: "studentStatus" },
        {
            title: "Hành động",
            render: (_: any, record: Student) => (
                <>
                    <Button
                        style={{ marginRight: 8, marginBottom: 8 }}
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
                placeholder="Tìm kiếm theo MSSV hoặc họ tên"
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