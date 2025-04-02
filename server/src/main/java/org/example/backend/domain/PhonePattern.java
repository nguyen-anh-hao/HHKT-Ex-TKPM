package org.example.backend.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "phone_patterns")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhonePattern {

    @Id
    @Column(name = "country_code", length = 2)
    private String countryCode;

    @Column(name = "regex_pattern", nullable = false)
    private String regexPattern;

    @Column(name = "description")
    private String description;
}