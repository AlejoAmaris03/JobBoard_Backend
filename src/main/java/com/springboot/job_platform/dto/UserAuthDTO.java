package com.springboot.job_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserAuthDTO {
    private int id;
    private String fullName;
    private String token;
}
