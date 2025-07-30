package com.springboot.job_platform.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "applicant_cvs")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ApplicantCV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String cvFileName;

    @Column(nullable = false)
    private String cvFileType;

    @Lob
    @Column(nullable = false)
    private byte[] cvFileData;

    @OneToOne
    @JoinColumn(
        name = "applicant_id",
        nullable = false
    )
    private User applicant;
}
