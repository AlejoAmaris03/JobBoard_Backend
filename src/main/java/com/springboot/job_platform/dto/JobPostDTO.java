package com.springboot.job_platform.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class JobPostDTO {
    private int id;
    private String name;
    private String description;
    private String location;
    private String type;
    private float salary;
    private LocalDate createdAt;
    private String companyName;
    private String recruiterName;
}
