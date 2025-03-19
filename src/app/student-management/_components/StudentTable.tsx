// components/StudentTable.tsx
import { Table, Button } from "antd";
import { EditOutlined, DeleteOutlined } from "@ant-design/icons";
import moment from "moment";

const StudentTable = ({ students, onEdit, onDelete, openModal }: any) => {
    const columns = [
        { title: "MSSV", dataIndex: "mssv" },
        { title: "Họ tên", dataIndex: "fullName" },
        { title: "Ngày sinh", dataIndex: "dob", render: (dob: string) => moment(dob).format("DD/MM/YYYY") },
        { title: "Giới tính", dataIndex: "gender" },
        { title: "Khoa", dataIndex: "faculty" },
        { title: "Khóa", dataIndex: "course" },
        { title: "Tình trạng", dataIndex: "status" },
        {
            title: "Hành động",
            render: (_: any, record: any) => (
                <>
                    <Button style={{ marginRight: 8 }} icon={<EditOutlined />} onClick={() => { onEdit(record); openModal(true); }}>Sửa</Button>
                    <Button icon={<DeleteOutlined />} danger onClick={() => onDelete(record.mssv)}>Xóa</Button>
                </>
            ),
        },
    ];

    return <Table columns={columns} dataSource={students} rowKey="mssv"/>;
};

export default StudentTable;
