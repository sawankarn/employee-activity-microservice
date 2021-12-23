package com.hcl.dbs.activityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyActivityDTO {
    private String name;
    private Integer emp_code;
    private String job_title;
    private String email_id;
    private String status;
    private String description;
    private Date date;
}
