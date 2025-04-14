package org.example.backend.service.impl;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.common.RegistrationStatus;
import org.example.backend.domain.Address;
import org.example.backend.domain.ClassRegistration;
import org.example.backend.domain.Document;
import org.example.backend.domain.Faculty;
import org.example.backend.domain.Program;
import org.example.backend.domain.Student;
import org.example.backend.domain.StudentStatus;
import org.example.backend.dto.request.StudentRequest;
import org.example.backend.dto.request.StudentUpdateRequest;
import org.example.backend.dto.response.StudentResponse;
import org.example.backend.mapper.AddressMapper;
import org.example.backend.mapper.DocumentMapper;
import org.example.backend.mapper.StudentMapper;
import org.example.backend.repository.IClassRegistrationRepository;
import org.example.backend.repository.IFacultyRepository;
import org.example.backend.repository.IProgramRepository;
import org.example.backend.repository.IStudentRepository;
import org.example.backend.repository.IStudentStatusRepository;
import org.example.backend.service.IStudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements IStudentService {

    private final IStudentRepository studentRepository;
    private final IProgramRepository programRepository;
    private final IFacultyRepository facultyRepository;
    private final IStudentStatusRepository studentStatusRepository;
    private final IClassRegistrationRepository classRegistrationRepository;

    @Override
    @Transactional
    public List<StudentResponse> addStudents(List<StudentRequest> requests) {
        return requests.stream()
                .map(this::addStudent)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StudentResponse addStudent(StudentRequest request) {
        if (studentRepository.findByStudentId(request.getStudentId()).isPresent()) {
            log.error("Student id {} already exists", request.getStudentId());
            throw new RuntimeException("Student id already exists");
        }

        Faculty faculty = facultyRepository.findById(request.getFacultyId()).orElseThrow(() -> new RuntimeException("Faculty not found"));
        Program program = programRepository.findById(request.getProgramId()).orElseThrow(() -> new RuntimeException("Program not found"));
        StudentStatus studentStatus = studentStatusRepository.findById(request.getStudentStatusId()).orElseThrow(() -> new RuntimeException("Student status not found"));

        Student student = StudentMapper.mapToDomain(request);
        student.setFaculty(faculty);
        student.setProgram(program);
        student.setStudentStatus(studentStatus);

        student = studentRepository.save(student);

        log.info("Student {} has been created and saved", student.getStudentId());

        return StudentMapper.mapToResponse(student);
    }

    @Override
    public StudentResponse getStudent(String studentId) {
        Student student = studentRepository.findByStudentId(studentId).orElseThrow(() -> new RuntimeException("Student not found"));

        log.info("Student {} has been retrieved", student.getStudentId());

        return StudentMapper.mapToResponse(student);
    }

    @Override
    public Page<StudentResponse> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable).map(StudentMapper::mapToResponse);
    }

    @Override
    @Transactional
    public StudentResponse updateStudent(String studentId, StudentUpdateRequest request) {
        Student student = studentRepository.findByStudentId(studentId).orElseThrow(() -> {
            log.error("Student not found");
            throw new RuntimeException("Student not found");
        });

        Optional.ofNullable(request.getFullName()).ifPresent(student::setFullName);
        Optional.ofNullable(request.getDob()).ifPresent(student::setDob);
        Optional.ofNullable(request.getGender()).ifPresent(student::setGender);
        Optional.ofNullable(request.getIntake()).ifPresent(student::setIntake);
        Optional.ofNullable(request.getEmail()).ifPresent(student::setEmail);
        Optional.ofNullable(request.getPhone()).ifPresent(student::setPhone);
        Optional.ofNullable(request.getNationality()).ifPresent(student::setNationality);
        Optional.ofNullable(request.getFacultyId()).ifPresent(facultyId -> {
            Faculty faculty = facultyRepository.findById(facultyId).orElseThrow(() -> {
                log.error("Faculty not found");
                throw new RuntimeException("Faculty not found");
            });
            student.setFaculty(faculty);
        });
        Optional.ofNullable(request.getProgramId()).ifPresent(programId -> {
            Program  program = programRepository.findById(programId).orElseThrow(() -> {
                log.error("Program not found");
                throw new RuntimeException("Program not found");
            });
            student.setProgram(program);
        });
        Optional.ofNullable(request.getStudentStatusId()).ifPresent(studentStatusId -> {
            StudentStatus studentStatus = studentStatusRepository.findById(studentStatusId).orElseThrow(() -> {
                log.error("Student status not found");
                throw new RuntimeException("Student status not found");
            });
            student.setStudentStatus(studentStatus);
        });

        log.info("Student {} has been updated with basic fields", student.getStudentId());

        if (request.getAddresses() != null) {
            student.getAddresses().clear();
            student.getAddresses().addAll(request.getAddresses().stream()
                    .map(addressRequest -> {
                        Address address = AddressMapper.mapToDomain(addressRequest);
                        address.setStudent(student);
                        return address;
                    })
                    .toList());

            log.info("Student {} has been updated with addresses and saved", student.getStudentId());
        }

        if (request.getDocuments() != null) {
            // Update Documents (Update existing or add new)
            List<Document> existingDocuments = student.getDocuments();
            List<Document> updatedDocuments = request.getDocuments().stream()
                    .map(documentRequest -> existingDocuments.stream()
                            .filter(existing -> existing.getDocumentNumber().equals(documentRequest.getDocumentNumber())) // Check if it already exists
                            .findFirst()
                            .map(existing -> { // Update existing
                                existing.setDocumentType(documentRequest.getDocumentType());
                                existing.setIssuedBy(documentRequest.getIssuedBy());
                                existing.setIssuedDate(documentRequest.getIssuedDate());
                                existing.setExpiredDate(documentRequest.getExpiredDate());
                                existing.setIssuedCountry(documentRequest.getIssuedCountry());
                                existing.setNote(documentRequest.getNote());
                                existing.setHasChip(documentRequest.getHasChip());
                                return existing;
                            })
                            .orElseGet(() -> { // Create new if not exists
                                Document document = DocumentMapper.mapToDomain(documentRequest);
                                document.setStudent(student);
                                return document;
                            }))
                    .toList();

            existingDocuments.clear();
            existingDocuments.addAll(updatedDocuments);
            log.info("Student {} has been updated with documents and saved", student.getStudentId());
        }

        Student updatedStudent = studentRepository.save(student);
        log.info("Student {} has been updated and saved", student.getStudentId());

        return StudentMapper.mapToResponse(updatedStudent);
    }

    @Override
    public void deleteStudent(String studentId) {
        Student student = studentRepository.findByStudentId(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        studentRepository.delete(student);

        log.info("Student {} has been deleted", student.getStudentId());
    }

    @Override
    public Page<StudentResponse> searchStudent(String keyword, Pageable pageable) {
        return studentRepository.searchByKeyword(keyword, pageable).map(StudentMapper::mapToResponse);
    }

    @Override
    public byte[] getStudentTranscript(String studentId) {
        Student student = studentRepository.findByStudentId(studentId).orElseThrow(() -> {
            log.error("Student not found");
            return new RuntimeException("Student not found");
        });

        List<ClassRegistration> classRegistrations = student.getClassRegistrations();

        double gpa = classRegistrations.stream()
                .filter(cr -> cr.getStatus() == RegistrationStatus.COMPLETED)
                .mapToDouble(ClassRegistration::getGrade)
                .average()
                .orElse(0.0);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            BaseFont baseFont = BaseFont.createFont("src/main/resources/font/Roboto-Regular.ttf",
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            Font titleFont = new Font(baseFont, 18, Font.BOLD);
            Font sectionFont = new Font(baseFont, 14, Font.BOLD);
            Font normalFont = new Font(baseFont, 12);

            Paragraph title = new Paragraph("BẢNG ĐIỂM SINH VIÊN", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            document.add(title);

            Paragraph studentInfoHeading = new Paragraph("1. Thông tin sinh viên", sectionFont);
            studentInfoHeading.setSpacingAfter(10f);
            document.add(studentInfoHeading);

            document.add(new Paragraph("• Mã sinh viên: " + student.getStudentId(), normalFont));
            document.add(new Paragraph("• Họ và tên: " + student.getFullName(), normalFont));
            document.add(new Paragraph("• Ngày sinh: " + student.getDob(), normalFont));
            document.add(new Paragraph("• Giới tính: " + student.getGender(), normalFont));
            document.add(new Paragraph("• Khóa học: " + student.getIntake(), normalFont));
            document.add(Chunk.NEWLINE);

            Paragraph classHeading = new Paragraph("2. Danh sách môn học đã hoàn thành", sectionFont);
            classHeading.setSpacingAfter(10f);
            document.add(classHeading);

            for (ClassRegistration cr : classRegistrations) {
                if (cr.getStatus() == RegistrationStatus.COMPLETED) {
                    String line = String.format("• Lớp: %s | Môn học: %s | Điểm: %.2f",
                            cr.getAClass().getClassCode(), cr.getAClass().getCourse().getCourseName(), cr.getGrade());
                    document.add(new Paragraph(line, normalFont));
                }
            }

            document.add(Chunk.NEWLINE);
            Paragraph gpaHeading = new Paragraph("3. Điểm trung bình tích lũy (GPA)", sectionFont);
            gpaHeading.setSpacingAfter(10f);
            document.add(gpaHeading);
            document.add(new Paragraph("• Điểm trung bình tích lũy: " + String.format("%.2f", gpa), normalFont));

            document.close();
        } catch (DocumentException e) {
            log.error("Error generating PDF", e);
            throw new RuntimeException("Error generating PDF", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        log.info("Transcript PDF for student {} has been generated", student.getStudentId());
        return outputStream.toByteArray();
    }
}
