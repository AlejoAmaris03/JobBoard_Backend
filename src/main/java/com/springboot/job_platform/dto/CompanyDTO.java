package com.springboot.job_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CompanyDTO {
    private int id;
    private String name;
    private String description;
    private String imageName;
}
