'use client'

import RegisterPage from './components/Home'; 
import { useRegistrations } from '@/libs/hooks/useRegisterQuery'; 

export default function RegisterManagementPage() {
    const { data: registrations, error, isLoading } = useRegistrations(); 

    if (isLoading) return <div>Đang tải dữ liệu đăng ký...</div>; 
    if (error) return <div>Lỗi khi tải đăng ký</div>; 

    return <RegisterPage initialRegisters={registrations || []} />; 
}
