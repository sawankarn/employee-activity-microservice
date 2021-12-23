package com.hcl.dbs.employeeservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull(message = "Please enter employee code")
    private Integer code;
    @NotNull(message = "Please enter employee name")
    private String name;
    @NotNull(message = "Please enter employee job")
    private String job;
    @NotNull(message = "Please enter employee designation")
    private String title;
    @NotNull(message = "Please enter employee email")
    @Email(message = "Please enter correct email.")
    private String emailId;
    @NotNull(message = "Please enter employee experience")
    private Integer experience;
    @NotNull(message = "Please enter employee phone")
    private Long phoneNumber;
    @NotNull(message = "Please enter employee location")
    private String location;
    @NotNull(message = "Please enter employee status")
    private String project_status;

}
