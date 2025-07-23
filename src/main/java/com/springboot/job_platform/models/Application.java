package com.springboot.job_platform.models;

import java.time.LocalDate;
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
@Table(name = "application")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int status;

    @Column(nullable = false)
    private LocalDate appliedAt;

    @ManyToOne
    @JoinColumn(
        name = "applicant_id",
        nullable = false
    )
    private User user;

    @ManyToOne
    @JoinColumn(
        name = "job_id",
        nullable = false
    )
    private JobPost job;
}
