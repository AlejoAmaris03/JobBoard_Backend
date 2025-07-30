package com.springboot.job_platform.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ResumeDTO {
    private int id;
    private String applicantName;
    private String title;
    private String description;
    private Long phoneNumber;
    private LocalDate dateOfBirth;
    private String city;
}
