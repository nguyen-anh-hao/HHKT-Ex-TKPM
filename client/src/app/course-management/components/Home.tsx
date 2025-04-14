'use client';

import { useEffect, useRef, useState } from 'react';
import { Button, message } from 'antd';
import { PlusOutlined, UploadOutlined, DownloadOutlined } from '@ant-design/icons';
import CourseTable from './CourseTable';
import CourseModal from './CourseModal';
import { Course } from '@/interfaces/CourseResponse';
import {
  updateCourse as updateCourseState,
  addCourse as addCourseState,
  deleteCourse as deleteCourseState
} from '../actions/CourseActions';
import {
  useCreateCourse,
  useDeleteCourse,
  useUpdateCourse
} from '@/libs/hooks/useCourseMutation';
import useReferenceStore from '@/libs/stores/referenceStore';
import { useFaculties } from '@/libs/hooks/useReferences';

export default function Home({ initialCourses }: { initialCourses: Course[] }) {
  const [courses, setCourses] = useState<Course[]>(initialCourses);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [selectedCourse, setSelectedCourse] = useState<Course | null>(null);
  const [isResetModal, setIsResetModal] = useState(false);
  const fileInputRef = useRef<HTMLInputElement>(null);

  const { mutate: createCourse } = useCreateCourse();
  const { mutate: updateCourse } = useUpdateCourse();
  const { mutate: deleteCourse } = useDeleteCourse();

  const { data: facultyOptions } = useFaculties();
  const fetchReference = useReferenceStore((state) => state.fetchReference);

  useEffect(() => {
    fetchReference();
  }, [fetchReference]);

  const handleAddOrUpdateCourse = (value: Course) => {
    if (selectedCourse) {
      updateCourse(value, {
        onSuccess: () => {
          message.success('Cập nhật môn học thành công');
          setCourses(updateCourseState(courses, value));
          setIsResetModal(true);
        },
        onError: (error: any) => {
          message.error(
            `Cập nhật môn học thất bại: ${
              error.response?.data?.errors?.map((e: any) => e.defaultMessage).join(' ') ||
              error.response?.data?.message
            }`
          );
        }
      });
    } else {
      if (value.courseId && courses.some((course) => course.courseId === value.courseId)) {
        message.error('Mã môn học đã tồn tại');
        return;
      }
      if (courses.some((course) => course.courseName === value.courseName)) {
        message.error('Tên môn học đã tồn tại');
        return;
      }
      createCourse(value, {
        onSuccess: () => {
          message.success('Thêm môn học thành công');
          setCourses(addCourseState(courses, value));
        },
        onError: (error: any) => {
          message.error(
            `Thêm môn học thất bại: ${
              error.response?.data?.errors?.map((e: any) => e.defaultMessage).join(' ') ||
              error.response?.data?.message
            }`
          );
        }
      });
    }
  };

  const handleDeleteCourse = (courseId: number) => {
    deleteCourse(courseId, {
      onSuccess: () => {
        message.success('Xóa môn học thành công');
        setCourses(deleteCourseState(courses, courseId));
      },
      onError: (error: any) => {
        message.error(
          `Xóa môn học thất bại: ${
            error.response?.data?.errors?.map((e: any) => e.defaultMessage).join(' ') ||
            error.response?.data?.message
          }`
        );
      }
    });
  };

 

  return (
    <div>
      <h1>Quản lý môn học</h1>
      <div style={{ display: 'flex', gap: '10px', marginBottom: '20px' }}>
        <Button
          type='primary'
          icon={<PlusOutlined />}
          onClick={() => {
            setSelectedCourse(null);
            setIsModalVisible(true);
          }}
        >
          Thêm môn học
        </Button>
     

       
      </div>
      <CourseTable
        courses={courses}
        openModal={setIsModalVisible}
        onEdit={setSelectedCourse}
        onDelete={handleDeleteCourse}
      />
      <CourseModal
        visible={isModalVisible}
        onCancel={() => {
          setIsModalVisible(false);
          setSelectedCourse(null);
        }}
        onSubmit={handleAddOrUpdateCourse}
        course={selectedCourse || undefined}
        isResetModal={isResetModal}
        setIsResetModal={setIsResetModal}
        allCourses={courses}
      />
    </div>
  );
}
