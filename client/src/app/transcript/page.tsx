'use client';

import { Card, Table, Button, Typography, Descriptions } from 'antd';
import { DownloadOutlined } from '@ant-design/icons';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';
import { useRef } from 'react';

const { Title } = Typography;

const mockStudent = {
  id: 'SV001',
  name: 'Nguyễn Văn A',
  dob: '2002-01-15',
  faculty: 'Công nghệ thông tin',
  intake: 'K20',
};

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

const TranscriptPage = () => {
  const contentRef = useRef<HTMLDivElement>(null);

  const handleExportPDF = async () => {
    const input = contentRef.current;
    if (!input) return;

    const canvas = await html2canvas(input);
    const imgData = canvas.toDataURL('image/png');
    const pdf = new jsPDF('p', 'mm', 'a4');
    const pdfWidth = pdf.internal.pageSize.getWidth();
    const pdfHeight = (canvas.height * pdfWidth) / canvas.width;

    pdf.addImage(imgData, 'PNG', 0, 0, pdfWidth, pdfHeight);
    pdf.save(`Bang-diem-${mockStudent.id}.pdf`);
  };

  return (
    <div>
      <Card>
        <div ref={contentRef}>
          <Title level={3} style={{ textAlign: 'center' }}>
            BẢNG ĐIỂM SINH VIÊN
          </Title>

          <Descriptions bordered column={1} size="middle" style={{ marginBottom: 24 }}>
            <Descriptions.Item label="Mã số sinh viên">{mockStudent.id}</Descriptions.Item>
            <Descriptions.Item label="Họ tên">{mockStudent.name}</Descriptions.Item>
            <Descriptions.Item label="Ngày sinh">{mockStudent.dob}</Descriptions.Item>
            <Descriptions.Item label="Khoa">{mockStudent.faculty}</Descriptions.Item>
            <Descriptions.Item label="Khóa">{mockStudent.intake}</Descriptions.Item>
          </Descriptions>

          <Table
            bordered
            pagination={false}
            dataSource={mockTranscript}
            rowKey="id"
            columns={[
              { title: 'Mã môn học', dataIndex: 'id' },
              { title: 'Tên môn học', dataIndex: 'name' },
              { title: 'Số tín chỉ', dataIndex: 'credits' },
              { title: 'Điểm', dataIndex: 'grade' },
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
        </div>

        <div style={{ marginTop: 24, textAlign: 'right' }}>
          <Button type="primary" icon={<DownloadOutlined />} onClick={handleExportPDF}>
            Xuất PDF
          </Button>
        </div>
      </Card>
    </div>
  );
};

export default TranscriptPage;
