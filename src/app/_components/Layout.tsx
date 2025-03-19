'use client'

import { MenuFoldOutlined, MenuUnfoldOutlined, UserOutlined, TeamOutlined, ApartmentOutlined, HomeOutlined } from '@ant-design/icons';
import { Button, Menu, theme, Layout as AntdLayout, Typography } from 'antd';
import { Content, Header } from 'antd/es/layout/layout';
import Sider from 'antd/es/layout/Sider';
import {useRouter, usePathname} from 'next/navigation';

import { useState } from 'react';

export default function Layout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {
    const [collapsed, setCollapsed] = useState(false);
    const router = useRouter();

    const handleMenuClick = (e: any) => {
        if (e.key === '1') {
            router.push('/');
        } else if (e.key === '2') {
            router.push('/student-management');
        } else if (e.key === '3') {
            router.push('/reference-data-management');
        }
    };

    const getKeyFromUrl = (url: string) => {
        if (url === '/student-management') {
            return '2';
        } else if (url === '/reference-data-management') {
            return '3';
        }
        return '1';
    };  

    return (
        <AntdLayout style={{ height: '100vh' }}>
            <Sider width={250} trigger={null} collapsible collapsed={collapsed} style={{ background: '#FFFFFF' }}>
                <Typography.Title level={3} style={{ textAlign: 'center', height: '80px', lineHeight: '80px', margin: 0 }}>
                    <TeamOutlined />
                </Typography.Title>
                <Menu
                    mode="inline"
                    selectedKeys={[getKeyFromUrl(usePathname())]}
                    onClick={handleMenuClick}
                    items={[
                        {
                            key: '1',
                            icon: <HomeOutlined />,
                            label: 'Trang chủ',
                        },
                        {
                            key: '2',
                            icon: <UserOutlined />,
                            label: 'Quản lý sinh viên',
                        },
                        {
                            key: '3',
                            icon: <ApartmentOutlined />,
                            label: 'Quản lý danh mục',
                        }
                    ]}
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
    );
}