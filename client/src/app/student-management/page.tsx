'use client'

import Home from './components/Home';
import { useStudents } from '@/libs/hooks/useStudents';

// import { fetchStudents } from '@/libs/services/studentService';
// export const dynamic = 'force-dynamic';

import { useTranslations } from 'next-intl';

export default function StudentManagementPage() {
    const t = useTranslations('common');
    const { data: students, error, isLoading } = useStudents();
    // console.log('students', students);
    if (isLoading) return <div>{t('loading')}...</div>;
    if (error) return <div>{t('error')}</div>;
    return <Home initialStudents={students || []} />;
}