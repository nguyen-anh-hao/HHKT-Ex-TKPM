'use client';

import { Card, Table, Button, Typography, Descriptions } from 'antd';
import { DownloadOutlined } from '@ant-design/icons';
import { downloadTranscript } from '@/libs/services/transcriptFileService';
import { useStudent } from '@/libs/hooks/student/useStudents';
import { useParams } from 'next/navigation';
import { useTranslations } from 'next-intl';
import { Transcript } from '@/interfaces/Transcript';
import { useTranscripts } from '@/libs/hooks/transcript/useTransripts';

const { Title } = Typography;

const calculateGPA = (transcript: Transcript[]) => {
    if (!transcript || transcript.length === 0) return '0.00';
    
    const validCourses = transcript.filter(course => course.grade != null && course.grade !== 0);
    const totalCredits = validCourses.reduce((sum, course) => sum + course.credits, 0);
    if (totalCredits === 0) return '0.00';
    const totalPoints = validCourses.reduce((sum, course) => sum + course.credits * (course.grade ?? 0), 0);
    return (totalPoints / totalCredits).toFixed(2);
};

export default function TranscriptPage() {
    const params = useParams();
    const studentId = params.studentId as string;
    const { data: student, error, isLoading } = useStudent(studentId);
    const t = useTranslations('transcript');
    const tCommon = useTranslations('common');
    const tStudent = useTranslations('student-management');
    const tRegister = useTranslations('register-class');
    const tCourse = useTranslations('course-management');
    const tClass = useTranslations('class-management');

    const { data: allTranscripts } = useTranscripts();
    const transcriptData = allTranscripts?.filter(item => item.studentId === studentId);

    const handleExportPDF = async () => {
        const response = await downloadTranscript(studentId);
        const link = document.createElement('a');
        link.href = response;
        link.click();
    }

    if (isLoading) return <p>{tCommon('loading')}</p>;
    if (error) return <p>{tCommon('error')}</p>;    
    
    return (
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
                        dataSource={transcriptData}
                        rowKey={(record) => `${record.courseCode}-${record.classCode}`}
                        columns={[
                            { title: tClass('semester'), dataIndex: 'semester' },
                            { title: tClass('academic-year'), dataIndex: 'academicYear' },
                            { title: tCourse('course-code'), dataIndex: 'courseCode' },
                            { title: tCourse('course-name'), dataIndex: 'courseName' },
                            { title: tCourse('credits'), dataIndex: 'credits' },
                            { title: tRegister('class-code'), dataIndex: 'classCode' },
                            { title: tRegister('grade'), dataIndex: 'grade' },
                        ]}
                        summary={() => (
                            <Table.Summary.Row>
                                <Table.Summary.Cell index={0} colSpan={6}>
                                    <div style={{ textAlign: 'right', width: '100%' }}>
                                        <b>GPA</b>
                                    </div>
                                </Table.Summary.Cell>
                                <Table.Summary.Cell index={7}>
                                    <b>{calculateGPA(transcriptData ?? [])}</b>
                                </Table.Summary.Cell>
                            </Table.Summary.Row>
                        )}
                    />
                </div>                
                <div style={{ marginTop: 24, textAlign: 'right' }}>
                    <Button type="primary" icon={<DownloadOutlined />} onClick={handleExportPDF}>
                        {t('export')}
                    </Button>
                </div>
            </Card>
        </div>
    );
}
