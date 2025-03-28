'use client'

import { MenuFoldOutlined, MenuUnfoldOutlined, UserOutlined, TeamOutlined, SettingOutlined, ApartmentOutlined, HomeOutlined, SwapOutlined } from '@ant-design/icons';
import { Button, Menu, theme, Layout as AntdLayout, Typography } from 'antd';
import { Content, Header } from 'antd/es/layout/layout';
import Sider from 'antd/es/layout/Sider';
import {useRouter, usePathname} from 'next/navigation';

import { useState } from 'react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

const menuItems = [
    { key: '1', icon: <HomeOutlined />, label: 'Trang chủ', route: '/' },
    { key: '2', icon: <UserOutlined />, label: 'Quản lý sinh viên', route: '/student-management' },
    { key: '3', icon: <ApartmentOutlined />, label: 'Quản lý danh mục', route: '/reference-data-management' },
    { key: '4', icon: <SwapOutlined />, label: 'Nhập xuất dữ liệu', route: '/inputoutput-data-management' },
    { key: '5', icon: <SettingOutlined />, label: 'Cấu hình', route: '/configuration' },
];

export default function Layout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {
    const [collapsed, setCollapsed] = useState(false);
    const router = useRouter();

    const handleMenuClick = (e: any) => {
        const selectedItem = menuItems.find(item => item.key === e.key);
        if (selectedItem) {
            router.push(selectedItem.route);
        }
    };

    const getKeyFromUrl = (url: string) => {
        const foundItem = menuItems.find(item => item.route === url);
        return foundItem ? foundItem.key : '1';
    };

    const queryClient = new QueryClient();

    return (
        <QueryClientProvider client={queryClient}>
            <AntdLayout style={{ height: '100vh' }}>
                <Sider width={250} trigger={null} collapsible collapsed={collapsed} style={{ background: '#FFFFFF' }}>
                    <Typography.Title level={4} style={{ textAlign: 'center', height: '80px', lineHeight: '80px', margin: 0 }}>
                        <TeamOutlined />
                    </Typography.Title>
                    <Menu
                        mode="inline"
                        selectedKeys={[getKeyFromUrl(usePathname())]}
                        onClick={handleMenuClick}
                        items={menuItems.map(item => ({
                            key: item.key,
                            icon: item.icon,
                            label: item.label
                        }))}
                    />
                </Sider>
                <AntdLayout className="site-layout">
                    <Header style={{ padding: 0, background: '#FFFFFF' }} >
                        <Button
                            type="text"
                            icon={collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
                            onClick={() => setCollapsed(!collapsed)}
                            style={{
                                fontSize: '16px',
                                width: 64,
                                height: 64,
                            }}
                        />
                    </Header>
                    <Content
                        className="site-layout-background"
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
