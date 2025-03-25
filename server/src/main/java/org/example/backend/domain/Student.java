package org.example.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.backend.common.Auditable;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student extends Auditable {
    @Id
    @Column(name = "student_id", length = 10)
    private String studentId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "intake")
    private String intake;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Column(name = "nationality")
    private String nationality;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    @ManyToOne
    @JoinColumn(name = "student_status_id")
    private StudentStatus studentStatus;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;

    @OneToMany(mappedBy= "student", cascade= CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents;
}
