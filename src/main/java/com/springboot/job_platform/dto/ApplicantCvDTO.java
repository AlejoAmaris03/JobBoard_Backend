package com.springboot.job_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ApplicantCvDTO {
    private int id;
    private String applicantName;
    private String cvFileName;
    private String cvFileType;
    private byte[] cvFileData;
}
