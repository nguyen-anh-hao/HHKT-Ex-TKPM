'use client'

import CoursePage from './components/Home';
import { useTranslations } from 'next-intl';

export default function CourseManagementPage() {
    const t = useTranslations('course-management');

    return <CoursePage />;
}