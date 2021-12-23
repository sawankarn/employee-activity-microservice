package com.hcl.dbs.activityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDailyActivityDTO {
    private String name;
    private String jobTitle;
    private String emailId;
    private List<ActivityDTO> activities;
}
