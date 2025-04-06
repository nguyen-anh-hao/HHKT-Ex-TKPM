'use client'

import Home from './components/Home';
import { useStudents } from '@/libs/hooks/useStudents';

// import { fetchStudents } from '@/libs/services/studentService';
// export const dynamic = 'force-dynamic';

export default function StudentManagementPage() {
    const { data: students, error, isLoading } = useStudents();
    if (isLoading) return <div>Loading...</div>;
    if (error) return <div>Error loading students</div>;
    return <Home initialStudents={students || []} />;
}