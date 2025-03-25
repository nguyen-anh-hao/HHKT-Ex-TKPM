package org.example.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.backend.common.Auditable;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "address_type")
    private String addressType;

    @Column(name = "house_number_street_name")
    private String houseNumberStreetName;

    @Column(name = "ward_commune")
    private String wardCommune;

    @Column(name = "district")
    private String district;

    @Column(name = "city_province")
    private String cityProvince;

    @Column(name = "country")
    private String country;
}
