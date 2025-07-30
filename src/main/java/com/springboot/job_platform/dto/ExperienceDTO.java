package com.springboot.job_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ExperienceDTO {
    private int id;
    private String applicantName;
    private String jobTitle;
    private String companyName;
    private String startDate;
    private String endDate;
}
