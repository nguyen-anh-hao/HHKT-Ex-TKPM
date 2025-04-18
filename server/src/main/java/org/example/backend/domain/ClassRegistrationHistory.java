package org.example.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.backend.common.Auditable;
import org.example.backend.common.RegistrationStatus;

@Entity
@Table(name = "class_registration_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassRegistrationHistory extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "action", nullable = false)
    @Enumerated(EnumType.STRING)
    private RegistrationStatus action;

    @Column(name = "reason")
    private String reason;

    @ManyToOne
    @JoinColumn(name = "class_registration_id")
    private ClassRegistration classRegistration;
}
