'use client';

import { useEffect, useState } from 'react';
import { Button, message } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import CourseTable from './CourseTable';
import CourseModal from './CourseModal';
import { Course } from '@/interfaces/course/Course';
import {
    useCreateCourse,
    useDeleteCourse,
    useUpdateCourse
} from '@/libs/hooks/course/useCourseMutation';
import useReferenceStore from '@/libs/stores/referenceStore';
import { useTranslations } from 'next-intl';

const Home = () => {
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [selectedCourse, setSelectedCourse] = useState<Course | null>(null);
    const [isResetModal, setIsResetModal] = useState(false);

    const { mutate: createCourse, isPending: isCreating } = useCreateCourse();
    const { mutate: updateCourse, isPending: isUpdating } = useUpdateCourse();
    const { mutate: deleteCourse, isPending: isDeleting } = useDeleteCourse();

    const fetchReference = useReferenceStore((state) => state.fetchReference);
    const t = useTranslations('course-management');
    const tCommon = useTranslations('common');
    const tMessages = useTranslations('messages');

    useEffect(() => {
        fetchReference();
    }, [fetchReference]);

    const handleAddOrUpdateCourse = (value: Course) => {
        if (selectedCourse) {
            updateCourse(
                { ...value, courseId: selectedCourse.courseId },
                {
                    onSuccess: () => {
                        message.success(tMessages('update-success', { entity: tCommon('course').toLowerCase() }));
                        setIsModalVisible(false);
                        setSelectedCourse(null);
                        setIsResetModal(true);
                    },
                    onError: (error: any) => {
                        const errorMessage = error.response?.data?.errors
                            ? error.response.data.errors.map((e: any) => e.defaultMessage).join(' ')
                            : error.response?.data?.message || error.message;
                        message.error(`${tMessages('update-error', { entity: tCommon('course').toLowerCase() })}: ${errorMessage}`);
                    },
                }
            );
        } else {
            createCourse(value, {
                onSuccess: () => {
                    message.success(tMessages('create-success', { entity: tCommon('course').toLowerCase() }));
                    setIsModalVisible(false);
                },
                onError: (error: any) => {
                    const errorMessage = error.response?.data?.errors
                        ? error.response.data.errors.map((e: any) => e.defaultMessage).join(' ')
                        : error.response?.data?.message || error.message;
                    message.error(`${tMessages('create-error', { entity: tCommon('course').toLowerCase() })}: ${errorMessage}`);
                },
            });
        }
    };

    const handleDeleteCourse = (id: number) => {
        deleteCourse(id, {
            onSuccess: () => {
                message.success(tMessages('delete-success', { entity: tCommon('course').toLowerCase() }));
            },
            onError: (error: any) => {
                const errorMessage = error.response?.data?.errors
                    ? error.response.data.errors.map((e: any) => e.defaultMessage).join(' ')
                    : error.response?.data?.message || error.message;
                message.error(`${tMessages('delete-error', { entity: tCommon('course').toLowerCase() })}: ${errorMessage}`);
            },
        });
    };

    const isLoading = isCreating || isUpdating || isDeleting;

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
                    loading={isCreating}
                >
                    {t('add-course')}
                </Button>
            </div>
            <CourseTable
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
            />
        </div>
    );
};

export default Home;
