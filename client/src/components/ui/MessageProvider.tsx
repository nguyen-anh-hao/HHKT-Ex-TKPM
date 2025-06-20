'use client';

import { useEffect } from 'react';
import '@/libs/utils/messageUtils';

export default function MessageProvider({ children }: { children: React.ReactNode }) {
    return <>{children}</>;
}