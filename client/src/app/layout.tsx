import type { Metadata } from 'next';

import '@ant-design/v5-patch-for-react-19';
import { AntdRegistry } from '@ant-design/nextjs-registry';
import { ConfigProvider } from 'antd';
import Layout from '../components/layout/Layout';
import { NextIntlClientProvider } from 'next-intl';
import { getLocale } from 'next-intl/server';
import MessageProvider from '@/components/ui/MessageProvider';

export const metadata: Metadata = {
  title: 'Student Management System',
  description: 'A comprehensive system for managing student data, classes, and registrations.',
};

export default async function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const locale = await getLocale();
  return (
    <html lang={locale}>
      <body style={{ margin: 0, padding: 0 }}>
        <NextIntlClientProvider>
          <AntdRegistry>
            <ConfigProvider
            // theme={{
            //   token: {
            //     fontSize: 16,
            //     controlHeight: 40,
            //   },
            // }}
            >
              <MessageProvider>
                <Layout>{children}</Layout>
              </MessageProvider>
            </ConfigProvider>
          </AntdRegistry>
        </NextIntlClientProvider>
      </body>
    </html>
  );
}
