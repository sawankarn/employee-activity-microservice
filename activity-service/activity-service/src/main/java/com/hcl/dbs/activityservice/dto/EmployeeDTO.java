package com.hcl.dbs.activityservice.dto;

import lombok.Data;

@Data
public class EmployeeDTO {
    private Integer id;
    private Integer code;
    private String name;
    private String job;
    private String title;
    private String emailId;
    private Integer experience;
    private Long phoneNumber;
    private String location;
    private String project_status;
}
