package com.springboot.job_platform.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ApplicationDTO {
    private int id;
    private UserDTO applicant;
    private String jobName;
    private String status;
    private LocalDate appliedAt;
}
