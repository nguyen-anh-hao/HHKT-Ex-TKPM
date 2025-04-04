package org.example.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.backend.common.Auditable;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "class_registrations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassRegistration extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @Column(name = "cancellation_date")
    private LocalDateTime cancellationDate;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class aClass;

    @OneToOne(mappedBy = "classRegistration")
    private Transcript transcript;

    @OneToMany(mappedBy = "classRegistration")
    private List<ClassRegistrationHistory> classRegistrationHistories;
}
