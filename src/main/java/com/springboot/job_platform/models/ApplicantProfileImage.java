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
@Table(name = "applicant_profile_images")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ApplicantProfileImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String profileImageName;

    @Column(nullable = false)
    private String profileImageType;

    @Lob
    @Column(nullable = false)
    private byte[] profileImageData;

    @OneToOne
    @JoinColumn(
        name = "applicant_id",
        nullable = false
    )
    private User applicant;
}
