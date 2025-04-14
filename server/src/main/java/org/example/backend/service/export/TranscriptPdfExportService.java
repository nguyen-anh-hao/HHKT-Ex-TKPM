package org.example.backend.service.export;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.domain.ClassRegistration;
import org.example.backend.domain.Student;
import org.example.backend.dto.data.TranscriptData;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class TranscriptPdfExportService implements ExportService {
    @Override
    public byte[] exportData(List<?> data) throws IOException {
        if (data.isEmpty() || !(data.get(0) instanceof TranscriptData)) {
            throw new IllegalArgumentException("Invalid data for transcript export");
        }

        TranscriptData transcriptData = (TranscriptData) data.get(0);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            BaseFont baseFont = BaseFont.createFont("src/main/resources/font/Roboto-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            Font titleFont = new Font(baseFont, 18, Font.BOLD);
            Font sectionFont = new Font(baseFont, 14, Font.BOLD);
            Font normalFont = new Font(baseFont, 12);

            addTitle(document, titleFont);
            addStudentInfo(document, transcriptData.getStudent(), sectionFont, normalFont);
            addCompletedCourses(document, transcriptData.getCompletedRegistrations(), sectionFont, normalFont);
            addGpaInfo(document, transcriptData.getGpa(), sectionFont, normalFont);

            document.close();
        } catch (DocumentException e) {
            log.error("Error creating PDF document", e);
            throw new RuntimeException("Error generating PDF", e);
        }

        log.info("Transcript PDF for student {} has been generated", transcriptData.getStudent().getStudentId());
        return outputStream.toByteArray();
    }

    private void addTitle(Document document, Font titleFont) throws DocumentException {
        Paragraph title = new Paragraph("BẢNG ĐIỂM SINH VIÊN", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20f);
        document.add(title);
    }

    private void addStudentInfo(Document document, Student student, Font sectionFont, Font normalFont) throws DocumentException {
        Paragraph studentInfoHeading = new Paragraph("1. Thông tin sinh viên", sectionFont);
        studentInfoHeading.setSpacingAfter(10f);
        document.add(studentInfoHeading);

        document.add(new Paragraph("• Mã sinh viên: " + student.getStudentId(), normalFont));
        document.add(new Paragraph("• Họ và tên: " + student.getFullName(), normalFont));
        document.add(new Paragraph("• Ngày sinh: " + student.getDob(), normalFont));
        document.add(new Paragraph("• Giới tính: " + student.getGender(), normalFont));
        document.add(new Paragraph("• Khóa học: " + student.getIntake(), normalFont));
        document.add(Chunk.NEWLINE);
    }

    private void addCompletedCourses(Document document, List<ClassRegistration> completedRegistrations, Font sectionFont, Font normalFont) throws DocumentException {
        Paragraph classHeading = new Paragraph("2. Danh sách môn học đã hoàn thành", sectionFont);
        classHeading.setSpacingAfter(10f);
        document.add(classHeading);

        for (ClassRegistration cr : completedRegistrations) {
            String line = String.format("• Lớp: %s | Môn học: %s | Điểm: %.2f",
                cr.getAClass().getClassCode(), cr.getAClass().getCourse().getCourseName(), cr.getGrade());
            document.add(new Paragraph(line, normalFont));
        }
        document.add(Chunk.NEWLINE);
    }

    private void addGpaInfo(Document document, Double gpa, Font sectionFont, Font normalFont) throws DocumentException {
        Paragraph gpaHeading = new Paragraph("3. Điểm trung bình tích lũy (GPA)", sectionFont);
        gpaHeading.setSpacingAfter(10f);
        document.add(gpaHeading);
        document.add(new Paragraph("• Điểm trung bình tích lũy: " + String.format("%.2f", gpa), normalFont));
    }

    @Override
    public String getFileExtension() {
        return "pdf";
    }

    @Override
    public MediaType getMediaType() {
        return MediaType.APPLICATION_PDF;
    }
}
