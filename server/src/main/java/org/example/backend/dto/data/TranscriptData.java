package org.example.backend.dto.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.domain.ClassRegistration;
import org.example.backend.domain.Student;

import java.util.List;

@Getter
@Setter
@Builder
public class TranscriptData {
    private Student student;
    private List<ClassRegistration> completedRegistrations;
    private Double gpa;
}
