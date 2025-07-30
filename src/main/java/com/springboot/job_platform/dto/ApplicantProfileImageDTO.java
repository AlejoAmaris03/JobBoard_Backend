package com.springboot.job_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ApplicantProfileImageDTO {
    private int id;
    private String applicantName;
    private String profileImageName;
    private String profileImageType;
    private byte[] profileImageData;
}
