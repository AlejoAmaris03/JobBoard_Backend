package com.springboot.job_platform.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "experiences")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String jobTitle;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = true)
    private String startDate;

    @Column(nullable = true)
    private String endDate;

    @ManyToOne
    @JoinColumn(
        name = "applicant_id",
        nullable = false
    )
    private User applicant;
}
