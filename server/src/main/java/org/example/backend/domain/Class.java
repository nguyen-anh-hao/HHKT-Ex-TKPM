package org.example.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.backend.common.Auditable;

import java.util.List;

@Entity
@Table(name = "classes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Class extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "class_code", length = 10, nullable = false, unique = true)
    private String classCode;

    @Column(name = "max_students", nullable = false)
    private Integer maxStudents;

    @Column(name = "schedule", nullable = false)
    private String schedule;

    @Column(name = "room", nullable = false)
    private String room;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;

    @OneToMany(mappedBy = "aClass")
    private List<ClassRegistration> classRegistrations;
}
