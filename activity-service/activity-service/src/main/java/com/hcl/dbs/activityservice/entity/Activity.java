package com.hcl.dbs.activityservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull(message = "Please enter employee code")
    @Column(name="emp_code")
    private Integer empCode;
    private Date date;
    @NotNull(message = "Please enter description")
    private String description;
    @NotNull(message = "Please enter status")
    private String status;
}
