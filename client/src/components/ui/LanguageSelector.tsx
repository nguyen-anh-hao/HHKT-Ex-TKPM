'use client'

import { Select, Typography } from 'antd';
import { useTranslations } from 'next-intl';
import Cookies from 'js-cookie';
import { useEffect, useState } from 'react';

const LanguageSelector = () => {
    const t = useTranslations('common');
    const [isClient, setIsClient] = useState(false);
    const defaultLocale = Cookies.get('NEXT_LOCALE') || 'vi';

    useEffect(() => {
        setIsClient(true);
    }, []);

    const handleLocaleChange = (value: string) => {
        Cookies.set('NEXT_LOCALE', value);
        window.location.reload();
    };

    if (!isClient) {
        return null;
    }

    return (
        <div style={{ marginRight: '16px' }}>
            <Typography.Text style={{ marginRight: '8px' }}>{t('language')}:</Typography.Text>
            <Select
                defaultValue={defaultLocale}
                onChange={handleLocaleChange}
                style={{ width: 120 }}
                options={[
                    { value: 'en', label: 'English' },
                    { value: 'vi', label: 'Tiếng Việt' },
                ]}
            />
        </div>
    );
};

export default LanguageSelector;
