package org.example.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.backend.common.Auditable;

import java.math.BigDecimal;

@Entity
@Table(name = "transcripts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transcript extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "grade", precision = 3, scale = 2)
    private BigDecimal grade;

    @OneToOne
    @JoinColumn(name = "class_registration_id", unique = true, nullable = false)
    private ClassRegistration classRegistration;
}
