'use client'

import { Suspense } from 'react';
import Home from './components/Home';
import { useTranslations } from 'next-intl';

export default function EnrollClassPage() {
    const t = useTranslations('enroll-class');

    return (
        <Suspense fallback={<div>{t('loading')}</div>}>
            <Home />
        </Suspense>
    );
}
