'use client'

import ClassPage from './components/Home';
import { useClasses } from '@/libs/hooks/useClassQuery';
import { useTranslations } from 'next-intl';

export default function ClassManagementPage() {
    const { data: classes, error, isLoading } = useClasses();
    const t = useTranslations('class-management');

    if (isLoading) return <div>{t('loading-classes')}</div>;
    if (error) return <div>{t('error-loading-classes')}</div>;

    return <ClassPage initialClasses={classes || []} />;
}