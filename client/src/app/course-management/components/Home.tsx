'use client';

import { useEffect, useRef, useState } from 'react';
import { Button, message } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import CourseTable from './CourseTable';
import CourseModal from './CourseModal';
import { Course } from '@/interfaces/course/Course';
import {
    updateCourse as updateCourseState,
    addCourse as addCourseState,
    deleteCourse as deleteCourseState
} from '../actions/CourseActions';
import {
    useCreateCourse,
    useDeleteCourse,
    useUpdateCourse
} from '@/libs/hooks/course/useCourseMutation';
import useReferenceStore from '@/libs/stores/referenceStore';
import { useFaculties } from '@/libs/hooks/reference/useReferences';
import { useTranslations } from 'next-intl';

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
    const t = useTranslations('course-management');

    useEffect(() => {
        fetchReference();
    }, [fetchReference]);

    const handleAddOrUpdateCourse = (value: Course) => {
        if (selectedCourse) {
            updateCourse(
                { ...value, courseId: selectedCourse.courseId },
                {
                    onSuccess: () => {
                        message.success(t('update-success'));
                        setCourses(updateCourseState(courses, { ...value, courseId: selectedCourse.courseId }));
                        setIsModalVisible(false);
                    },
                    onError: (error: any) => {
                        message.error(
                            t('update-error', {
                                error: error.response?.data?.errors?.map((e: any) => e.defaultMessage).join(' ') ||
                                    error.response?.data?.message
                            })
                        );
                    },
                }
            );
        } else {
            createCourse(value, {
                onSuccess: () => {
                    message.success(t('add-success'));
                    setCourses(addCourseState(courses, value));
                    setIsModalVisible(false);
                },
                onError: (error: any) => {
                    message.error(
                        t('add-error', {
                            error: error.response?.data?.errors?.map((e: any) => e.defaultMessage).join(' ') ||
                                error.response?.data?.message
                        })
                    );
                },
            });
        }
    };

    const handleDeleteCourse = (id: number) => {
        deleteCourse(id, {
            onSuccess: () => {
                message.success(t('delete-success'));
                setCourses(deleteCourseState(courses, id));
            },
            onError: (error: any) => {
                message.error(
                    t('delete-error') + {
                        error: error.response?.data?.errors?.map((e: any) => e.defaultMessage).join(' ') ||
                            error.response?.data?.message
                    }
                );
            },
        });
    };

    return (
        <div>
            <h1>{t('course-management')}</h1>
            <div style={{ display: 'flex', gap: '10px', marginBottom: '20px' }}>
                <Button
                    type='primary'
                    icon={<PlusOutlined />}
                    onClick={() => {
                        setSelectedCourse(null);
                        setIsModalVisible(true);
                    }}
                >
                    {t('add-course')}
                </Button>
            </div>
            <CourseTable
                courses={courses}
                onEdit={(course) => {
                    setSelectedCourse(course);
                    setIsModalVisible(true);
                }}
                onDelete={handleDeleteCourse}
                openModal={setIsModalVisible}
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
