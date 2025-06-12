'use client';

import { Card, Table, Button, Typography, Descriptions } from 'antd';
import { DownloadOutlined } from '@ant-design/icons';
import { downloadTranscript } from '@/libs/services/transcriptService';
import { useStudent } from '@/libs/hooks/student/useStudents';
import { useParams } from 'next/navigation';
import { useTranslations } from 'next-intl';

const { Title } = Typography;

const mockTranscript = [
    { id: 'CS101', name: 'Lập trình căn bản', credits: 3, grade: 8.0 },
    { id: 'CS102', name: 'Cấu trúc dữ liệu', credits: 4, grade: 7.5 },
    { id: 'MATH01', name: 'Toán rời rạc', credits: 3, grade: 9.0 },
];

const calculateGPA = () => {
    const totalCredits = mockTranscript.reduce((sum, course) => sum + course.credits, 0);
    const totalPoints = mockTranscript.reduce((sum, course) => sum + course.credits * course.grade, 0);
    return (totalPoints / totalCredits).toFixed(2);
};

export default function TranscriptPage() {
    const params = useParams();
    const studentId = params.studentId as string;
    const { data: student, error, isLoading } = useStudent(studentId);
    const t = useTranslations('transcript');
    const tCommon = useTranslations('common');
    const tStudent = useTranslations('student-management');

    const handleExportPDF = async () => {
        const response = await downloadTranscript(studentId);
        const link = document.createElement('a');
        link.href = response;
        link.click();
    }

    if (isLoading) return <p>{tCommon('loading')}</p>;
    if (error) return <p>{tCommon('error')}</p>;    return (
        <div>
            <Card>
                <div>
                    <Title level={3} style={{ textAlign: 'center' }}>
                        {t('title')}
                    </Title>

                    <Descriptions bordered column={1} size="middle" style={{ marginBottom: 24 }}>
                        <Descriptions.Item label={tStudent('mssv')}>{student?.studentId}</Descriptions.Item>
                        <Descriptions.Item label={tStudent('full-name')}>{student?.fullName}</Descriptions.Item>
                        <Descriptions.Item label={tStudent('dob')}>{student?.dob}</Descriptions.Item>
                        <Descriptions.Item label={tStudent('faculty')}>{student?.faculty}</Descriptions.Item>
                        <Descriptions.Item label={tStudent('year')}>{student?.intake}</Descriptions.Item>
                    </Descriptions>

                    <Table
                        bordered
                        pagination={false}
                        dataSource={mockTranscript}
                        rowKey="id"
                        columns={[
                            { title: t('course-code'), dataIndex: 'id' },
                            { title: t('course-name'), dataIndex: 'name' },
                            { title: t('credits'), dataIndex: 'credits' },
                            { title: t('grade'), dataIndex: 'grade' },
                        ]}
                        summary={() => (
                            <Table.Summary.Row>
                                <Table.Summary.Cell index={0} colSpan={3}>
                                    <b>GPA</b>
                                </Table.Summary.Cell>
                                <Table.Summary.Cell index={3}>
                                    <b>{calculateGPA()}</b>
                                </Table.Summary.Cell>
                            </Table.Summary.Row>
                        )}
                    />
                </div>                <div style={{ marginTop: 24, textAlign: 'right' }}>
                    <Button type="primary" icon={<DownloadOutlined />} onClick={handleExportPDF}>
                        {t('export')}
                    </Button>
                </div>
            </Card>
        </div>
    );
}
