package org.example.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.backend.common.Auditable;

import java.util.List;

@Entity
@Table(name = "student_statuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentStatus extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "student_status_name", unique = true, nullable = false)
    private String studentStatusName;

    @OneToMany(mappedBy = "studentStatus", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Student> students;

    @OneToMany(mappedBy = "currentStatus", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<StudentStatusRule> currentStatusRules;

    @OneToMany(mappedBy = "allowedTransition", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<StudentStatusRule> allowedTransitionRules;
}
