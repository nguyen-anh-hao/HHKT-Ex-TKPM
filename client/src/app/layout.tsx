import type { Metadata } from 'next';

import '@ant-design/v5-patch-for-react-19';
import { AntdRegistry } from '@ant-design/nextjs-registry';
import { ConfigProvider } from 'antd';
import Layout from '../components/layout/Layout';

export const metadata: Metadata = {
  title: 'Create Next App',
  description: 'Generated by create next app',
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang='en'>
      <body style={{ margin: 0, padding: 0 }}>
        <AntdRegistry>
          <ConfigProvider
            // theme={{
            //   token: {
            //     fontSize: 16,
            //     controlHeight: 40,
            //   },
            // }}
          >
            <Layout>{children}</Layout>
          </ConfigProvider>
        </AntdRegistry>
      </body>
    </html>
  );
}
