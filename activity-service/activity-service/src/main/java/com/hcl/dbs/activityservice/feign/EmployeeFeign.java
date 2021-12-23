package com.hcl.dbs.activityservice.feign;

import com.hcl.dbs.activityservice.config.FeignConfiguration;
import com.hcl.dbs.activityservice.dto.EmployeeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Date;
import java.util.List;

@FeignClient(name = "employeeData", url = "${employee-url}", configuration = FeignConfiguration.class)
public interface EmployeeFeign {
    @RequestMapping(method = RequestMethod.GET, value = "{employeeCode}")
    ResponseEntity<Object> findEmployeeByCode(@PathVariable("employeeCode") Integer employeeId);

    @RequestMapping(method = RequestMethod.GET, value = "dateSearch/{employeeCode}")
    ResponseEntity<List<Object>> findAllByDate(Date date);
}
