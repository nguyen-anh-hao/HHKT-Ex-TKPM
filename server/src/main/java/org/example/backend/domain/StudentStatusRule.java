package org.example.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.backend.common.Auditable;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "student_status_rules")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentStatusRule extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "current_status", nullable = false)
    private String currentStatus;

    @Column(name = "new_status", nullable = false)
    private String newStatus;
}
