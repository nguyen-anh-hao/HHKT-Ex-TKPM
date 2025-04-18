'use client';

import { useEffect, useRef, useState } from 'react';
import { Button, message } from 'antd';
import { PlusOutlined, UploadOutlined, DownloadOutlined } from '@ant-design/icons';
import ClassTable from './ClassTable'; // Sửa từ CourseTable thành ClassTable
import ClassModal from './ClassModal'; // Sửa từ CourseModal thành ClassModal
import { Class } from '@/interfaces/ClassResponse'; // Sửa từ CourseResponse thành ClassResponse
import {

  addClass as addClassState,
 
} from '../actions/ClassActions'; // Sửa từ CourseActions thành ClassActions
import {
  useCreateClass,
} from '@/libs/hooks/useClassMutation'; // Sửa từ useCourseMutation thành useClassMutation
import useReferenceStore from '@/libs/stores/referenceStore';
import { useFaculties } from '@/libs/hooks/useReferences';

export default function Home({ initialClasses }: { initialClasses: Class[] }) { 
  const [classes, setClasses] = useState<Class[]>(initialClasses); 
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [selectedClass, setSelectedClass] = useState<Class | null>(null); 
  const [isResetModal, setIsResetModal] = useState(false);
  const fileInputRef = useRef<HTMLInputElement>(null);

  const { mutate: createClass } = useCreateClass(); 


  const { data: facultyOptions } = useFaculties();
  const fetchReference = useReferenceStore((state) => state.fetchReference);

  useEffect(() => {
    fetchReference();
  }, [fetchReference]);

  const handleAddOrUpdateClass = (value: Class) => {
  createClass(value, {
    onSuccess: () => {
      message.success('Thêm lớp học thành công');
      setClasses(addClassState(classes, value));
      setIsModalVisible(false); // Ẩn modal sau khi thêm
    },
    onError: (error: any) => {
      message.error(
        `Thêm lớp học thất bại: ${
          error.response?.data?.errors?.map((e: any) => e.defaultMessage).join(' ') ||
          error.response?.data?.message
        }`
      );
    },
  });
};


 

  return (
    <div>
      <h1>Quản lý lớp học</h1> {}
      <div style={{ display: 'flex', gap: '10px', marginBottom: '20px' }}>
        <Button
          type='primary'
          icon={<PlusOutlined />}
          onClick={() => {
            setSelectedClass(null); 
            setIsModalVisible(true);
          }}
        >
          Thêm lớp học {}
        </Button>
      </div>
      <ClassTable 
        classes={classes}
        openModal={setIsModalVisible} onEdit={function (classData: Class): void {
          throw new Error('Function not implemented.');
        } } onDelete={function (id: number): void {
          throw new Error('Function not implemented.');
        } }       
       
      />
      <ClassModal
        visible={isModalVisible}
        onCancel={() => {
          setIsModalVisible(false);
          setSelectedClass(null); 
        }}
        onSubmit={handleAddOrUpdateClass}
        classData={selectedClass || undefined} 
        isResetModal={isResetModal}
        setIsResetModal={setIsResetModal}
        allClasses={classes} 
      />
    </div>
  );
}
