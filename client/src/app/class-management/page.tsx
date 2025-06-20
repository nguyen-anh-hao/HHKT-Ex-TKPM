'use client'

import ClassPage from './components/Home';
import { useTranslations } from 'next-intl';

export default function ClassManagementPage() {
    const t = useTranslations('class-management');

    return <ClassPage />;
}