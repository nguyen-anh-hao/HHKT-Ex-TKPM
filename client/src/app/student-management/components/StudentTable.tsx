import { Table, Button, Popconfirm, Input } from "antd";
import { EditOutlined, DeleteOutlined } from "@ant-design/icons";
import { Student } from "@/interfaces/Student";
import moment from "moment";
import { useState } from "react";
import { SortOrder } from "antd/es/table/interface";
import { useEffect } from "react";
import { fetchReference } from "@/libs/services/referenceService";

const StudentTable = ({ students, onEdit, onDelete, openModal }: any) => {
    const [searchText, setSearchText] = useState("");
    const [facultyOptions, setFacultyOptions] = useState<{ text: string; value: string }[]>([]);

    const filteredStudents = students.filter((student: Student) =>
        student.studentId.toLowerCase().includes(searchText.toLowerCase()) ||
        student.fullName.toLowerCase().includes(searchText.toLowerCase())
    );
    
    useEffect(() => {
        const fetchFacultyOptions = async () => {
            const response = await fetchReference("faculties");
            const options = response.map((option: any) => ({
                text: option.facultyName,
                value: option.facultyName,
            }));
            setFacultyOptions(options);
        };

        fetchFacultyOptions();
    }, []);

    const columns = [
        { title: "MSSV", dataIndex: "studentId", sorter: (a: Student, b: Student) => a.studentId.localeCompare(b.studentId), defaultSortOrder: "ascend" as SortOrder, sortDirections: ["ascend", "descend"] as SortOrder[] },
        { title: "Họ tên", dataIndex: "fullName" },
        { title: "Ngày sinh", dataIndex: "dob", render: (dob: string) => moment(dob).format("YYYY-MM-DD") },
        { title: "Giới tính", dataIndex: "gender" },
        {
            title: "Khoa",
            dataIndex: "faculty",
            filters: facultyOptions?.map((option) => ({
                text: option.text,
                value: option.value,
            })) || [],
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
            <Table columns={columns} dataSource={filteredStudents} rowKey="studentId" />
        </div>
    );
};

export default StudentTable;