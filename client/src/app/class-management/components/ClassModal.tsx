import { Form, Input, Modal, Button, Select, message } from 'antd';
import { useEffect, useState } from 'react';
import { Class } from '../../../interfaces/ClassResponse';
import { useLecturers, useCourses } from '@/libs/hooks/useReferences';

const { Option } = Select;

interface ClassModalProps {
  visible: boolean;
  onCancel: () => void;
  onSubmit: (value: any) => void;
  classData?: Class;  
  allClasses: Class[];  
  isResetModal?: boolean;
  setIsResetModal?: any;
}

type LecturerOption = {
  id: number;
  name: string;
};

type CourseOption = {
  id: number;
  name: string;
};


const ClassModal = ({
  visible,
  onCancel,
  onSubmit,
  classData,  
  allClasses,  
  isResetModal,
  setIsResetModal,
}: ClassModalProps) => {
  const [classForm] = Form.useForm();
  const [isEdit, setIsEdit] = useState<boolean>(false);
const { data: lecturerOptions } = useLecturers();
const { data: courseOptions } = useCourses();


const renderOptions = (options?: { key: number; value: string; label: string }[]) =>
        options?.map((option) => (
            <Option key={option.key} value={option.key}>
                {option.label}
            </Option>
        )) ?? null;

  useEffect(() => {
    if (classData) {
      classForm.setFieldsValue(classData);
      setIsEdit(true);
    } else {
      classForm.resetFields();
      setIsEdit(false);
    }
  }, [classData, classForm]);

  const handleSubmit = () => {
    classForm
      .validateFields()
      .then((value) => {
      
        console.log('value' , value);

        const finalValue = {
          ...value,
        };

        onSubmit(finalValue);
      })
      .catch(() => {
        message.error('Vui lòng kiểm tra lại thông tin!');
      });
  };

  return (
    <Modal
      title={isEdit ? 'Sửa lớp học' : 'Thêm lớp học'}
      open={visible}
      onCancel={() => {
        onCancel();
        classForm.resetFields();
      }}
      footer={null}
      width={700}
    >
      <Form form={classForm} layout="vertical">
        <Form.Item
          label="Mã lớp học"
          name="classCode"
          rules={[{ required: true, message: 'Mã lớp học là bắt buộc!' }]}
        >
          <Input disabled={isEdit} />
        </Form.Item>


        <Form.Item
          label="Học kỳ"
          name="semesterId"
          rules={[{ required: true, message: 'Học kỳ là bắt buộc!' }]}
        >
          <Select placeholder="Chọn học kỳ">
            <Option value={1}>Học kỳ 1</Option>
            <Option value={2}>Học kỳ 2</Option>
            <Option value={3}>Học kỳ 3</Option>
          </Select>
        </Form.Item>
   <Form.Item label='Giảng viên' name='lecturerName' rules={[{ required: true, message: 'Giảng viên là bắt buộc!' }]}>
                                <Select>{renderOptions(lecturerOptions)} </Select>
                            </Form.Item>
                            <Form.Item label='Môn học' name='courseName' rules={[{ required: true, message: 'Môn học là bắt buộc!' }]}>
                                <Select>{renderOptions(courseOptions)}</Select>
                            </Form.Item>
        
       

  
        <Form.Item
          label="Số lượng tối đa"
          name="maxStudents"
          rules={[{ required: true, message: 'Số lượng tối đa là bắt buộc!' }]}
        >
          <Input type="number" min={1} />
        </Form.Item>

        <Form.Item label="Lịch học" name="schedule" rules={[{ required: true, message: 'Lịch học là bắt buộc!' }]}>
          <Input />
        </Form.Item>

        <Form.Item label="Phòng học" name="room" rules={[{ required: true, message: 'Phòng học là bắt buộc!' }]}>
          <Input />
        </Form.Item>
      </Form>

      <Button type="primary" onClick={handleSubmit}>
        Lưu
      </Button>
    </Modal>
  );
};

export default ClassModal;
