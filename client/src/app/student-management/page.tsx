'use client'

import { Suspense } from 'react';
import Home from './components/Home';
import { useTranslations } from 'next-intl';

export default function StudentManagementPage() {
    const t = useTranslations('common');

    return (
        <Suspense fallback={<div>{t('loading')}...</div>}>
            <Home />
        </Suspense>
    );
}