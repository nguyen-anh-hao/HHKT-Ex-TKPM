// components/StudentModal.tsx
import { Modal, Form, Input, DatePicker, Select, Button } from "antd";
import moment from "moment";

const { Option } = Select;

const StudentModal = ({ visible, onCancel, onSubmit, student }: any) => {
    const [form] = Form.useForm();

    return (
        <Modal
            title={student ? "Sửa sinh viên" : "Thêm sinh viên"}
            open={visible}
            onCancel={onCancel}
            footer={null}
            key={student ? student.mssv : "new"}
        >
            <Form
                initialValues={student ? { ...student, dob: moment(student.dob) } : {}}
                onFinish={(values) => {
                    onSubmit(values);
                    if (student == null)
                        form.resetFields();
                }}
                layout="vertical"
                form={form}
            >
                <Form.Item name="mssv" label="Mã số sinh viên" rules={[{ required: true, message: "Vui lòng nhập MSSV!" }]}>
                    <Input disabled={!!student} />
                </Form.Item>
                <Form.Item name="fullName" label="Họ tên" rules={[{ required: true, message: "Vui lòng nhập họ tên!" }]}>
                    <Input />
                </Form.Item>
                <Form.Item name="dob" label="Ngày sinh">
                    <DatePicker style={{ width: "100%" }} />
                </Form.Item>
                <Form.Item name="gender" label="Giới tính">
                    <Select>
                        <Option value="Nam">Nam</Option>
                        <Option value="Nữ">Nữ</Option>
                    </Select>
                </Form.Item>
                <Form.Item name="faculty" label="Khoa" rules={[{ required: true, message: "Vui lòng chọn khoa!" }]}>
                    <Select>
                        <Option value="Khoa Luật">Khoa Luật</Option>
                        <Option value="Khoa Tiếng Anh thương mại">Khoa Tiếng Anh thương mại</Option>
                    </Select>
                </Form.Item>
                <Form.Item>
                    <Button type="primary" htmlType="submit">Lưu</Button>
                </Form.Item>
            </Form>
        </Modal>
    );
};

export default StudentModal;
