'use client'

import { MenuFoldOutlined, MenuUnfoldOutlined, UserOutlined, TeamOutlined, SettingOutlined, FileTextOutlined, ApartmentOutlined, BookOutlined, SwapOutlined, CalendarOutlined } from '@ant-design/icons';
import { Button, Menu, Layout as AntdLayout, Typography } from 'antd';
import { useRouter, usePathname } from 'next/navigation';
import { useState } from 'react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { useTranslations } from 'next-intl';
import React from 'react';
import LanguageSelector from '../ui/LanguageSelector';

const { Header, Content, Sider } = AntdLayout;

export default function Layout({ children }: Readonly<{ children: React.ReactNode }>) {
    const [collapsed, setCollapsed] = useState(false);
    const router = useRouter();
    const pathname = usePathname();
    const queryClient = new QueryClient();
    const t = useTranslations('common');    

    const menuItems = [
        { key: '2', icon: <UserOutlined />, label: t('student-management'), route: '/student-management' },
        { key: '3', icon: <ApartmentOutlined />, label: t('reference-management'), route: '/reference-management' },
        { key: '4', icon: <SettingOutlined />, label: t('status-rules-configuration'), route: '/status-rules-configuration' },
        { key: '5', icon: <BookOutlined />, label: t('course-management'), route: '/course-management' },
        { key: '6', icon: <CalendarOutlined />, label: t('semester-management'), route: '/semester-management' },
        { key: '7', icon: <TeamOutlined />, label: t('class-management'), route: '/class-management' },
        { key: '8', icon: <SwapOutlined />, label: t('register-class'), route: '/register-class' },
        { key: '9', icon: <FileTextOutlined />, label: t('transcript'), route: '/transcript' },
    ];

    const handleMenuClick = (e: any) => {
        const selectedItem = menuItems.find(item => item.key === e.key);
        if (selectedItem) {
            router.push(selectedItem.route);
        }
    };

    const getKeyFromUrl = (url: string) => {
        const foundItem = menuItems.find(item => url.startsWith(item.route));
        return foundItem ? foundItem.key : '1';
    };

    return (
        <QueryClientProvider client={queryClient}>
            <AntdLayout style={{ minHeight: '100vh' }}>
                <Sider width={250} trigger={null} collapsible collapsed={collapsed} style={{ background: '#FFFFFF' }}>
                    <Typography.Title level={4} style={{ textAlign: 'center', height: '80px', lineHeight: '80px', margin: 0 }}>
                        <TeamOutlined />
                    </Typography.Title>
                    <Menu
                        mode='inline'
                        selectedKeys={[getKeyFromUrl(pathname)]}
                        onClick={handleMenuClick}
                        items={menuItems.map(item => ({
                            key: item.key,
                            icon: item.icon,
                            label: item.label
                        }))}
                    />
                </Sider>
                <AntdLayout className='site-layout'>
                    <Header style={{ padding: 0, background: '#FFFFFF', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                        <Button
                            type='text'
                            icon={collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
                            onClick={() => setCollapsed(!collapsed)}
                            style={{ fontSize: '16px', width: 64, height: 64 }}
                        />
                        <LanguageSelector />
                    </Header>
                    <Content
                        className='site-layout-background'
                        style={{
                            margin: '24px 16px',
                            padding: 24,
                            minHeight: 280,
                        }}
                    >
                        {children}
                    </Content>
                </AntdLayout>
            </AntdLayout>
        </QueryClientProvider>
    );
}
