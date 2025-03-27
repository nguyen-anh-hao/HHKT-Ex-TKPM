package org.example.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.backend.common.Auditable;

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

    @ManyToOne
    @JoinColumn(name = "current_status_id", nullable = false)
    private StudentStatus currentStatus;

    @ManyToOne
    @JoinColumn(name = "allowed_transition_id", nullable = false)
    private StudentStatus allowedTransition;
}
