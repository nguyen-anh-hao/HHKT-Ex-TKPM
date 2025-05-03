'use client'

import ClassPage from './components/Home';
import { useClasses } from '@/libs/hooks/useClassQuery';

export default function ClassManagementPage() {
    const { data: classes, error, isLoading } = useClasses();

    if (isLoading) return <div>Đang tải dữ liệu lớp học...</div>;
    if (error) return <div>Lỗi khi tải lớp học</div>;

    return <ClassPage initialClasses={classes || []} />;
}