package org.example.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.backend.common.Auditable;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "semesters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Semester extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "semester", nullable = false)
    private Integer semester;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "academic_year", nullable = false)
    private String academicYear;

    @Column(name = "last_cancel_date", nullable = false)
    private LocalDate lastCancelDate;

    @OneToMany(mappedBy = "semester")
    private List<Class> classes;
}
