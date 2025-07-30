package com.springboot.job_platform.models;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resumes")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = true)
    private String title;

    @Column(
        nullable = true,
        length = 500
    )
    private String description;

    @Column(nullable = true)
    private Long phoneNumber;

    @Column(nullable = true)
    private LocalDate dateOfBirth;

    @Column(nullable = true)
    private String city;

    @OneToOne
    @JoinColumn(
        name = "applicant_id",
        nullable = false
    )
    private User applicant;
}
